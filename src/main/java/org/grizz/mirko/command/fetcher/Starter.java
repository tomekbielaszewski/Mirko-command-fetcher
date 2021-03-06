package org.grizz.mirko.command.fetcher;

import lombok.extern.slf4j.Slf4j;
import org.grizz.mirko.command.fetcher.task.MirkoFetcherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class Starter {

    public static void main(String[] args) {
        SpringApplication.run(Starter.class, args);
    }

    @Autowired
    private MirkoFetcherTask task;

    @Scheduled(cron = "0 */1 * * * *")
//	@PostConstruct
    public void runScheduledTask() {
        task.run();
    }
}
