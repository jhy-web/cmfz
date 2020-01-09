package baizhi.jhy.service;

import baizhi.jhy.entity.Banner;
import baizhi.jhy.entity.BannerPageDto;

public interface BannerService {
    //分页
    BannerPageDto selectPage(int pageNow, int pageSize);

    //添加
    void addBanner(Banner banner);

    //删除
    void deleteBanner(String id);

    //修改
    void updateBanner(Banner banner);

}
