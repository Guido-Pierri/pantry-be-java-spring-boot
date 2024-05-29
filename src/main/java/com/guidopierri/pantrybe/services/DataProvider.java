package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface DataProvider {

    Optional<DabasItemResponse> getArticle(String gtin) throws Exception;

    List<Search> getAllBaseArticleSearchResults(String searchParameter);

    Page<DabasItemResponse> searchToPageable(String searchParameter, int page, int size) throws Exception;
}


