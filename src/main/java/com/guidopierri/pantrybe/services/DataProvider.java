package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataProvider {
    DabasItemResponse getArticle(String gtin) throws Exception;

    List<Search> fetchUpaginatedSearch(String searchParameter);

    Page<Search> searchToPageable(String searchParameter, int page, int size);
}


