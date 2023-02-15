package kz.careerguidance;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CareerGuidanceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CareerGuidanceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	//Change from Mamedov2001
}
