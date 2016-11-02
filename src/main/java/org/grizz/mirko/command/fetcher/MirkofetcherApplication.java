package org.grizz.mirko.command.fetcher;

import org.grizz.mirko.command.fetcher.task.MirkoFetcherTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class MirkofetcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(MirkofetcherApplication.class, args);
	}

	@Autowired
	private MirkoFetcherTask task;

	@Scheduled(cron = "0 */1 * * * *")
	public void runScheduledTask() {
		task.run();
	}
}
