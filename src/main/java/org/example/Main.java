package org.example;

import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.*;

import static org.example.Converter.jsonToJava;

public class Main {

    public static void main(String[] args) throws IOException {
        CloseableHttpResponse response;

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)    // максимальное время ожидание подключения к серверу
                        .setSocketTimeout(30000)    // максимальное время ожидания получения данных
                        .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                        .build())
                .build();

        // создание объекта запроса
        HttpGet request = new HttpGet("https://api.nasa.gov/planetary/apod?api_key=UwlnJ0L0byHnz3dqUxUYd1KGSYRT0w1slCzRFRYy");
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        response = httpClient.execute(request); // отправка запроса
        System.out.println(Converter.jsonToJava(response));

        response = httpClient.execute(request);
        String url = jsonToJava(response).url();

        request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        response = httpClient.execute(request);
        Converter.saveFile(response, url);
    }
}