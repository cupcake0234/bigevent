package com.sptj.controller;

import com.sptj.pojo.Category;
import com.sptj.pojo.Result;
import com.sptj.service.CategoryService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Category.Add.class) Category category){
        categoryService.add(category);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<Category>> list(){
        List<Category> cs = categoryService.list();
        return Result.success(cs);
    }

    @GetMapping("/detail")
    public Result detail(Integer id){
        Category category = categoryService.findById(id);
        return Result.success(category);
    }


    @PutMapping("/update")
    public Result update(@RequestBody @Validated(Category.Update.class) Category category){
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }
}
