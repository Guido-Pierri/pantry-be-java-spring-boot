package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.models.dabas.article.Article;
import com.guidopierri.pantrybe.models.dabas.search.Search;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DataProvider {
    Article getArticle(String gtin);

    List<Search> fetchUpaginatedSearch(String searchParameter);

    Page<Search> searchToPageable(String searchParameter, int page, int size);
}


