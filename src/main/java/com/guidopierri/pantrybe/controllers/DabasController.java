package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.responses.ItemResponse;
import com.guidopierri.pantrybe.models.dabas.article.Article;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import com.guidopierri.pantrybe.services.DabasDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v2/search")
public class DabasController {
    private final DabasDataService dabasDataService;
    public DabasController(DabasDataService dabasDataService) {
        this.dabasDataService = dabasDataService;
    }
    private final String externalApiBaseUrl = "https://api.dabas.com/DABASService/V2";
    //   private final String externalApiBaseUrlSeach = "https://api.dabas.com/DABASService/V2";
    @GetMapping("/product/{gtin}")
    public ResponseEntity<ItemResponse> fetchProductByGtin(@PathVariable String gtin) {
        Article article = dabasDataService.getArticle(gtin);
        ItemResponse item = new ItemResponse();
        item.name = article.artikelbenamning;
        item.brand = article.varumarke.varumarke;
        item.category = article.artikelkategori;
        //item.expiryDate = article.hyllkantstext;
        //item.quantity = article.forpackningsstorlek;
        item.image = article.bilder.get(1).lank;
        item.gtin = article.gTIN;
        return new ResponseEntity<>(item, HttpStatus.OK);
    }
    @GetMapping("/parameter/{searchParameter}")
    public ResponseEntity<List<Search>> fetchProductBySearchParameter(@PathVariable String searchParameter) {
    /*
    * FIXME: Pageable response
    */
        return new ResponseEntity<>(dabasDataService.search(searchParameter), HttpStatus.OK);
    }
}
