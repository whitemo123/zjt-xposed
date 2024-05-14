package cn.mobai.zjt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpUtil {
  public static String performGetRequest(String requestUrl) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(requestUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();
        return response.toString();
      } else {
        return null; // 或者抛出异常
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null; // 或者抛出异常
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }

  public static String performPostRequest(String requestUrl, String postData) {
    HttpURLConnection connection = null;
    try {
      URL url = new URL(requestUrl);
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setConnectTimeout(5000);
      connection.setReadTimeout(5000);

      // 设置请求头
      connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      connection.setDoOutput(true);

      // 发送请求参数
      try (OutputStream os = connection.getOutputStream()) {
        os.write(postData.getBytes(StandardCharsets.UTF_8));
        os.flush();
      }

      // 接收响应
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
          response.append(line);
        }
        reader.close();
        return response.toString();
      } else {
        System.out.println("http状态码不正常");
        return null; // 或者抛出异常
      }
    } catch (Exception e) {
      e.printStackTrace();
      return null; // 或者抛出异常
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}
