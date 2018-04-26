# AwsStatusChecker

![slack](https://user-images.githubusercontent.com/4110851/39289300-6a635682-48e0-11e8-9a24-f7777ff829da.PNG)


## Why would I need this?




You just received a page duty message for prod. You spend minutes, maybe hours going through debugging steps, waking up engineers, nothing is making sense. A member of your team says hey look at [Aws Status Webpage](http://status.aws.amazon.com/) there are network errors in us-west-2! 

Aws status checker will beat that team member to the punch, before you spend your valuable time debugging an issue out of your control, and give you the information you need to trigger that cross cloud provider failover strategy. 

### How it works

Aws status checker simply needs a place to hold state, and an execution environment. It could be installed to a raspberry pi, but initially I chose to put it in AWS Lambda. There's a config file that stores information on where you want to send the slack posts, and also a file that indicates the last time the checker ran (so it doesn't spam with duplicate posts).

The irony of the checker being dependant on AWS services isn't lost on me either... don't worry :)  


### How to get started. 

## Create an IAM role that allows lambda functions access to S3 

![iam](https://user-images.githubusercontent.com/4110851/39289351-aaa5d8a0-48e0-11e8-9c12-a1b29c8cd97c.PNG)

1. Navigate to IAM in your AWS account. 
2. Find roles, and then create one that gives access to S3, and Lambda. 
3. I think for the future I will post instructions on just giving access to one bucket. If you want to submit those I would appreciate it!


## Upload the code to Lambda 

I use the AWS plugin for Eclipse which makes this part really easy. You can also simply upload the code into S3 as well, or create a new lambda function manually and upload the code as a zip. 

![upload1](https://user-images.githubusercontent.com/4110851/39289444-09fec730-48e1-11e8-9665-6d718aa52cd3.PNG)


## Set the timer 

![cloudwatch](https://user-images.githubusercontent.com/4110851/39289717-1e9d067e-48e2-11e8-8b5c-5aeb6aee9124.PNG)

Use a Cloudwatch timer in Lambda to trigger the Aws status checker. I have set it to poll every five minutes. Note keep it disabled until you complete the entire setup process. You may also want to setup loggin 


## Set the env vars 

The environment variables : 

| Var      | Details           | Example  |
| ------------- |-------------| -----|
|  bucketName     | S3 bucket used to store files |  status-checker |
|  region |  Region of S3 bucket    |  us-west-2   |

![envvars](https://user-images.githubusercontent.com/4110851/39289863-933ab986-48e2-11e8-8898-9d279a014e53.PNG)


## Create the config file

Here's an example. Create a slack webhook URL and fill it in here! 

Note for notificationLevel it can be "AT_HERE" , or "AT_CHANNEL" 

``` json 
{
	"slackConfig": {
		"url": "https://hooks.slack.com/services/YOUR/WEBBOOK",
		"channel": "#awsstatus",
		"userName": "AwsStatusBot",
		"notificationLevel":"AT_HERE",
		"socketReadTimeout": 5000,
		"connectionTimeout": 5000
	},
	"statusReaderConfig": {
		"url": "http://status.aws.amazon.com/rss/all.rss",
		"sourceType": "URL"
	}
}
```
Then name it config.json and upload it to your S3 bucket specified in the env variable. That's it! Your first run will have all of the 15 most recent events, think of it as a test run. 

## Troubleshooting 

1. Check that the slack webhook is valid. 
2. The checker create a .lastChecked file in the bucket if it actually does something, if that isn't present check your cloudwatch schedule rule is enabled. 
3. If you need to retest the webhook, delete the .lastChecked and the checker will post the 15 most recent items again.

## What's the cost? 

The most expensive part of the checker is the S3 puts, which is still very small. It cost me 4cents to run the checker all last month. 


