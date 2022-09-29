package red.appl.aws.stepfunctions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * Configures task scheduler thread pool and enables scheduling.
 */
@Configuration
@EnableScheduling
public class TaskSchedulingConfig {

    private static final Integer THREAD_POOL_TASK_SCHEDULER_POOL_SIZE = 32;

    @Bean
    TaskScheduler getTaskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(THREAD_POOL_TASK_SCHEDULER_POOL_SIZE);
        return scheduler;
    }

}
