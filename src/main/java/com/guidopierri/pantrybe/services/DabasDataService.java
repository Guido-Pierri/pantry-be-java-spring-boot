package com.guidopierri.pantrybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guidopierri.pantrybe.exceptions.DataValidationException;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.dabas.article.Article;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DabasDataService implements DataProvider {
    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public Article getArticle(String gtin) {
        Item item = new Item();
        String url = "https://api.dabas.com/DABASService/V2/article/gtin/" + gtin + "/JSON?apikey=741ffd2b-3be4-49b8-b837-45be48c7e7be";
        String jsonString;
        try {
            jsonString = apiRequest(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (jsonString.contains("Not Found")) {
            System.out.println("404 Error: Resource not found for search parameter: " + gtin);
            return null; // or handle it in another way based on your requirements
        }
        try {
            Article data = getObjectMapper().readValue(jsonString, Article.class);
            if (data == null) {
                throw new DataValidationException("Data not found for gtin: " + gtin);
            }
            return data;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Search> fetchUpaginatedSearch(String searchParameter) {
        List<Search> searchList = new ArrayList<>();
        String url = "https://api.dabas.com/DABASService/V2/articles/searchparameter/" + searchParameter + "/JSON?apikey=741ffd2b-3be4-49b8-b837-45be48c7e7be";
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
            return Arrays.stream(getObjectMapper().readValue(jsonString, Search[].class)).toList();

        } catch (JsonProcessingException e) {
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
        System.out.println("url" + url);
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

    @Override
    public Page<Search> searchToPageable(String searchParameter, int page, int size) {
        Pageable pageRequest = createPageRequestUsing(page, size);

        List<Search> searchList = fetchUpaginatedSearch(searchParameter);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), searchList.size());

        List<Search> pageContent = searchList.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, searchList.size());

    }
}
