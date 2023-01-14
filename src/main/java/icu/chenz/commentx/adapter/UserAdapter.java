package icu.chenz.commentx.adapter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author : Chenz
 * @date : 2023-01-13 1:00
 */

@Data
@Component
@ConfigurationProperties(prefix = "commentx.adapter")
public class UserAdapter {
    boolean enable = false;

    String key = null;

    String value = null;

    String verify = null;

    String verifyKey = null;

    String verifyValue = null;

    public boolean verifyPassword(String username, String password) throws Exception {
        OutputStreamWriter writer = null;
        try {
            URL url = new URL(verify);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json;charset=utf8");
            if (verifyKey != null) {
                connection.setRequestProperty(verifyKey, verifyValue);
            }
            writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(String.format("""
                    {"username":"%s","password":"%s"}
                    """, username, password));
            writer.flush();
            writer.close();
            return connection.getResponseCode() == 200;
        } catch (MalformedURLException e) {
            throw new Exception();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
}
