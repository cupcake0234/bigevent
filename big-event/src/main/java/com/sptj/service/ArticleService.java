package com.sptj.service;

import com.sptj.pojo.Article;
import com.sptj.pojo.PageBean;

public interface ArticleService {
    void add(Article article);

    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    Article findById(Integer id);

    void update(Article article);

    void delete(Integer id);
}
