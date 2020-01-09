package baizhi.jhy.service;

import baizhi.jhy.dao.BannerDao;
import baizhi.jhy.entity.Banner;
import baizhi.jhy.entity.BannerPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {

    @Autowired
    BannerDao bnnerDao;


    //修改
    @Override
    public void updateBanner(Banner banner) {
        bnnerDao.updateBanner(banner);
    }

    //删除
    @Override
    public void deleteBanner(String id) {
        bnnerDao.deleteBanner(id);
    }

    //添加
    @Override
    public void addBanner(Banner banner) {
        bnnerDao.addBanner(banner);
    }

    //分页
    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public BannerPageDto selectPage(int pageNow, int pageSize) {
        BannerPageDto pageDto = new BannerPageDto();
        pageDto.setPage(pageNow);// 设置当前页
        int totalCount = bnnerDao.selectTotalCount();
        pageDto.setRecords(totalCount);// 设置总行数
        pageDto.setTotal(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);// 设置总页
        pageDto.setRows(bnnerDao.selectPage(pageNow, pageSize));// 设置当前页的数据行
        return pageDto;
    }
}
