package ru.kda.web.servlets;

import ru.kda.Person.Person;
import ru.kda.Person.PersonCrud;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class ClientServer implements Runnable{
    private final Socket socket;
    private final PersonCrud personCrud;

    public ClientServer(Socket socket, PersonCrud personCrud) {
        this.socket = socket;
        this.personCrud = personCrud;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             OutputStream output = socket.getOutputStream()) {

            String requestLine = reader.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            String method = requestParts[0];
            String path = requestParts[1];


            String response;
            if (!path.startsWith("/index")) {}
            switch (method) {
                case "GET":
                    response = handleGet(path);
                    break;
                case "POST":
                    response = handlePost(path, reader);
                    break;
                case "PUT":
                    response = handlePut(path, reader);
                    break;
                case "DELETE":
                    response = handleDelete(path);
                    break;
                default:
                    response = buildResponse(405, "Метод не поддерживается");
            }

            output.write(response.getBytes(StandardCharsets.UTF_8));
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String handleGet(String path) throws IOException {
        // Обслуживаем index.html по пути "/" или "/index.html"
        if ("/".equals(path) || "/index.html".equals(path)|| "/index".equals(path)) {
            return serveStaticFile("index.html", "text/html");
        }

        if (path.startsWith("/person?")) {
            String params = path.substring(8);
            if (params.startsWith("id=")) {
                int id = Integer.parseInt(params.substring(3));
                Person person = personCrud.read(id);
                return person != null ? buildJsonResponse(200, person.toJson())
                        : buildResponse(404, "Person не найден");
            } else if (params.startsWith("name=")) {
                String name = params.substring(5);
                Person person = personCrud.read(name);
                return person != null ? buildJsonResponse(200, person.toJson())
                        : buildResponse(404, "Person не найден");
            }
        }
        return buildResponse(400, "Неверный запрос");
    }

    private String serveStaticFile(String fileName, String contentType) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStream == null) {
            return buildResponse(404, "Файл не найден: " + fileName);
        }

        String content;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            content = bufferedReader.lines().reduce("", (a, b) -> a + b + "\n");
        }

        return "HTTP/1.1 200 OK\r\n" +
                "Content-Type: " + contentType + "; charset=utf-8\r\n" +
                "Content-Length: " + content.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                content;
    }

    private String handlePost(String path, BufferedReader reader) throws IOException {
    if (!"/person".equals(path)) {
        return buildResponse(400, "Неверный путь");
    }

    // Парсим заголовки и ищем Content-Length
    String line;
    int contentLength = 0;
    while ((line = reader.readLine()) != null) {
        if (line.isEmpty()) {
            break;
        }
        if (line.startsWith("Content-Length:")) {
            try {
                contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
            } catch (NumberFormatException e) {
                return buildResponse(400, "Неверный Content-Length");
            }
        }
    }

    if (contentLength == 0) {
        return buildResponse(400, "Отсутствует или нулевое тело запроса");
    }

    char[] bodyChars = new char[contentLength];
    int totalRead = 0;
    while (totalRead < contentLength) {
        int read = reader.read(bodyChars, totalRead, contentLength - totalRead);
        if (read == -1) break;
        totalRead += read;
    }

    String body = new String(bodyChars, 0, totalRead);
    Person person = parsePersonFromForm(body);
    if (person == null) {
        return buildResponse(400, "Неверные данные");
    }

    int id = personCrud.create(person);
    return id != -1 ? buildResponse(201, "Создано с ID: " + id)
            : buildResponse(500, "Ошибка при создании");
}

    private String handlePut(String path, BufferedReader reader) throws IOException {
    if (path.startsWith("/person?id=")) {
        int id = Integer.parseInt(path.split("=")[1]);

        // Пропускаем заголовки — читаем до пустой строки
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                break; // Конец заголовков, начинается тело
            }
        }

        // Теперь читаем тело
        StringBuilder body = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            body.append(line);
        }

        if (body.isEmpty()) {
            return buildResponse(400, "Пустое тело запроса");
        }

        Person person = parsePersonFromForm(body.toString());
        if (person == null) {
            return buildResponse(400, "Неверные данные");
        }

        boolean updated = personCrud.update(id, person);
        return updated ? buildResponse(200, "Обновлено")
                : buildResponse(404, "Person не найден");
    }
    return buildResponse(400, "Неверный путь");
}

    private String handleDelete(String path) {
        if (path.startsWith("/person?")) {
            String params = path.substring(8);
            if (params.startsWith("id=")) {
                int id = Integer.parseInt(params.substring(3));
                boolean deleted = personCrud.delete(id);
                return deleted ? buildResponse(200, "Удалено")
                        : buildResponse(404, "Person не найден");
            } else if (params.startsWith("name=")) {
                String name = params.substring(5);
                boolean deleted = personCrud.delete(name);
                return deleted ? buildResponse(200, "Удалено")
                        : buildResponse(404, "Person не найден");
            }
        }
        return buildResponse(400, "Неверный запрос");
    }

    private Person parsePersonFromForm(String body) {
        try {
            // Декодируем тело из URL-encoded формата
            String decodedBody = URLDecoder.decode(body, StandardCharsets.UTF_8.name());
            String[] pairs = decodedBody.split("&");
            String name = null, city = null;
            int age = 0;
            for (String pair : pairs) {
                String[] kv = pair.split("=", 2); // Разделить только по первому '='
                if (kv.length != 2) continue;
                String key = kv[0];
                String value = kv[1];
                switch (key) {
                    case "name": name = value; break;
                    case "city": city = value; break;
                    case "age": age = Integer.parseInt(value); break;
                }
            }
            return name != null && city != null ? new Person(name, city, age) : null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String buildResponse(int status, String message) {
        String statusText = status == 200 ? "OK" : status == 201 ? "Created" : "Bad Request";
        return "HTTP/1.1 " + status + " " + statusText + "\r\n" +
                "Content-Type: text/plain; charset=utf-8\r\n" +
                "Content-Length: " + message.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                message;
    }

    private String buildJsonResponse(int status, String json) {
        String statusText = status == 200 ? "OK" : "Not Found";
        return "HTTP/1.1 " + status + " " + statusText + "\r\n" +
                "Content-Type: application/json; charset=utf-8\r\n" +
                "Content-Length: " + json.getBytes(StandardCharsets.UTF_8).length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n" +
                json;
    }
}
