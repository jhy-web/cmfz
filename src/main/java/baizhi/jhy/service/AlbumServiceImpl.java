package baizhi.jhy.service;

import baizhi.jhy.dao.AlbumDao;
import baizhi.jhy.entity.Album;
import baizhi.jhy.entity.AlbumPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional//事务
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumDao albumDao;

    //分页
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public AlbumPageDto selectPage(int pageNow, int pageSize) {
        AlbumPageDto albumPageDto = new AlbumPageDto();
        albumPageDto.setPage(pageNow);//设置当前页
        int totalCount = albumDao.selectTotalCount();//获取总条数
        albumPageDto.setRecords(totalCount);//设置总行数
        albumPageDto.setTotal(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);// 设置总页
        albumPageDto.setRows(albumDao.selectPage(pageNow, pageSize));// 设置当前页的数据行
        return albumPageDto;
    }

    //添加
    @Override
    public void addAlbum(Album album) {
        albumDao.addAlbum(album);
    }

    //删除
    @Override
    public void deleteAlbum(String id) {
        albumDao.deleteAlbum(id);
    }

    //修改
    @Override
    public void updateAlbum(Album album) {
        albumDao.updateAlbum(album);
    }
}
