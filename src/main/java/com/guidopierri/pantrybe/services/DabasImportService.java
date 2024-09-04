package com.guidopierri.pantrybe.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.exceptions.DabasException;
import com.guidopierri.pantrybe.repositories.DabasItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service class for handling operations related to importing Dabas data.
 * This includes fetching data from the DABAS API, validating API responses,
 * caching results, and interacting with the local database.
 * <p>
 * The class uses the DabasDataService for fetching data from the DABAS API,
 * the DabasItemRepository for database operations, and the EntityMapper for converting
 * between DabasItem and DabasItemResponse objects.
 * <p>
 * The class is annotated with @Service, indicating that it is a Spring service component.
 * It is also annotated with @Slf4j, which provides a logger for logging messages.
 */
@Service
@Slf4j
public class DabasImportService {

    private final DabasDataService dabasDataService;
    private final DabasItemRepository dabasItemRepository;
    private final EntityMapper entityMapper;

    @Value("${api-key}")
    private String apiKey;
    @Value("${dabas-api-url}")
    private String dabasApiUrl;

    public DabasImportService(DabasDataService dabasDataService, DabasItemRepository dabasItemRepository, EntityMapper entityMapper) {
        this.dabasDataService = dabasDataService;
        this.dabasItemRepository = dabasItemRepository;
        this.entityMapper = entityMapper;
    }

    /**
     * TODO: Import only articles with level "Bas" from DABAS API
     * remove the articles that are not level "Bas" from articlesMap
     */
    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * Imports articles from the DABAS API using their GTIN numbers.
     * The method is cache evicting, meaning that Spring will clear the cache of the "articles" cache.
     *
     * @return A List of DabasItemResponse objects representing the imported articles.
     * @throws Exception If there is an error during the API request or the JSON processing.
     * @CacheEvict(value = "articles", allEntries = true) Annotation indicating that the cache of the "articles" cache should be cleared.
     */
    @CacheEvict(value = "articles", allEntries = true)
    public List<DabasItemResponse> importArticlesGtin() throws Exception {
        List<Map<String, String>> articlesMap = new ArrayList<>();
        String url = dabasApiUrl + "articles/JSON?apikey=" + apiKey;
        String jsonString;

        jsonString = (dabasDataService.sendApiRequest(url).orElseThrow(() ->
                new DabasException("Error: No response from DABAS")));

        log.info("jsonString: {}", jsonString);
        int articlesCount = 0;
        for (Map map : getObjectMapper().readValue(jsonString, Map[].class)) {
            Map<String, String> article = new HashMap<>();
            article.put("GTIN", map.get("GTIN").toString());
            articlesMap.add(article);
            articlesCount++;
        }
        log.info("Found {} Articles in DABAS ", articlesCount);

        int batchSize = 100; // Define your batch size here
        List<DabasItemResponse> articles = new ArrayList<>();
        for (int i = 0; i < articlesMap.size(); i += batchSize) {
            int end = Math.min(i + batchSize, articlesMap.size());
            List<Map<String, String>> batch = articlesMap.subList(i, end);

            for (Map<String, String> article : batch) {
                Optional<DabasItemResponse> response = dabasDataService.getArticle(article.get("GTIN"));
                if (response.isPresent() && response.get().level().equals("Bas")) {
                    dabasItemRepository.saveAndFlush(entityMapper.dabasItemResponseToDabasItem(response.get()));
                    articles.add(response.get());
                    log.info("Imported article: {}", response.get().name());
                    log.info("article nr: {} from :{}", articles.size(), articlesCount);
                }
            }
        }
        return articles;
    }

    /**
     * Imports articles from the DABAS API on a schedule.
     * The method is scheduled to run every week at 2:00 AM.
     *
     * @throws Exception If there is an error during the API request or the JSON processing.
     * @Scheduled(cron = "0 2 * * 0") Annotation indicating that the method should be run on a schedule.
     */
    @Scheduled(cron = "0 2 * * 0") // run every week at 2:00 AM
    public void cronImportArticles() throws Exception {
        importArticlesGtin();
    }

    /**
     * Checks if the database is empty, and if so, imports articles from the DABAS API.
     *
     * @throws Exception If there is an error during the API request or the JSON processing.
     */
    public void checkAndImportArticles() throws Exception {
        if (dabasItemRepository.count() == 0) {
            importArticlesGtin();
        }
    }
}
