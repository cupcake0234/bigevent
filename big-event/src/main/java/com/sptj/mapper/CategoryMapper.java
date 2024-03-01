package com.sptj.mapper;

import com.sptj.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("select * from category where id =#{id}")
    Category findById(Integer id);

    @Insert("Insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);

    @Select("Select * from category where create_user = #{userId}")
    List<Category> list(Integer userId);

    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id = #{id}")
    void update(Category category);

    @Delete("delete from category where id = #{id}")
    void deleteById(Integer id);
}