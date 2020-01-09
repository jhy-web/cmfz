package baizhi.jhy.entity;

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
public class Album {
    @Id
    private String id;
    private String title;//标题
    private String score;//评分
    private String author;//作者
    private String broadcast;//播音
    private Integer counts;//章节数量
    private String des;//封面
    private String status;//内容简介
    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createDate;//发布日期

}
