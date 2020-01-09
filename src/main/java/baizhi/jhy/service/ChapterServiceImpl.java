package baizhi.jhy.service;

import baizhi.jhy.dao.ChapterDao;
import baizhi.jhy.entity.Chapter;
import baizhi.jhy.entity.ChapterPageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterDao chapterDao;

    @Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
    @Override
    public ChapterPageDto selectPage(String id, int pageNow, int pageSize) {
        ChapterPageDto ahapterPageDto = new ChapterPageDto();
        ahapterPageDto.setPage(pageNow);//设置当前页
        int totalCount = chapterDao.selectTotalCount();//获取总条数（调用dao方法）
        ahapterPageDto.setRecords(totalCount);//设置总行数
        ahapterPageDto.setTotal(totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1);// 设置总页数
        ahapterPageDto.setRows(chapterDao.selectPage(id, pageNow, pageSize));// 设置当前页的数据行
        return ahapterPageDto;
    }

    @Override
    public void addChapter(Chapter chapter) {
        chapterDao.addChapter(chapter);
    }

    @Override
    public void deleteChapter(String id) {
        chapterDao.deleteChapter(id);
    }

    @Override
    public void updateChapter(Chapter chapter) {
        chapterDao.updateChapter(chapter);
    }
}
