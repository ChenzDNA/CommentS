package icu.chenz.securitydemo.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import icu.chenz.securitydemo.client.function.Callback;
import icu.chenz.securitydemo.client.function.HttpBodyWriter;
import icu.chenz.securitydemo.constant.CommentSConstant;

import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Chenz
 * @date : 2023-01-30 6:20
 */

public class CommentSClient {
    public static Map<String, String> getToken(String username) throws Exception {
        return request("http://localhost:8080/user/g",
                "application/x-www-form-urlencoded;charset=UTF-8",
                CommentSConstant.COMMENTS_GET_TOKEN_HEADER_KEY,
                CommentSConstant.COMMENTS_GET_TOKEN_HEADER_VALUE,
                writer -> writer.write("username=" + username),
                CommentSClient::resolveResult
        );
    }

    public static Map<String, String> register(String username, String nickname) throws Exception {
        return request("http://localhost:8080/user/r",
                "application/x-www-form-urlencoded;charset=UTF-8",
                CommentSConstant.COMMENTS_REGISTER_HEADER_KEY,
                CommentSConstant.COMMENTS_REGISTER_HEADER_VALUE,
                writer -> writer.write(String.format("username=%s&nickname=%s", username, nickname)),
                CommentSClient::resolveResult
        );
    }

    private static <T> T request(String url, String contentType, String headerKey, String headerValue, HttpBodyWriter bodyWriter, Callback<T> callback) throws Exception {
        OutputStreamWriter writer = null;
        InputStream inputStream = null;
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);
            connection.setRequestProperty(headerKey, headerValue);
            connection.setDoOutput(true);
            writer = new OutputStreamWriter(connection.getOutputStream());
            bodyWriter.writeTo(writer);
            writer.flush();
            inputStream = connection.getInputStream();
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            return callback.call(connection.getHeaderFields(), content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return null;
    }

    private static Map<String, String> resolveResult(Map<String, List<String>> headers, String content) throws JsonProcessingException {
        HashMap<String, String> res = new HashMap<>();
        res.put("token", headers.get("Authorization").get(0));
        HashMap map = new ObjectMapper().readValue(content, HashMap.class);
        res.put("id", map.get("data").toString());
        return res;
    }
}
