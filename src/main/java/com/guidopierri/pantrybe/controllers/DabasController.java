package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.models.DabasItem;
import com.guidopierri.pantrybe.services.DabasDataService;
import com.guidopierri.pantrybe.services.DabasImportService;
import com.guidopierri.pantrybe.services.DabasSearchService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/search")
public class DabasController {
    private static final Logger log = LoggerFactory.getLogger(DabasController.class);
    private final DabasDataService dabasDataService;
    private final DabasSearchService dabasSearchService;
    private final DabasImportService dabasImportService;

    public DabasController(DabasDataService dabasDataService, DabasSearchService dabasSearchService, DabasImportService dabasImportService) {
        this.dabasDataService = dabasDataService;
        this.dabasSearchService = dabasSearchService;
        this.dabasImportService = dabasImportService;
    }

    @Operation(summary = "Get a product by gtin number.")
    @GetMapping("/product/{gtin}")
    public ResponseEntity<Optional<DabasItemResponse>> getProductByGtin(@PathVariable String gtin) throws Exception {
        return dabasSearchService.getProductByGtin(gtin);
    }

    @Operation(summary = "Get all products by search parameter.")
    @GetMapping("/parameter/{searchParameter}")
    public ResponseEntity<List<DabasItemResponse>> getAllProductsBySearchParameter(@PathVariable String searchParameter) {

        return new ResponseEntity<>(dabasDataService.searchInDatabase(searchParameter), HttpStatus.OK);
    }

    @Operation(summary = "Get all products paginated by search parameter.")
    @GetMapping("/paginated/parameter/{searchParameter}")
    public ResponseEntity<Page<DabasItemResponse>> getAllProductsBySearchParameterPageable(
            @PathVariable String searchParameter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("searchParameter: {}", searchParameter);

        Page<DabasItemResponse> searchPage = dabasDataService.searchToPageable(searchParameter, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(searchPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(searchPage.getSize()));
        log.debug("searchPage: {}", searchPage);
        return ResponseEntity.ok().headers(headers).body(searchPage);

    }

    @Operation(summary = "Import all articles from DABAS API.")
    @GetMapping("/import")
    public ResponseEntity<List<DabasItemResponse>> importArticles() throws Exception {
        return new ResponseEntity<>(dabasImportService.importArticlesGtin(), HttpStatus.OK);
    }

    @Operation(summary = "Sanitize all articles imported from DABAS API.")
    @PostMapping("/sanitize")
    public ResponseEntity<List<DabasItem>> sanitizeArticles() {
        return new ResponseEntity<>(dabasDataService.sanitize(), HttpStatus.OK);
    }
}
