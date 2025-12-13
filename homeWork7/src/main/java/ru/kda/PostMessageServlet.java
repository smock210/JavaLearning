package ru.kda;

import ru.kda.web.model.Message;
import ru.kda.web.service.GuestBookInstance;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/postMessage")
public class PostMessageServlet extends HttpServlet {
    private final GuestBookInstance guestBookInstance = GuestBookInstance.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String author = req.getParameter("author");
        String text = req.getParameter("text");
        guestBookInstance.addMessage(new Message(text, author));
        resp.sendRedirect("/Servlet-1.1/");
    }
}
