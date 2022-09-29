# Spring Cloud AWS Samples with LocalStack

[LocalStack](https://localstack.cloud) is a local implementation of large parts of the AWS cloud platform.
Docker needs to be installed for it to function.

## Installing LocalStack

Install LocalStack following the instructions, or with:

```shell
$ pipx install localstack
```

## Starting LocalStack

Run LocalStack with:

```shell
$ localstack start
```

## Configuring Access to LocalStack

While there are several options, it's simple to set up an `aws`
CLI profile for LocalStack. As an example, place the following
in `~/.aws/credentials`:

```
[localstack]
region=us-east-1
output=json
cli_pager=
aws_access_key_id=test
aws_secret_access_key=test
```

When using the `aws` command, specify the profile and endpoint
with `--profile localstack --endpoint-url=http://localhost:4566`,
or add something like an alias:

```shell
alias awsls="aws --profile localstack --endpoint-url=http://localhost:4566"
```

Then you can run something usual like `get-caller-identity` to verify that
LocalStack is working (after starting it):

```
$ awsls sts get-caller-identity
{
    "UserId": "AKIAIOSFODNN7EXAMPLE",
    "Account": "000000000000",
    "Arn": "arn:aws:iam::000000000000:root"
}
```

## Using LocalStack

LocalStack accepts but [ignores authentication by default](https://docs.localstack.cloud/aws/iam/);
all functionality is available to any connecting process. Supported functions can be used via
the AWS SDK or the `aws` CLI as usual.

See the [LocalStack documentation](https://docs.localstack.cloud/overview/) for more usage information.
