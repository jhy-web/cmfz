package baizhi.jhy.test;

import baizhi.jhy.dao.BannerDao;
import baizhi.jhy.entity.Banner;
import org.apache.poi.hssf.usermodel.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPoi {
    /*
     *  实战实例  poi与项目集成
     * */
    @Autowired
    BannerDao bannerDao;

    @Test
    public void testPoi() {
        //创建 excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作薄
        HSSFSheet sheet = workbook.createSheet();
        //设置列宽度
        sheet.setColumnWidth(3, 15 * 256);
        //创建行对象
        HSSFRow row = sheet.createRow(0);
        //设置行高
        row.setHeight((short) 2000);
        //创建一个单元格
        HSSFCell cell = row.createCell(1);
        //给单元格设置内容
        cell.setCellValue("腊八节");
        //导出单元格
        try {
            workbook.write(new File("E:\\后期项目\\day7-poiEasyExcel\\EasyPoi\\" + new Date().getTime() + ".xls"));
            //释放资源
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testYes() {
        //查询所有数据
        List<Banner> banners = bannerDao.selectAll();
        //将 banners 放在excel表格中
        //创建 excel 文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //创建工作薄
        HSSFSheet sheet = workbook.createSheet();
        //创建行对象 参数：行下标（从0开始）
        HSSFRow row = sheet.createRow(0);
        //设置表对象信息
        String[] str = {"ID", "标题", "图片", "超链接", "创建时间", "描述", "状态"};
//        row.createCell(0).setCellValue(str[0]);
        for (int i = 0; i < str.length; i++) {
            String s = str[i];
            row.createCell(i).setCellValue(s);
        }
        // 通过workbook对象获取样式对象
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        // 通过workbook对象获取数据格式化处理对象
        HSSFDataFormat dataFormat = workbook.createDataFormat();
        // 指定格式化样式 如 yyyy-MM-dd
        short format = dataFormat.getFormat("yyyy-MM-dd");
        // 为样式对象 设置格式化处理
        cellStyle.setDataFormat(format);
        for (int i = 0; i < banners.size(); i++) {
            Banner banner = banners.get(i);
            //给每个对象设置 一行
            HSSFRow row1 = sheet.createRow(i + 1);
            row1.createCell(0).setCellValue(banner.getId());
            row1.createCell(1).setCellValue(banner.getTitle());
            row1.createCell(2).setCellValue(banner.getUrl());
            row1.createCell(3).setCellValue(banner.getHref());
            HSSFCell cell = row1.createCell(4);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(banner.getCreateDate());
            row1.createCell(5).setCellValue(banner.getDes());
            row1.createCell(6).setCellValue(banner.getStatus());
        }
        try {
            workbook.write(new File("E:\\后期项目\\day7-poiEasyExcel\\EasyPoi\\" + new Date().getTime() + ".xls"));
            workbook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}