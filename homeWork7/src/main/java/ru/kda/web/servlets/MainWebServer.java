package ru.kda.web.servlets;

import ru.kda.Person.PersonCrud;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainWebServer {
    private final PersonCrud personCrud;
    private final ServerSocket serverSocket;
    private final ExecutorService threadPool;

    /**
     * Конструктор сервера.
     *
     * @param port порт для прослушивания
     * @throws IOException если не удалось открыть ServerSocket
     */
    public MainWebServer(int port) throws IOException {
        this.personCrud = new PersonCrud();
        this.serverSocket = new ServerSocket(port);
        this.threadPool = Executors.newFixedThreadPool(10); // пул из 10 потоков
        System.out.println("Сервер запущен на порту: " + port);
    }

    /**
     * Запускает сервер и начинает слушать входящие соединения.
     */
    public void start() {
        while (!serverSocket.isClosed()) {
            try {
                Socket clientSocket = serverSocket.accept();
                threadPool.submit(new ClientServer(clientSocket, personCrud));
            } catch (IOException e) {
                if (!serverSocket.isClosed()) {
                    System.err.println("Ошибка при принятии соединения: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Останавливаем сервер.
     */
    public void stop() {
        try {
            serverSocket.close();
            threadPool.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
