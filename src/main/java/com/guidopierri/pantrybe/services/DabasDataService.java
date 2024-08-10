package com.guidopierri.pantrybe.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.exceptions.DabasException;
import com.guidopierri.pantrybe.models.DabasItem;
import com.guidopierri.pantrybe.models.SearchParams;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import com.guidopierri.pantrybe.repositories.DabasItemRepository;
import com.guidopierri.pantrybe.services.search.DabasItemSearchSpecification;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to Dabas data.
 * This includes fetching data from the DABAS API, validating API responses,
 * caching results, and interacting with the local database.
 * <p>
 * The class implements the DataProvider interface, which defines methods for fetching
 * and searching for articles in the DABAS API.
 * <p>
 * It uses the DabasItemRepository for database operations, the EntityMapper for converting
 * between DabasItem and DabasItemResponse objects, and the EntityManager for executing
 * custom database queries.
 * <p>
 * The class is annotated with @Service, indicating that it is a Spring service component.
 * It is also annotated with @Slf4j, which provides a logger for logging messages.
 */
@Service
@Slf4j
public class DabasDataService implements DataProvider {

    private final ItemService itemService;
    private final DabasItemRepository dabasItemRepository;
    private final EntityMapper entityMapper;
    private final EntityManager entityManager;
    @Value("${api-key}")
    private String apiKey;
    @Value("${dabas-api-url}")
    private String dabasApiUrl;
    @Autowired
    private Environment env;

    public DabasDataService(ItemService itemService, DabasItemRepository dabasItemRepository, EntityMapper entityMapper, EntityManager entityManager) {
        this.itemService = itemService;
        this.dabasItemRepository = dabasItemRepository;
        this.entityMapper = entityMapper;
        this.entityManager = entityManager;
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }

    /**
     * Fetches an article from the DABAS API using the provided GTIN number.
     * The method is cacheable, meaning that Spring will cache the result of the method
     * and, for subsequent calls with the same GTIN number, it will return the cached result
     * instead of calling the method again.
     *
     * @param gtinNumber The GTIN number of the article to fetch.
     * @return An Optional containing a DabasItemResponse if the article is found, or an empty Optional if not.
     * @throws Exception If there is an error during the API request or the JSON processing.
     * @Cacheable(value = "articles", key = "#gtinNumber") Annotation indicating that the result of the method
     * should be cached. The cache name is "articles" and the key for storing the result in the cache is the GTIN number.
     */
    @Cacheable(value = "articles", key = "#gtinNumber")
    @Override
    public Optional<DabasItemResponse> getArticle(String gtinNumber) throws Exception {
        String url = dabasApiUrl + "article/gtin/" + gtinNumber + "/JSON?apikey=" + apiKey;
        Optional<String> response = sendApiRequest(url);


        if (response.isEmpty()) {
            log.error("404 Error: Resource not found for search parameter: {}", gtinNumber);
            // or handle it in another way based on your requirements
        }
        if (response.isEmpty()) {
            return Optional.empty();
        }
        try {
            JsonNode jsonNode = validateResponse(response.get());
            String productName = jsonNode.path("Produktnamn").asText();
            String gtin = jsonNode.path("GTIN").asText();
            String brand = jsonNode.path("Varumarke").path("Varumarke").asText();
            String mainGroup = jsonNode.path("Varugrupp").path("HuvudgruppBenamning").asText();
            JsonNode bilder = jsonNode.path("Bilder");
            String imageLink = (bilder.isArray() && !bilder.isEmpty()) ? bilder.path(0).path("Lank").asText() : null;
            String size = jsonNode.path("Storlek").asText();
            String ingredients = jsonNode.path("Ingrediensforteckning").asText();
            ingredients = ingredients.replace("\r", "").replace("\n", "").replace("+", "").replace("*", "");
            String productClassifications = jsonNode.path("Produktkod").asText();
            String bruteWeight = jsonNode.path("Nettoinnehall").asText();
            String drainedWeightUnit = jsonNode.path("MangdFardigVaraEnhetKod").asText();
            String drainedWeight = jsonNode.path("MangdFardigVara_Formatted").asText() + " " + drainedWeightUnit;
            String level = jsonNode.path("Niva").asText();
            log.info("productName: {}", productName);
            log.info("gtin: {}", gtin);
            log.info("brand: {}", brand);
            log.info("imageLink: {}", imageLink);
            log.info("mainGroup: {}", mainGroup);
            log.info("size: {}", size);
            log.info("ingredients: {}", ingredients);
            log.info("productClassifications: {}", productClassifications);
            log.info("bruteWeight: {}", bruteWeight);
            log.info("drainedWeight: {}", drainedWeight);
            log.info("level: {}", level);

            return Optional.of(new DabasItemResponse(gtin,
                    productName,
                    brand,
                    imageLink,
                    mainGroup,
                    size,
                    ingredients,
                    productClassifications,
                    bruteWeight,
                    drainedWeight, level));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Validates the response from the DABAS API.
     *
     * @param response The response from the DABAS API as a String.
     * @return A JsonNode object representing the JSON structure of the response.
     * @throws JsonProcessingException If there is an error during the JSON processing.
     *                                 <p>
     *                                 This method checks if the response is null or empty, and if so, throws a DabasException.
     *                                 If the response is valid, it converts the response into a JsonNode object using the Jackson library
     *                                 and returns it.
     */
    private JsonNode validateResponse(String response) throws JsonProcessingException {
        if (response == null || response.isEmpty()) {
            throw new DabasException("Error validating DABAS data");
        }
        JsonNode jsonNode = getObjectMapper().readTree(response);
        jsonNode.isEmpty();
        return jsonNode;
    }

    /**
     * Fetches a list of Search objects from the DABAS API using the provided search parameter.
     * The search parameter is URL encoded and appended to the DABAS API URL.
     *
     * @param searchParameter The search parameter to use in the DABAS API request.
     * @return A List of Search objects representing the search results from the DABAS API.
     * @throws RuntimeException If there is an error during the API request or the JSON processing.
     *                          <p>
     *                          This method sends an API request to the DABAS API with the provided search parameter.
     *                          If the API response contains "Not Found", it logs an error and returns an empty list.
     *                          Otherwise, it converts the JSON response into a list of Search objects and returns it.
     */
    @Override
    public List<Search> getAllBaseArticleSearchResults(String searchParameter) {
        log.info("inside fetchUpaginatedSearch:");
        List<Search> searchList = new ArrayList<>();
        String encodedSearchParameter = searchParameter.replace(" ", "%20");
        String url = dabasApiUrl + "articles/basesearchparameter/" + encodedSearchParameter + "/JSON?apikey=" + apiKey;
        log.info("url: {}", url);
        String jsonString;

        try {
            jsonString = (sendApiRequest(url).get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // Check for 404 error
        if (jsonString.contains("Not Found")) {
            log.error("404 Error: Resource not found for search parameter: {}", searchParameter);
            return searchList; // or handle it in another way based on your requirements
        }
        log.info("jsonString: {}", jsonString);
        try {
            return Arrays.stream(getObjectMapper().readValue(jsonString, Search[].class)).toList();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sends an HTTP request to the specified URL and returns the response body as a String.
     *
     * @param url The URL to send the request to.
     * @return An Optional containing the response body as a String if the request was successful, or an empty Optional if the status code was 404.
     * @throws IOException          If an I/O error occurs when sending or receiving.
     * @throws InterruptedException If the operation is interrupted.
     *                              <p>
     *                              This method creates a new HttpClient and HttpRequest, sends the request, and records the time taken.
     *                              If the status code of the response is 404, it logs an error and returns an empty Optional.
     *                              Otherwise, it returns an Optional containing the response body as a String.
     */
    Optional<String> sendApiRequest(String url) throws IOException, InterruptedException {
        long startTime = System.currentTimeMillis();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/json")
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("HTTP request took {} milliseconds", duration);

        int statusCode = response.statusCode();
        if (statusCode == 404) {
            log.error("404 Error: Resource not found: {}", url);
            return Optional.empty();
            // Handle it as needed

        }
        return response.body().describeConstable();
    }

    /**
     * Searches for articles in the DABAS API using the provided search parameter.
     *
     * @param searchParameter The search parameter to use in the DABAS API request.
     * @return A List of DabasItemResponse objects representing the search results from the DABAS API.
     * <p>
     * This method first calls the fetchUpaginatedSearch method to get a list of Search objects from the DABAS API.
     * It then converts this list into a Set of DabasItemResponse objects by calling the dtoSet method.
     * Finally, it converts the Set back into a List and returns it.
     */
    public List<DabasItemResponse> search(String searchParameter) {
        List<Search> searchList = getAllBaseArticleSearchResults(searchParameter);
        log.info("searchList in search(): {}", searchList);

        Set<DabasItemResponse> dtos = dtoSet(searchList);
        return dtos.stream().toList();
    }

    public List<DabasItemResponse> searchInDatabase(String searchParameter) {
        SearchParams string = new SearchParams(searchParameter);
        //List<DabasItemResponse> searchList = searchDatabase(string, PageRequest.of(0, 10));
        List<DabasItemResponse> searchList = searchDatabaseUnpaginated(string);
        log.info("searchList in searchInDatabase(): {}", searchList);

        //Set<DabasItemResponse> dtos = dtoSet(searchList);
        return searchList;
    }


    private Set<DabasItemResponse> dtoSet(@NotNull List<Search> searchList) {
        return searchList.stream()
                .map(search -> getArticleByGtin(search.getGtin()).orElse(null))
                .filter(Objects::nonNull)
                .map(item -> {
                    return new DabasItemResponse(item.gtin(), item.name(), item.brand(), item.image(), item.category(), item.size(), item.ingredients(), item.productClassifications(), item.bruteWeight(), item.drainedWeight(), item.level());

                })
                .collect(Collectors.toSet());
    }

    /**
     * Searches for articles in the DABAS API using the provided search parameter and returns a page of results.
     * The method is cacheable, meaning that Spring will cache the result of the method
     * and, for subsequent calls with the same search parameter and page number, it will return the cached result
     * instead of calling the method again.
     *
     * @param searchParameter The search parameter to use in the DABAS API request.
     * @param page            The page number to fetch.
     * @param size            The number of results per page.
     * @return A Page of DabasItemResponse objects representing the search results from the DABAS API.
     * @Cacheable(value = "search", key = "{#searchParameter, #page}") Annotation indicating that the result of the method
     * should be cached. The cache name is "search" and the key for storing the result in the cache is a combination of the search parameter and the page number.
     */
    @Override
    @Cacheable(value = "search", key = "{#searchParameter, #page}")
    public Page<DabasItemResponse> searchToPageable(String searchParameter, int page, int size) {
        Pageable pageRequest = PageRequest.of(page, size);

        List<Search> searchList = getAllBaseArticleSearchResults(searchParameter);
        log.info("searchList: {}", searchList);
        int pageSize = pageRequest.getPageSize();
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageSize), searchList.size());
        var dtos = dtoSet(searchList);
        List<DabasItemResponse> pageContent = dtos.stream().toList().subList(start, end);
        return new PageImpl<>(pageContent, pageRequest, dtos.size());
    }

    /**
     * Fetches an article from the local database using the provided GTIN number.
     *
     * @param gtin The GTIN number of the article to fetch.
     * @return An Optional containing a DabasItemResponse if the article is found, or an empty Optional if not.
     * <p>
     * This method queries the local database for an article with the provided GTIN number.
     * If the article is found, it is converted to a DabasItemResponse and returned.
     * If the article is not found, an empty Optional is returned.
     */
    private Optional<DabasItemResponse> getArticleByGtin(String gtin) {
        DabasItem dabasItem = dabasItemRepository.findDabasItemByGtin(gtin).orElse(null);
        if (dabasItem == null) {
            return Optional.empty();
        }
        return Optional.of(entityMapper.dabasItemToDabasItemResponse(dabasItem));
    }

    /**
     * Sanitizes the local database by removing duplicate articles.
     *
     * @return A List of all DabasItem objects in the local database after the sanitization.
     * <p>
     * This method fetches all articles from the local database and groups them by GTIN number.
     * If any GTIN number has more than one associated article, all but one of these articles are deleted.
     * The method then returns a list of all remaining articles in the local database.
     */
    public List<DabasItem> sanitize() {

        List<DabasItem> allItems = dabasItemRepository.findAll();

        // Group the articles by GTIN and identify the duplicates
        Map<String, List<DabasItem>> groupedByGtin = allItems.stream()
                .collect(Collectors.groupingBy(DabasItem::getGtin));
        for (List<DabasItem> duplicates : groupedByGtin.values()) {
            if (duplicates.size() > 1) {
                // Keep the first article and get a list of the rest
                List<DabasItem> toDelete = duplicates.subList(1, duplicates.size());

                // Delete the duplicates
                dabasItemRepository.deleteAllInBatch(toDelete);
            }
        }
        log.info("All items: {}", allItems.size());
        log.info("Grouped by GTIN: {}", groupedByGtin.size());
        log.info("Duplicates: {}", allItems.size() - groupedByGtin.size());

        allItems = dabasItemRepository.findAll();
        log.info("All items: {}", allItems.size());
        return allItems;
    }

    /**
     * Saves a list of DabasItem objects to the database.
     *
     * @param items The list of DabasItem objects to save.
     *              <p>
     *              This method uses the DabasItemRepository to save the provided list of DabasItem objects to the database.
     *              It then flushes the persistence context, ensuring that any pending changes are actually written to the database.
     */
    public void saveAll(List<DabasItem> items) {
        dabasItemRepository.saveAllAndFlush(items);
    }

    /**
     * Seeds the database with articles.
     * <p>
     * This method reads a JSON file containing a list of DabasItem objects, converts the JSON into a list of DabasItem objects,
     * and then saves this list to the database using the saveAll method. If an error occurs during this process, it logs the error message.
     */
    public void seedArticles() {
        log.info("Seeding articles");
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<DabasItem>> typeReference = new TypeReference<>() {
        };
        InputStream inputStream = TypeReference.class.getResourceAsStream("/dabas-items/dabas_item.json");
        try {
            List<DabasItem> items = mapper.readValue(inputStream, typeReference);
            saveAll(items);
            log.info("{} Items seeded!", items.size());
        } catch (IOException e) {
            log.info("Unable to seed articles: {}", e.getMessage());
        }
    }

    /**
     * Fetches a list of DabasItemResponse objects from the database using the provided list of IDs.
     *
     * @param dabasItemsIds The list of IDs to fetch.
     * @return A list of DabasItemResponse objects.
     * <p>
     * This method uses the DabasItemRepository to fetch a list of DabasItem objects from the database using the provided list of IDs.
     * It then converts this list of DabasItem objects into a list of DabasItemResponse objects and returns it.
     */
    private List<DabasItemResponse> getDabasItemsByIds(List<Long> dabasItemsIds) {
        return entityMapper.convertListOfEmployeeToListOfEmployeeResponse(dabasItemRepository.findAllById(dabasItemsIds));
    }

    /**
     * Searches for articles in the database using the provided search parameters and pagination information.
     *
     * @param search   The search parameters to use.
     * @param pageable The pagination information to use.
     * @return A list of DabasItemResponse objects representing the search results.
     * <p>
     * This method uses the DabasItemSearchSpecification to create a specification based on the provided search parameters.
     * It then uses this specification to fetch a page of IDs from the database, converts this page of IDs into a list of IDs,
     * and fetches a list of DabasItemResponse objects from the database using this list of IDs. It then returns this list of DabasItemResponse objects.
     */
    public List<DabasItemResponse> searchDatabase(SearchParams search, Pageable pageable) {
        Page<Long> page = findIdsBySpecificationPage(new DabasItemSearchSpecification(search), pageable, DabasItem.class);
        return getDabasItemsByIds(page.getContent());
    }

    /**
     * Searches for articles in the database using the provided search parameters without pagination.
     *
     * @param string The search parameters to use.
     * @return A list of DabasItemResponse objects representing the search results.
     * <p>
     * This method uses the DabasItemSearchSpecification to create a specification based on the provided search parameters.
     * It then uses this specification to fetch a list of IDs from the database, converts this list of IDs into a list of IDs,
     * and fetches a list of DabasItemResponse objects from the database using this list of IDs. It then returns this list of DabasItemResponse objects.
     */
    private List<DabasItemResponse> searchDatabaseUnpaginated(SearchParams string) {
        List<DabasItemResponse> dabasItemResponseList = new ArrayList<>();
        findIdsBySpecificationUpaged(new DabasItemSearchSpecification(string), DabasItem.class).forEach(id -> {
            dabasItemRepository.findById(id).ifPresent(dabasItem -> dabasItemResponseList.add(entityMapper.dabasItemToDabasItemResponse(dabasItem)));
        });
        return dabasItemResponseList;

    }

    /**
     * Fetches a page of IDs from the database using the provided specification and pagination information.
     *
     * @param specification The specification to use.
     * @param pageable      The pagination information to use.
     * @param clazz         The class of the objects to fetch.
     * @return A page of IDs.
     * <p>
     * This method uses the provided specification to create a CriteriaQuery for fetching a page of IDs from the database.
     * It then executes this query and returns the result as a page of IDs.
     */
    public <T> Page<Long> findIdsBySpecificationPage(Specification<T> specification,
                                                     Pageable pageable, Class<T> clazz) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(clazz);
        query.select(root.get("id"))
                .where(specification.toPredicate(root, query, builder))
                .orderBy(builder.asc(root.get(DabasItemSearchSpecification.NAME)));

        var result = entityManager.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize());

        CriteriaBuilder countBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = countBuilder.createQuery(Long.class);
        Root<T> countRoot = countQuery.from(clazz);
        countQuery.select(countBuilder.count(countRoot))
                .where(specification.toPredicate(countRoot, countQuery, countBuilder));

        var totalElements = entityManager.createQuery(countQuery).getSingleResult();
        return new PageImpl<>(result.getResultList(), pageable, totalElements);
    }

    public <T> List<Long> findIdsBySpecificationUpaged(Specification<T> specification, Class<T> clazz) {

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> root = query.from(clazz);
        query.select(root.get("id"))
                .where(specification.toPredicate(root, query, builder))
                .orderBy(builder.asc(root.get(DabasItemSearchSpecification.NAME)));

        return entityManager.createQuery(query).getResultList();
    }

    /**
     * Checks if the database is empty, and if so, seeds it with articles.
     * <p>
     * This method checks if the database is empty by counting the number of DabasItem objects in it.
     * If the count is zero, it calls the seedArticles method to seed the database with articles.
     */
    @Profile("!test")
    public void checkAndSeedArticles() {
        if (dabasItemRepository.count() == 0) {
            seedArticles();
        }
    }

    /**
     * Initializes the service by checking and seeding the database with articles.
     * <p>
     * This method is annotated with @PostConstruct, meaning it is automatically called by Spring after the service is constructed and before it is used.
     * It calls the checkAndSeedArticles method to check if the database is empty and seed it with articles if necessary.
     */
    @PostConstruct
    @Profile("!test")
    public void init() {
        String[] activeProfiles = env.getActiveProfiles();
        boolean isTestProfileActive = Arrays.asList(activeProfiles).contains("test");
        if (!isTestProfileActive) {
            checkAndSeedArticles();
        }

    }
}
