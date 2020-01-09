package baizhi.jhy.service;

import baizhi.jhy.entity.UserPageDto;

public interface UserService {
    //分页
    UserPageDto selectPage(int pageNow, int pageSize);
}
