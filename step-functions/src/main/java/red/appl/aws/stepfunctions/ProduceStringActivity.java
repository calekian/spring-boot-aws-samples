package red.appl.aws.stepfunctions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sfn.SfnClient;
import software.amazon.awssdk.services.sfn.model.GetActivityTaskRequest;
import software.amazon.awssdk.services.sfn.model.GetActivityTaskResponse;
import software.amazon.awssdk.services.sfn.model.SendTaskFailureRequest;
import software.amazon.awssdk.services.sfn.model.SendTaskSuccessRequest;

/**
 * Simple step function activity, which produces a String indicating
 * that it produced a String.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class ProduceStringActivity {

    private static final String LOCALSTACK_PRODUCE_STRING_ACTIVITY_ARN = "arn:aws:states:us-east-1:000000000000:activity:red-appl-produce-string";
    private static final String LOCALSTACK_WORKER_NAME = "red-appl-aws-samples-step-functions-worker";

    private final SfnClient sfnClient;

    /**
     * Polls AWS, up to 60s, for work (a task) to run for a defined activity.
     * If work is available, accepts it and provides a task token which it
     * will later notify the Step Function of success or failure regarding.
     */
    @Scheduled(fixedDelay = 1000)
    @Async
    public void pollForActivityWork() {

        log.info("Entered " + this.getClass().getSimpleName() + "::" + (new Throwable().getStackTrace())[0].getMethodName());

        final GetActivityTaskResponse taskResponse = pollForTask();
        final String taskToken = taskResponse.taskToken();

        if (taskToken != null) {

            log.info("Received task for activity");

            try {
                final String processedWorkResponse = processActivityWork(taskResponse.taskToken());

                log.info(processedWorkResponse);

                sfnClient.sendTaskSuccess(generateTaskSuccessMessage(taskToken, processedWorkResponse));
            } catch (Exception exception) {
                sfnClient.sendTaskFailure(generateTaskFailureMessage(taskToken, exception));
            }

        } else {
            log.info("No pending task for activity");
        }

    }

    private String processActivityWork(String taskToken) {
        return "Produced String for activity ARN " + LOCALSTACK_PRODUCE_STRING_ACTIVITY_ARN + " with task token " + taskToken;
    }

    private GetActivityTaskResponse pollForTask() {

        log.info("Polling for task (up to 60s)");

        return sfnClient.getActivityTask(
            GetActivityTaskRequest.builder()
                .activityArn(LOCALSTACK_PRODUCE_STRING_ACTIVITY_ARN)
                .workerName(LOCALSTACK_WORKER_NAME)
                .build()
        );
    }

    private SendTaskSuccessRequest generateTaskSuccessMessage(String taskToken, String output) {
        return SendTaskSuccessRequest.builder()
            .taskToken(taskToken)
            .output(output)
            .build();
    }

    private SendTaskFailureRequest generateTaskFailureMessage(String taskToken, Exception exception) {
        return SendTaskFailureRequest.builder()
            .taskToken(taskToken)
            .error(exception.getMessage())
            .build();
    }

}
