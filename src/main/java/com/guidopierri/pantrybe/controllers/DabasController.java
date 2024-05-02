package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import com.guidopierri.pantrybe.services.DabasDataService;
import com.guidopierri.pantrybe.services.DabasSearchService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/search")
public class DabasController {
    private final DabasDataService dabasDataService;
    private final DabasSearchService dabasSearchService;

    public DabasController(DabasDataService dabasDataService, DabasSearchService dabasSearchService) {
        this.dabasDataService = dabasDataService;
        this.dabasSearchService = dabasSearchService;
    }

    private final String externalApiBaseUrl = "https://api.dabas.com/DABASService/V2";

    //   private final String externalApiBaseUrlSeach = "https://api.dabas.com/DABASService/V2";
    @GetMapping("/product/{gtin}")
    public ResponseEntity<DabasItemResponse> getProductByGtin(@PathVariable String gtin) throws Exception {
        return dabasSearchService.getProductByGtin(gtin);
    }

    @GetMapping("/parameter/{searchParameter}")
    public ResponseEntity<List<Search>> fetchProductBySearchParameter(@PathVariable String searchParameter) {
        /*
         * FIXME: Pageable response
         *  https://www.baeldung.com/spring-data-jpa-pagination-sorting
         *  https://www.baeldung.com/rest-api-pagination-in-spring
         */
        return new ResponseEntity<>(dabasDataService.fetchUpaginatedSearch(searchParameter), HttpStatus.OK);
    }

    @GetMapping("/paginated/parameter/{searchParameter}")
    public ResponseEntity<Page<Search>> fetchProductBySearchParameterPageable(
            @PathVariable String searchParameter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Search> searchPage = dabasDataService.searchToPageable(searchParameter, page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(searchPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(searchPage.getSize()));
        return ResponseEntity.ok().headers(headers).body(searchPage);

    }
}
