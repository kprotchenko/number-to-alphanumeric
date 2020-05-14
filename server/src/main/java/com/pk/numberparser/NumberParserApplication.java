package com.pk.numberparser;
import com.pk.numberparser.enteties.Number;
import com.pk.numberparser.repositories.PhoneNumberRepository;
import com.pk.numberparser.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;
import java.util.stream.Stream;

@SpringBootApplication
public class NumberParserApplication {

	@Autowired
	PhoneNumberService phoneNumberService;

	public static void main(String[] args) {
		SpringApplication.run(NumberParserApplication.class, args);
	}

	@Bean
	ApplicationRunner init(PhoneNumberRepository phoneNumberRepository) {
		return args -> {
			Stream.of("911", "1-800-COLLECT", "01","02","03").forEach(number -> {
				Number phoneNumber = new Number();
				phoneNumber.setName(number);
				phoneNumber.setNumber(phoneNumberService.numberCombination(number));
//                phoneNumber.setLetterCombinations(phoneNumberService.letterCombinations(number));
				phoneNumberRepository.save(phoneNumber);
			});
			phoneNumberRepository.findAll().forEach(System.out::println);
		};
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
		config.setAllowedMethods(Collections.singletonList("*"));
		config.setAllowedHeaders(Collections.singletonList("*"));
		source.registerCorsConfiguration("/**", config);
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new CorsFilter(source));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}
