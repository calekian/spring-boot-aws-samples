#!/bin/sh
aws --profile localstack --endpoint-url=http://localhost:4566 stepfunctions create-state-machine --definition file://state-machine.json --name RedApplAwsSampleStateMachine --role-arn arn:aws:iam::000000000000:role/DummyRole
aws --profile localstack --endpoint-url=http://localhost:4566 stepfunctions create-activity --name red-appl-produce-string
