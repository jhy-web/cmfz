package baizhi.jhy.test;

import baizhi.jhy.test.entity.DemoData;
import baizhi.jhy.test.entity.DemoDataListener;
import baizhi.jhy.test.entity.ImgData;
import com.alibaba.excel.EasyExcel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestEasyExcel {

    //导出
    @Test
    public void testEasyExcelOut() {
        /*
         * write(): 参数1：文件路径，参数2：实体类，
         * sheet: 指定写入工作薄的名称
         * doWrite(list数据)
         * 如果需要下载使用 参数1：outputSteam，参数2：实体类.class
         * */
        String fileName = "E:\\后期项目\\day7-poiEasyExcel\\EasyExcel\\" + new Date().getTime() + ".xls";
        EasyExcel.write(fileName, DemoData.class)
                .sheet("测试")
                .doWrite(Arrays.asList(new DemoData(UUID.randomUUID().toString(), 23, new Date(), "web"), new DemoData(UUID.randomUUID().toString(), 23, new Date(), "jhy")));
    }

    //导入
    @Test
    public void testEadyExcelIn() {
        /*
         * readListener: 读取数据时的监听器，每次使用DemoDataListener都要new，不能把每次使用DemoDataListener都要new交给工厂（单例模式）管理，
         * 文件上传：Mfile url,   File file = new File;
         * */
        String url = "E:\\后期项目\\day7-poiEasyExcel\\EasyExcel\\1577966910506.xls";
        EasyExcel.read(url, DemoData.class, new DemoDataListener()).sheet().doRead();
    }

    //导出图片
    @Test
    public void testImg() {
        /*
         * write(): 参数1：文件路径，参数2：实体类，
         * sheet: 指定写入工作薄的名称
         * doWrite(list数据)
         * 如果需要下载使用 参数1：outputSteam，参数2：实体类.class
         * */
        String fileName = "E:\\后期项目\\day7-poiEasyExcel\\EasyExcel\\" + new Date().getTime() + ".xls";
        ImgData imgData = new ImgData("imgConverter/yb.jpg");
        EasyExcel.write(fileName, ImgData.class)
                .sheet("测试图片")
                .doWrite(Arrays.asList(imgData));
    }
}
