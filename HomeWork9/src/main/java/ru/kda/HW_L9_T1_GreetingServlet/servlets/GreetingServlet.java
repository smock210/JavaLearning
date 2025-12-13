package ru.kda.HW_L9_T1_GreetingServlet.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/author")
public class GreetingServlet extends HttpServlet {
    private static final int MIN_DEPOSIT_AMOUNT = 50000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String firstname = "Коротков";
        String lastname = "Дмитрий";
        String midlename = "Алексеевич";
        String phone = "89534279384";
        String interests = "Java, Kotlin, Spring";
        String uri = "https://github.com/smock210/JavaLearning";

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>Информация об авторе</title>");
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; background: #f4f6f9; margin: 0; padding: 0; display: flex; justify-content: center; }");
        out.println("        .container { background: white; padding: 30px; margin: 50px auto; width: 400px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }");
        out.println("        h1 { text-align: center; color: #333; font-size: 24px; margin-bottom: 20px; }");
        out.println("        label { display: block; margin: 10px 0 5px; font-weight: bold; color: #555; }");
        out.println("        input[type='number'] { width: 100%; padding: 10px; margin: 5px 0 15px; border: 1px solid #ccc; border-radius: 5px; font-size: 16px; box-sizing: border-box; }");
        out.println("        input[type='submit'] { width: 100%; padding: 12px; background: #007BFF; color: white; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; }");
        out.println("        input[type='submit']:hover { background: #0056b3; }");
        out.println("        .result, .error { text-align: center; margin: 20px 0; padding: 15px; border-radius: 5px; }");
        out.println("        .result { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }");
        out.println("        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }");
        out.println("        a { display: block; text-align: center; margin-top: 20px; color: #007BFF; text-decoration: none; }");
        out.println("        a:hover { text-decoration: underline; }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"card\">");
        out.println("        <h1>Информация об авторе</h1>");
        out.println("            <p><strong>Фамилия:</strong>"+firstname+" </p>");
        out.println("            <p><strong>Имя:</strong> "+lastname+"</p>");
        out.println("            <p><strong>Отчество:</strong> "+midlename+"</p>");
        out.println("            <p><strong>Телефон:</strong> "+phone+"</p>");
        out.println("            <p><strong>Хобби:</strong> "+interests+"</p>");
        out.println("            <p><strong>Bitbucket url:</strong> <a href='"+uri+"'>"+uri+"</p>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

}
