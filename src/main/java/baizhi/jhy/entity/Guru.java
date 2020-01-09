package baizhi.jhy.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guru {

    private String id;
    private String name;
    private String photo;
    private String status;
    private String nickName;

}
