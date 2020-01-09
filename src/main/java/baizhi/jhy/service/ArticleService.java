package baizhi.jhy.service;

import baizhi.jhy.entity.Article;
import baizhi.jhy.entity.ArticlePageDto;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface ArticleService {
    //分页
    ArticlePageDto selectPage(int pageNow, int pageSize);

    //添加
    void addArticle(Article article);

    //删除
    void deleteArticle(String id);

    //修改
    void updateArticle(Article article);

    //文件上传
    public String articleUploa(MultipartFile inputfile, String id, HttpServletRequest request);
}
