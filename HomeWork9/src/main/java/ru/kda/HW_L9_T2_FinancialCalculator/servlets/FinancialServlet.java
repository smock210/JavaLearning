package ru.kda.HW_L9_T2_FinancialCalculator.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.kda.HW_L9_T2_FinancialCalculator.model.Message;
import ru.kda.HW_L9_T2_FinancialCalculator.service.GuestBookInstance;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
// ... существующий импорт ...
@WebServlet("/finance")
public class FinancialServlet extends HttpServlet {
    private static final int MIN_DEPOSIT_AMOUNT = 50000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>Калькулятор доходности вклада</title>");
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
        out.println("    <div class='container'>");
        out.println("        <h1>Калькулятор доходности вклада</h1>");
        out.println("        <form method='post'>");
        out.println("            <table>");
        out.println("            <tr><td><label for='amount'>Сумма вклада (₽):</label></td>");
        out.println("            <td><input type='number' id='amount' name='amount' value='100000' min='50000' required></td></tr>");
        out.println("            <tr><td><label for='rate'>Процентная ставка:</label></td>");
        out.println("            <td><input type='number' id='rate' name='rate' value='10' step='0.01' required></td></tr>");
        out.println("            <tr><td><label for='years'>Количество лет:</label></td>");
        out.println("            <td><input type='number' id='years' name='years' value='2' min='1' required></td></tr>");
        out.println("            <tr><td><input type='submit' value='Посчитать'></td><td></td></tr>");
        out.println("            </table>");
        out.println("        </form>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String amountStr = request.getParameter("sum");
        String rateStr = request.getParameter("percentage");
        String yearsStr = request.getParameter("years");

        try {
            int amount = Integer.parseInt(amountStr);

            if (amount < MIN_DEPOSIT_AMOUNT) {//Минимальная сумма на момент открытия вклада 50 000 рублей
                showStyledErrorPage(out, "Минимальная сумма на момент открытия вклада 50 000 рублей");
                return;
            }

            double rate = Double.parseDouble(rateStr);
            int years = Integer.parseInt(yearsStr);

            if (rate <= 0 || years <= 0) {
                showStyledErrorPage(out, "Процентная ставка и срок должны быть больше нуля.");
                return;
            }

            double result = amount * Math.pow(1 + rate / 100, years);
            int finalAmount = (int) Math.round(result);

            showStyledResultPage(out, amount, rate, years, finalAmount);

        } catch (NumberFormatException e) {
            showStyledErrorPage(out, "Неверный формат данных. Скорректируйте значения");
        }
    }

    private void showStyledResultPage(PrintWriter out, int amount, double rate, int years, int finalAmount) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>Результат</title>");
        includeStyles(out);
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <h1>Результат</h1>");
        out.println("        <div class='result'>");
        out.println("            <p><strong>Начальная сумма:</strong> " + format(amount) + " ₽</p>");
        out.println("            <p><strong>Ставка:</strong> " + rate + " % годовых</p>");
        out.println("            <p><strong>Срок:</strong> " + years + " лет</p>");
        out.println("            <p><strong>Итоговая сумма:</strong> <strong>" + format(finalAmount) + " ₽</strong></p>");
        out.println("        </div>");
        out.println("        <a href='/finance'>← Вернуться к калькулятору</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void showStyledErrorPage(PrintWriter out, String message) {
        out.println("<!DOCTYPE html>");
        out.println("<html lang='ru'>");
        out.println("<head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>Ошибка</title>");
        includeStyles(out);
        out.println("</head>");
        out.println("<body>");
        out.println("    <div class='container'>");
        out.println("        <h1>Ошибка</h1>");
        out.println("        <div class='error'>");
        out.println("            <p>" + message + "</p>");
        out.println("        </div>");
        out.println("        <a href='/finance'>← Вернуться к калькулятору</a>");
        out.println("    </div>");
        out.println("</body>");
        out.println("</html>");
    }

    private void includeStyles(PrintWriter out) {
        out.println("    <style>");
        out.println("        body { font-family: Arial, sans-serif; background: #f4f6f9; margin: 0; padding: 0; display: flex; justify-content: center; }");
        out.println("        .container { background: white; padding: 30px; margin: 50px auto; width: 400px; border-radius: 10px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }");
        out.println("        h1 { text-align: center; color: #333; font-size: 24px; margin-bottom: 20px; }");
        out.println("        .result, .error { text-align: center; margin: 20px 0; padding: 15px; border-radius: 5px; }");
        out.println("        .result { background: #d4edda; color: #155724; border: 1px solid #c3e6cb; }");
        out.println("        .error { background: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }");
        out.println("        a { display: block; text-align: center; margin-top: 20px; color: #007BFF; text-decoration: none; }");
        out.println("        a:hover { text-decoration: underline; }");
        out.println("    </style>");
    }

    private String format(int value) {
        return String.format("%,d", value).replace(",", " ");
    }
}