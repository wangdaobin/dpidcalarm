package com.services.dpidcalarm;

import com.services.dpidcalarm.collect.job.CollectDataBTA;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
public class DpidcalarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(DpidcalarmApplication.class, args);

    }

}
