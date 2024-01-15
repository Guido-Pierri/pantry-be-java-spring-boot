package com.guidopierri.pantrybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.dabas.article.Article;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class DabasDataService implements DataProvider {
    @Override
    public Article getArticle(String gtin) {
        Item item = new Item();
        String url = "https://api.dabas.com/DABASService/V2/article/gtin/"+ gtin + "/JSON?apikey=741ffd2b-3be4-49b8-b837-45be48c7e7be";
        String jsonString;
        try {
            jsonString = apiRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            Article data = getObjectMapper().readValue( jsonString, Article.class);

        return data;
    }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Search> search(String searchParameter) {
        List<Search> searchList = new ArrayList<>();
        String url = "https://api.dabas.com/DABASService/V2/articles/searchparameter/"+ searchParameter + "/JSON?apikey=741ffd2b-3be4-49b8-b837-45be48c7e7be";
        String jsonString;

        try {
            jsonString = apiRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Check for 404 error
        if (jsonString.contains("Not Found")) {
            System.out.println("404 Error: Resource not found for search parameter: " + searchParameter);
            return searchList; // or handle it in another way based on your requirements
        }
        System.out.println("jsonString:" + jsonString);
        try {
            Search[] data = getObjectMapper().readValue( jsonString, Search[].class);

            return List.of(data);
        }catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
   }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
    String apiRequest(String url) throws Exception {
        System.out.println("inside apiRequest");
        System.out.println("url"+url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode == 404) {
            System.out.println("404 Error: Resource not found");
            // Handle it as needed

        }
        return response.body();
    }

}
