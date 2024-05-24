package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.models.DabasItem;
import com.guidopierri.pantrybe.services.DabasDataService;
import com.guidopierri.pantrybe.services.DabasSearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/search")
public class DabasController {
    private static final Logger log = LoggerFactory.getLogger(DabasController.class);
    private final DabasDataService dabasDataService;
    private final DabasSearchService dabasSearchService;

    public DabasController(DabasDataService dabasDataService, DabasSearchService dabasSearchService) {
        this.dabasDataService = dabasDataService;
        this.dabasSearchService = dabasSearchService;
    }

    private final String externalApiBaseUrl = "https://api.dabas.com/DABASService/V2";

    //   private final String externalApiBaseUrlSeach = "https://api.dabas.com/DABASService/V2";
    @GetMapping("/product/{gtin}")
    public ResponseEntity<Optional<DabasItemResponse>> getProductByGtin(@PathVariable String gtin) throws Exception {
        return dabasSearchService.getProductByGtin(gtin);
    }

    @GetMapping("/parameter/{searchParameter}")
    public ResponseEntity<List<DabasItemResponse>> fetchProductBySearchParameter(@PathVariable String searchParameter) {

        return new ResponseEntity<>(dabasDataService.search(searchParameter), HttpStatus.OK);
    }

    @GetMapping("/paginated/parameter/{searchParameter}")
    public ResponseEntity<Page<DabasItemResponse>> fetchProductBySearchParameterPageable(
            @PathVariable String searchParameter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws Exception {
        log.info("searchParameter: {}", searchParameter);

        Page<DabasItemResponse> searchPage = dabasDataService.searchToPageable(searchParameter, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(searchPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(searchPage.getSize()));
        log.debug("searchPage: {}", searchPage);
        return ResponseEntity.ok().headers(headers).body(searchPage);

    }

    @GetMapping("/import")
    public ResponseEntity<List<DabasItemResponse>> importArticles() throws Exception {
        return new ResponseEntity<>(dabasDataService.importArticlesGtin(), HttpStatus.OK);
    }

    @PostMapping("/sanitize")
    public ResponseEntity<List<DabasItem>> sanitizeArticles() {
        return new ResponseEntity<>(dabasDataService.sanitize(), HttpStatus.OK);
    }
}
