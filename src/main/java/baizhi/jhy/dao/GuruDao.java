package baizhi.jhy.dao;

import baizhi.jhy.entity.Guru;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface GuruDao extends Mapper<Guru> {
    //查所有
    public List<Guru> queryAll();

    public void insertGuru(Guru guru);
}
