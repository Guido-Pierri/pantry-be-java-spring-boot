package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DabasSearchService {
    private final DabasDataService dabasDataService;

    public DabasSearchService(DabasDataService dabasDataService) {
        this.dabasDataService = dabasDataService;
    }

    public ResponseEntity<DabasItemResponse> getProductByGtin(String gtin) throws Exception {
        DabasItemResponse article = dabasDataService.getArticle(gtin);
        if (article == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<>(article, HttpStatus.OK);
    }
}
