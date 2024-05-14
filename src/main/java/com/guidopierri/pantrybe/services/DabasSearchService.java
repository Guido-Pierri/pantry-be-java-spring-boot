package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DabasSearchService {
    private final DabasDataService dabasDataService;

    public DabasSearchService(DabasDataService dabasDataService) {
        this.dabasDataService = dabasDataService;
    }

    public ResponseEntity<Optional<DabasItemResponse>> getProductByGtin(String gtin) throws Exception {
        Optional<DabasItemResponse> article = dabasDataService.getArticle(gtin);
        if (article.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(article, HttpStatus.OK);
    }
}
