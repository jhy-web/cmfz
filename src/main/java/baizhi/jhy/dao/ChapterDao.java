package baizhi.jhy.dao;

import baizhi.jhy.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {// extends Mapper

    //查询分页
    List<Chapter> selectPage(@Param("id") String id, @Param("pageNow") int pageNow, @Param("pageSize") int pageSize);

    //查询总行数
    int selectTotalCount();

    //添加
    void addChapter(Chapter chapter);

    //删除
    void deleteChapter(String id);

    //修改
    void updateChapter(Chapter chapter);

    //通过专辑id
    public List<Chapter> selectAllChapter(String id);
}
