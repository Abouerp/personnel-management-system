package zsc.edu.abouerp.service.service;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @author LT
 */
@Configuration
@EnableScheduling
public class ScheduleTask {

    private final AdministratorService administratorService;

    public ScheduleTask(AdministratorService administratorService) {
        this.administratorService = administratorService;
    }

    @Scheduled(cron = "0 0 1 1 * ?")
    private void wageTask() {
        administratorService.wageTask();
    }

}
