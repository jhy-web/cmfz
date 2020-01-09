package baizhi.jhy.controller;

import baizhi.jhy.dao.GuruDao;
import baizhi.jhy.entity.Guru;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("guru")
public class GuruController {

    @Autowired
    GuruDao guruDao;

    @RequestMapping("showAllGuru")
    public List<Guru> guru() {
        List<Guru> gurus = guruDao.selectAll();
        return gurus;
    }
}

