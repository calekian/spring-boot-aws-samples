package red.appl.aws.stepfunctions.config;

import java.util.concurrent.Executor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Configures async task executor thread pool.
 *
 * <p>Implementing AsyncConfigurer permits configuring the task pool for
 * methods @Async-annotated; Java config permits configuring a superset
 * of spring.task.execution properties.
 *
 * <p>AsyncConfigurerSupport implements AsyncConfigurer as a convenient
 * way to use defaults (via returning null for all methods in the
 * interface), so that we only have to override what we want to set.
 */
@Configuration
@EnableAsync
public class TaskExecutionConfig extends AsyncConfigurerSupport {

    private static final Integer THREAD_POOL_TASK_EXECUTOR_CORE_SIZE = 60;

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(THREAD_POOL_TASK_EXECUTOR_CORE_SIZE);
        executor.setThreadNamePrefix("RedApplAsync-");
        executor.initialize();
        return executor;
    }

}
