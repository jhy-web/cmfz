package baizhi.jhy.service;

import baizhi.jhy.entity.Album;
import baizhi.jhy.entity.AlbumPageDto;

public interface AlbumService {
    //分页
    AlbumPageDto selectPage(int pageNow, int pageSize);

    //添加
    void addAlbum(Album album);

    //删除
    void deleteAlbum(String id);

    //修改
    void updateAlbum(Album album);
}
