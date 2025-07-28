package uz.imv.lmssystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class LmsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(LmsSystemApplication.class, args);
    }

}
