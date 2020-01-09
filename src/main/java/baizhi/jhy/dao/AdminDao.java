package baizhi.jhy.dao;

import baizhi.jhy.entity.Admin;
import tk.mybatis.mapper.common.Mapper;

public interface AdminDao extends Mapper<Admin> {
    public Admin selectByAdminNames(String username, String password);
}
