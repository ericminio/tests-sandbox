package ericminio.http.demos;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ericminio.http.UploadProtocol;
import ericminio.support.Stringify;
import ericminio.zip.FileSet;
import ericminio.zip.Zip;

import java.io.IOException;

public class Explore implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String incomingBody = new Stringify().inputStream(exchange.getRequestBody());
            debug(incomingBody);
            FileSet fileSet = new UploadProtocol().parse(incomingBody);
            debug("fileSet size = " + fileSet.size());
            byte[] bytes = new Zip().please(fileSet);
            exchange.getResponseHeaders().add("content-type", "application/zip");
            exchange.getResponseHeaders().add( "content-disposition", "attachment; filename=\"download.zip\"" );
            exchange.sendResponseHeaders(200, bytes.length);
            exchange.getResponseBody().write(bytes);
            exchange.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            exchange.getResponseHeaders().add("content-type", "text/plain");
            exchange.sendResponseHeaders(500, e.getMessage().length());
            exchange.getResponseBody().write(e.getMessage().getBytes());
            exchange.close();
        }
    }

    private void debug(String message) {
        System.out.println("[INFO] " + message);
    }
}
