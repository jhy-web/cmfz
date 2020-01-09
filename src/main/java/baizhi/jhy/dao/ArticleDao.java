package baizhi.jhy.dao;

import baizhi.jhy.entity.Article;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ArticleDao extends Mapper<Article> {// extends Mapper

    //查询分页
    List<Article> selectPage(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize);

    //查询总行数
    int selectTotalCount();

    //添加
    void addArticle(Article article);

    //删除
    void deleteArticle(String id);

    //修改
    void updateArticle(Article article);

    //通过id查询文章
    public Article selectByOne(String id);

    public List<Article> selectArticleDate();
}
