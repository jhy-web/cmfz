package baizhi.jhy.dao;

import baizhi.jhy.entity.Album;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AlbumDao extends Mapper<Album> {// extends Mapper

    //查询分页
    List<Album> selectPage(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize);

    //查询总行数
    int selectTotalCount();

    //添加
    void addAlbum(Album album);

    //删除
    void deleteAlbum(String id);

    //修改
    void updateAlbum(Album album);

    //查所有
    public Album selectByIdAlbum(String id);

    //随机两条
    public List<Album> selectByDate();
}
