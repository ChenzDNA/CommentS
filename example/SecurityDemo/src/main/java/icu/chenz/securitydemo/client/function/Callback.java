package icu.chenz.securitydemo.client.function;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FunctionalInterface
public interface Callback<R> {
    R call(Map<String, List<String>> headers, String content) throws IOException;
}
