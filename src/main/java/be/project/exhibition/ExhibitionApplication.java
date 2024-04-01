package be.project.exhibition;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@Profile("local")
public class ExhibitionApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExhibitionApplication.class, args);
    }

}
