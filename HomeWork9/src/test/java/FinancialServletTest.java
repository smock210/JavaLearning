
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FinancialServletTest {

    private String sendPost(String params) throws Exception {
        URL url = new URL("http://localhost:8080/finance?" + params);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(false); // параметры в URL

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }

    @Test
    void testValidCalculation() throws Exception {
        String response = sendPost("sum=100000&percentage=10&years=5");
        assertTrue(response.contains("Итоговая сумма"), "Должен вернуть результат");
    }

    @Test
    void testMinDepositError() throws Exception {
        String response = sendPost("sum=40000&percentage=10&years=5");
        assertTrue(response.contains("Минимальная сумма на момент открытия вклада 50 000 рублей"));
    }

    @Test
    void testInvalidFormatError() throws Exception {
        String response = sendPost("sum=abc&percentage=10&years=5");
        assertTrue(response.contains("Неверный формат данных. Скорректируйте значения"));
    }
}
