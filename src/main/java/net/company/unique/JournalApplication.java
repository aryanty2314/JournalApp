
package net.company.unique;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);

	}
     @Bean
	public PlatformTransactionManager manager(MongoDatabaseFactory db)
	 {
       return new MongoTransactionManager(db);
	 }
	 @Bean
	 public RestTemplate restTemplate()
	 {
		 RestTemplate restTemplate = new RestTemplate();
		 restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter());
		 return restTemplate;

	 }
}
