package br.edu.ifrs.restinga.ds.prova;

import java.util.TimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProvaApplication {

	public static void main(String[] args) {
                TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		SpringApplication.run(ProvaApplication.class, args);
	}
}
