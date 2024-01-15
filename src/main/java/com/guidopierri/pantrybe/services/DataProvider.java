package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.models.dabas.article.Article;
import com.guidopierri.pantrybe.models.dabas.search.Search;

import java.util.List;

public interface DataProvider {
    Article getArticle(String gtin);
    List<Search>search(String searchParameter);
}
