# Step Functions

This sample contains a simple step function state machine definition
in `state-machine.json`. It has one task with a failure handler.
The Spring Boot application polls the activity for work, running
the simple task of producing a `String` when work is available.

## Setup

After LocalStack is started, and assuming the `awsls` alias is set
up as discussed in the parent README, start LocalStack as instructed
and install the state machine definition and create the activiity
as follows:

```shell
awsls stepfunctions create-state-machine --definition file://state-machine.json --name RedApplAwsSampleStateMachine --role-arn "arn:aws:iam::000000000000:role/DummyRole"
awsls stepfunctions create-activity --name red-appl-produce-string
```

## Execution

After starting the Spring Boot application, trigger an execution of the
state machine as follows:

```shell
awsls stepfunctions start-execution --state-machine arn:aws:states:us-east-1:000000000000:stateMachine:RedApplAwsSampleStateMachine
```

After a moment the task will be picked up by the Spring Boot
step function client and executed.
