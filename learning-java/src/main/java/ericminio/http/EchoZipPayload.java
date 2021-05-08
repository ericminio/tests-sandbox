package ericminio.http;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ericminio.support.Stringify;
import ericminio.zip.FileSet;
import ericminio.zip.Zip;

import java.io.IOException;

public class EchoZipPayload implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        FileSet fileSet = new UploadProtocol().parse(new Stringify().inputStream(exchange.getRequestBody()));
        byte[] bytes = new Zip().please(fileSet);
        exchange.sendResponseHeaders(200, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
