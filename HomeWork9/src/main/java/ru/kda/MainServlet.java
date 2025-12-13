package ru.kda;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"ru\">");
        out.println("<head>");
        out.println("    <meta charset=\"UTF-8\">");
        out.println("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("    <title>Домашнее задание №9</title>");
        out.println("    <link href=\"https://fonts.googleapis.com/css2?family=Roboto:wght@400;500;700&display=swap\" rel=\"stylesheet\">");
        out.println("    <style>");
        out.println("        body {");
        out.println("            font-family: 'Roboto', sans-serif;");
        out.println("            background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);");
        out.println("            margin: 0;");
        out.println("            padding: 0;");
        out.println("            display: flex;");
        out.println("            justify-content: center;");
        out.println("            align-items: center;");
        out.println("            min-height: 100vh;");
        out.println("            color: #333;");
        out.println("        }");
        out.println("        .container {");
        out.println("            background: white;");
        out.println("            padding: 40px;");
        out.println("            border-radius: 12px;");
        out.println("            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);");
        out.println("            text-align: center;");
        out.println("            max-width: 600px;");
        out.println("            width: 90%;");
        out.println("        }");
        out.println("        h1 {");
        out.println("            color: #2c3e50;");
        out.println("            margin-top: 0;");
        out.println("            font-size: 28px;");
        out.println("        }");
        out.println("        .links {");
        out.println("            margin: 30px 0;");
        out.println("        }");
        out.println("        .links a {");
        out.println("            display: inline-block;");
        out.println("            margin: 10px;");
        out.println("            padding: 14px 24px;");
        out.println("            background-color: #3498db;");
        out.println("            color: white;");
        out.println("            text-decoration: none;");
        out.println("            border-radius: 6px;");
        out.println("            font-weight: 500;");
        out.println("            transition: background 0.3s;");
        out.println("        }");
        out.println("        .links a:hover {");
        out.println("            background-color: #2980b9;");
        out.println("        }");
        out.println("        footer {");
        out.println("            margin-top: 40px;");
        out.println("            color: #7f8c8d;");
        out.println("            font-size: 14px;");
        out.println("        }");
        out.println("    </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class=\"container\">");
        out.println("        <h1>Добро пожаловать на страницу тестового задания №9!</h1>");
        out.println("        <div class=\"links\">");
        out.println("            <a href=\"/finance\">Перейти к первой части задания</a>");
        out.println("            <a href=\"/author\">Ко второй части</a>");
        out.println("        </div>");
        out.println("        <footer>");
        out.println("            &copy; 2025 Домашнее задание по сервлетам. Все права защищены.");
        out.println("        </footer>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }
}