package org.juhewu.data.springboot.simple.all;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 数据应用
 *
 * @author duanjw
 * @since 2022/09/27
 */
@SpringBootApplication
@MapperScan("org.juhewu.data.springboot.simple.all.mapper")
public class JuhewuDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuhewuDataApplication.class, args);
    }
}
