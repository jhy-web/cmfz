package baizhi.jhy.test.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@HeadRowHeight(20)    //头部行高
@ContentRowHeight(60) //内容行高
@ColumnWidth(20)      //列宽
public class ImgData {

    @ExcelProperty(value = "图片", converter = ImgConverter.class)
    private String string;
}
