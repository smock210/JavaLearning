package ru.kda.HW_L9_T2_FinancialCalculator.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kda.HW_L9_T2_FinancialCalculator.model.Message;
import ru.kda.HW_L9_T2_FinancialCalculator.service.GuestBookInstance;


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
        resp.sendRedirect("/");
    }
}
