package ru.kda.web.servlets;

import ru.kda.web.model.Message;
import ru.kda.web.service.GuestBookInstance;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/")
public class GetAllNotesServlet extends HttpServlet {
    private final GuestBookInstance guestBookInstance = GuestBookInstance.getInstance();
    private final List<Message> messages = guestBookInstance.getMessages();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();

        printWriter.write("<!DOCTYPE html><html lang=\"ru\"><head><meta charset=\"UTF-8\"><title>Книга отзывов и предложений</title></head>");
        printWriter.write("<body>");
        printWriter.write("<h1>Книга отзывов и предложений</h1><hr/>");
        printWriter.write("<form action=\"/Servlet-1.1/postMessage\" method=\"post\">");
        printWriter.write("<input name='author' placeholder='Введите Ваше имя' required><br/>");
        printWriter.write("<input name='text' placeholder='Введите Ваш отзыв' required><br/>");
        printWriter.write("<button type='submit'>Отправить</button>");
        printWriter.write("</form><hr/>");

        for (Message message : messages) {
            printWriter.write(message.toString());
        }

        printWriter.write("</body></html>");
    }
}