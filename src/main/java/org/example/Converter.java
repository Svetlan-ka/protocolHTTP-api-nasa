package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class Converter {
    public static ObjectMapper mapper = new ObjectMapper();

    public record Post(String date, String explanation, String hdurl, String media_type,
                       String service_version, String title, String url) {
    }

    public static Post jsonToJava(CloseableHttpResponse response) throws IOException {
        Post post = mapper.readValue(
                response.getEntity().getContent(),
                new TypeReference<>() {
                }
        );
        return post;
    }

    public static void saveFile(CloseableHttpResponse response, String url) throws MalformedURLException {
        String dir = "D:/Java/homework_java-core/protocolHTTP-api-nasa";
        String fileName = Paths.get(new URL(url).getPath()).getFileName().toString();
        File file = new File(dir, fileName);

        HttpEntity entity = response.getEntity();
        if (entity != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
                entity.writeTo(fileOutputStream);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Файл успешно сохранён!");
        }
   }
}
