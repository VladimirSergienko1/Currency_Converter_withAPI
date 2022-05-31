import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLOutput;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

public class Converter {
    public static void main(String[] args) throws IOException {
        Boolean running = true;
        do {
            HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

            currencyCodes.put(1, "USD");
            currencyCodes.put(2, "KZT");
            currencyCodes.put(3, "RUB");
            currencyCodes.put(4, "EURO");
            currencyCodes.put(5, "CNY");

            String fromCode, toCode;
            int from, to;
            double amount;
            Scanner sc = new Scanner(System.in);

            System.out.println("Currency convert From?");
            System.out.println("1:USD \t 2:TENGE \t 3:RUB \t 4:EURO \t 5:YUAN ");
            from = sc.nextInt();
            while (from < 1 || from > 5) {
                System.out.println("Please select a valid currency (1-5)");
                System.out.println("1:USD \t 2:TENGE \t 3:RUB \t 4:EURO \t 5:YUAN ");
                from = sc.nextInt();
            }
            fromCode = currencyCodes.get(from);

            System.out.println("Currency convert To?");
            System.out.println("1:USD \t 2:TENGE \t 3:RUB \t 4:EURO \t 5:YAN ");
            to = sc.nextInt();
            while (to < 1 || to > 5) {
                System.out.println("Please select a valid currency (1-5)");
                System.out.println("1:USD \t 2:TENGE \t 3:RUB \t 4:EURO \t 5:YUAN ");
                to = sc.nextInt();
            }
            toCode = currencyCodes.get(to);

            System.out.println("Amount of money?");
            amount = sc.nextFloat();
            sendHttpGetRequest(fromCode, toCode, amount);
            System.out.println("Would you like to make another conversation?");
            System.out.println("1 YES \t 2 NO");
            if (sc.nextInt()!=1){
                running=false;
            }
        } while (running);

    }
    private static void  sendHttpGetRequest(String fromCode, String toCode, double amount) throws IOException {
        DecimalFormat f = new DecimalFormat("00.00");
        String GET_URL = "https://api.exchangerate.host/convert?from="+fromCode+"&to="+toCode+"&amount="+amount;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) !=null){
                response.append(inputLine);
            }in.close();


        JSONObject obj = new JSONObject(response.toString());
         Double oresult = obj.getDouble("result");
         System.out.println();
         System.out.println(f.format(amount) +" " + fromCode +" = " +  f.format(oresult)+" "+ toCode);
        }
        else {
            System.out.println("Fail");
        }
    }
}
