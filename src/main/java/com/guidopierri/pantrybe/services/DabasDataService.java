package com.guidopierri.pantrybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DabasDataService implements DataProvider {

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(DabasDataService.class);

    private Pageable createPageRequestUsing(int page, int size) {
        return PageRequest.of(page, size);
    }

    @Override
    public DabasItemResponse getArticle(String gtinNumber) throws Exception {
        String url = "https://api.dabas.com/DABASService/V2/article/gtin/" + gtinNumber + "/JSON?apikey=741ffd2b-3be4-49b8-b837-45be48c7e7be";
        Optional<String> response = sendApiRequest(url);


        if (response.get().contains("Not Found")) {
            System.out.println("404 Error: Resource not found for search parameter: " + gtinNumber);
            return null; // or handle it in another way based on your requirements
        }
        try {
            JsonNode jsonNode = validateResponse(response.get());
            String productName = jsonNode.path("Produktnamn").asText();
            String gtin = jsonNode.path("GTIN").asText();
            String brand = jsonNode.path("Varumarke").path("Varumarke").asText();
            String mainGroup = jsonNode.path("Varugrupp").path("HuvudgruppBenamning").asText();
            JsonNode bilder = jsonNode.path("Bilder");
            String imageLink = (bilder.isArray() && !bilder.isEmpty()) ? bilder.path(0).path("Lank").asText() : null;

            return new DabasItemResponse(productName, gtin, brand, imageLink, mainGroup);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    private JsonNode validateResponse(String response) throws JsonProcessingException {
        if (response == null || response.isEmpty()) {
            throw new RuntimeException("Error processing SIBS data: Response is empty");
        }
        JsonNode jsonNode = getObjectMapper().readTree(response);
        if (jsonNode.isEmpty()) {
            return jsonNode;
        }
        return jsonNode;
    }

    @Override
    public List<Search> fetchUpaginatedSearch(String searchParameter) {
        List<Search> searchList = new ArrayList<>();
        String url = "https://api.dabas.com/DABASService/V2/articles/basesearchparameter/" + searchParameter + "/JSON?apikey=741ffd2b-3be4-49b8-b837-45be48c7e7be";
        String jsonString;

        try {
            jsonString = (sendApiRequest(url).get());
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

    Optional<String> sendApiRequest(String url) throws Exception {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.statusCode();
        if (statusCode == 404) {
            log.error("404 Error: Resource not found");
            // Handle it as needed

        }
        return response.body().describeConstable();
    }

    @Override
    @Cacheable(value = "search", key = "{#searchParameter, #page}")
    public Page<DabasItemResponse> searchToPageable(String searchParameter, int page, int size) throws Exception {
        Pageable pageRequest = createPageRequestUsing(page, size);

        List<Search> searchList = fetchUpaginatedSearch(searchParameter);
        int pageSize = pageRequest.getPageSize();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageSize), searchList.size());
        List<DabasItemResponse> dtos = searchList.parallelStream()
                .map(search -> {
                    try {
                        var item = getArticle(search.getGtin());
                        String imageLink = item.image();
                        return new DabasItemResponse(search.getArtikelbenamning(), search.getGtin(), search.getVarumarke(), imageLink, search.getArtikeltyp());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
        List<DabasItemResponse> pageContent = dtos.subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, dtos.size());
    }
}
