package o;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan
@MapperScan(basePackages="o.map")
@PropertySource(value="classpath:va.properties")
public class App {
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
