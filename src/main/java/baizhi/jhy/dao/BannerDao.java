package baizhi.jhy.dao;

import baizhi.jhy.entity.Banner;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BannerDao extends Mapper<Banner> {

    //一级页面展示接口
    List<Banner> selectAllBanner();

    //查询分页
    List<Banner> selectPage(@Param("pageNow") int pageNow, @Param("pageSize") int pageSize);

    //查询总行数
    int selectTotalCount();

    //添加
    void addBanner(Banner banner);

    //删除
    void deleteBanner(String id);

    //修改
    void updateBanner(Banner banner);

}
