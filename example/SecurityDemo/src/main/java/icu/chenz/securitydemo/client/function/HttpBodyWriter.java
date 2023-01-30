package icu.chenz.securitydemo.client.function;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * @author : Chenz
 * @date : 2023-01-30 6:28
 */

@FunctionalInterface
public interface HttpBodyWriter {
    void writeTo(OutputStreamWriter writer) throws IOException;
}
