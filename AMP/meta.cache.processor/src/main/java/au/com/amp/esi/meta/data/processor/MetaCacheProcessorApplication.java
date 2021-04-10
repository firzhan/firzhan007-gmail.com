package au.com.amp.esi.meta.data.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = "au.com.amp.esi.meta.cache.processor")
@PropertySource("classpath:application.yml")
public class MetaCacheProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetaCacheProcessorApplication.class, args);
	}

}
