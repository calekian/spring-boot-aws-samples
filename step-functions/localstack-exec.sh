#!/bin/sh
aws --profile localstack --endpoint-url=http://localhost:4566 stepfunctions start-execution --state-machine arn:aws:states:us-east-1:000000000000:stateMachine:RedApplAwsSampleStateMachine
