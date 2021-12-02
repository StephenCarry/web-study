package http_app;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Map;

public class HttpClient {
    private String url = "https://www.baidu.com";

    private static java.net.http.HttpClient httpClient = java.net.http.HttpClient.newBuilder().build();

    public void get() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setUseCaches(false);
            connection.setConnectTimeout(1000);
            //headers
            connection.setRequestProperty("Accept","*/*");
            connection.setRequestProperty("User-Agent","Mozilla/5.0 (compatible; MSIE 11; Windows NT 5.1)");
            //send
            connection.connect();
            //response
            if (connection.getResponseCode() != 200) {
                throw new RuntimeException("request fail");
            }
            Map<String, List<String>> map = connection.getHeaderFields();
            map.forEach((key,value)->{
                System.out.println(key+":"+value);
            });
            System.out.println("-----------");
            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void post() {
        try {
            HttpRequest httpRequest = HttpRequest.newBuilder(new URI(this.url))
                    .header("User-Agent", "Java HttpClient")
                    .headers("Accept", "*/*")
                    .timeout(Duration.ofSeconds(5))
                    .version(java.net.http.HttpClient.Version.HTTP_2)
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(httpRequest,  HttpResponse.BodyHandlers.ofString());
            Map<String, List<String>> headers = httpResponse.headers().map();
            headers.forEach((key,value)->{
                System.out.println(key+":"+value);
            });
            System.out.println(httpResponse.body().substring(0, 1024) + "...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HttpClient().get();
        new HttpClient().post();
    }
}
