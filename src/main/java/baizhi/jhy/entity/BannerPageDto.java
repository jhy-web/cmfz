package baizhi.jhy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BannerPageDto implements Serializable {
    private Integer page;//当前页
    private Integer total;//总页数
    private Integer records;//总行数
    private List<Banner> rows;
}