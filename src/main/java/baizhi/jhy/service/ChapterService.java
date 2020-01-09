package baizhi.jhy.service;

import baizhi.jhy.entity.Chapter;
import baizhi.jhy.entity.ChapterPageDto;

public interface ChapterService {
    //分页
    ChapterPageDto selectPage(String id, int pageNow, int pageSize);

    //添加
    void addChapter(Chapter chapter);

    //删除
    void deleteChapter(String id);

    //修改
    void updateChapter(Chapter chapter);
}
