package baizhi.jhy.test.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;

/*
* invoke : 读取每行数据后会执行的方法
  doAfterAllAnalysed: 所有数据读取完毕后执行的方法
* */
public class DemoDataListener extends AnalysisEventListener<DemoData> {

    ArrayList<DemoData> list = new ArrayList<>();

    @Override
    public void invoke(DemoData demoData, AnalysisContext analysisContext) {
        //参数：DemoData  针对每行数据 进行实体类封装
        list.add(demoData);
        System.out.println(demoData);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println(list);
    }
}
