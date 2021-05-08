package ericminio.http;

import ericminio.support.Stringify;

import java.net.HttpURLConnection;
import java.net.URL;

public class UploadRequest {

    public static HttpResponse upload(String url, UploadPayload uploadPayload) throws Exception {
        HttpURLConnection request = (HttpURLConnection) new URL( url ).openConnection();
        request.setDoOutput(true);
        request.setDoInput(true);
        request.setUseCaches(false);
        request.setRequestMethod("POST");
        new UploadProtocol().send(uploadPayload, request);

        HttpResponse response = new HttpResponse();
        response.setStatusCode(request.getResponseCode());
        response.setContentType(request.getContentType());
        if (request.getResponseCode() < 400) {
            response.setBody(new Stringify().inputStream(request.getInputStream()));
        } else {
            response.setBody(new Stringify().inputStream(request.getErrorStream()));
        }

        return response;
    }
}
