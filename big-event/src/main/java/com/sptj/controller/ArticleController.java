package com.sptj.controller;

import com.sptj.pojo.Article;
import com.sptj.pojo.Category;
import com.sptj.pojo.PageBean;
import com.sptj.pojo.Result;
import com.sptj.service.ArticleService;
import com.sptj.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public Result<PageBean<Article>> list(Integer pageNum,Integer pageSize,@RequestParam(required = false) Integer categoryId,@RequestParam(required = false)String state){
        //
//        try{
//            Map<String,Object> claims = JwtUtil.parseToken(token);
//            return Result.success("所有文章数据");
//        }catch (Exception e){
//            //
//            response.setStatus(401);
//            return Result.error("未登录");
//        }
        PageBean<Article> pb = articleService.list(pageNum,pageSize,categoryId,state);
        return Result.success(pb);
    }

    @PostMapping("/add")
    public Result add(@RequestBody @Validated Article article){
        articleService.add(article);
        return Result.success();
    }

    @GetMapping("/detail")
    public Result detail(Integer id){
        Article article = articleService.findById(id);
        return Result.success(article);
    }

    @PostMapping("/update")
    public Result update(@RequestBody  @Validated(Article.Update.class) Article article){
        articleService.update(article);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        articleService.delete(id);
        return Result.success();
    }
}
