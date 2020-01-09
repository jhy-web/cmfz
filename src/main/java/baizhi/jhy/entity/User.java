package baizhi.jhy.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor

@HeadRowHeight(20)    //头部行高
@ContentRowHeight(60) //内容行高
@ColumnWidth(20)      //列宽
public class User {
    @ExcelProperty(value = {"用户", "ID"})
    @Id
    private String id;
    @ExcelProperty(value = {"用户", "phone"})
    private String phone;
    @ExcelProperty(value = {"用户", "password"})
    private String password;
    @ExcelProperty(value = {"用户", "salt"})
    private String salt;
    @ExcelProperty(value = {"用户", "status"})
    private String status;
    @ExcelProperty(value = {"用户", "photo"})
    private String photo;
    @ExcelProperty(value = {"用户", "name"})
    private String name;
    @ExcelProperty(value = {"用户", "nickName"})
    private String nick_name;
    @ExcelProperty(value = {"用户", "sex"})
    private String sex;
    @ExcelProperty(value = {"用户", "sign"})
    private String sign;
    @ExcelProperty(value = {"用户", "location"})
    private String location;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = {"用户", "rigestDate"})
    private Date rigest_date;
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = {"用户", "lastLogin"})
    private Date last_login;

}
