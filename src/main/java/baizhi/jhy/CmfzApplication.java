package baizhi.jhy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
/*@tk.mybatis.spring.annotation.MapperScan("baizhi.jhy.dao")*/
/*@org.mybatis.spring.annotation.MapperScan("baizhi.jhy.dao")*/
@MapperScan("baizhi.jhy.dao")
public class CmfzApplication {

    public static void main(String[] args) {
        SpringApplication.run(CmfzApplication.class, args);
    }

}
