package baizhi.jhy.test.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//不能使用链式调用
public class DemoData {
    //@ExcelProperty 声明表头信息

    @ExcelProperty(value = {"班级", "ID"})
    private String id;
    @ExcelProperty(value = {"班级", "年龄"})
    private Integer age;
    @ExcelProperty(value = {"班级", "日期"})
    private Date date;

    //@ExcelIgnore 导出数据时 忽略该属性
    @ExcelIgnore
    private String ignore;
}
