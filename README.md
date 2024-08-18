# Cron scheduler
 Implement an in-process cron scheduler that accepts a job and executes it periodically using java
 
# Brief Description
The Cron Scheduler is an in-process scheduling library implemented in Java. It allows you to schedule and execute multiple jobs concurrently, with each job running at specified intervals. The scheduler is designed to handle job execution with the following features:
  Each job can be scheduled to run at a specific frequency.
  Jobs are identified uniquely and executed periodically.
  The execution time and logging for each job are tracked.
  
# Reasoning Behind Technical Decisions
Concurrency: Jobs are executed in separate threads to allow concurrent execution. The Job class implements Runnable, and each job runs in its own thread.
Job Scheduling: The scheduler maintains a map of job IDs to threads. Jobs are scheduled by creating new Job instances and starting them in new threads.
Error Handling: The job execution loop includes try-catch blocks to handle interruptions and exceptions gracefully. Logging is used for tracking job execution and issues.
Time Management: To maintain job intervals, the scheduler calculates the sleep time based on job execution duration and desired frequency.

# Trade-offs Made
Precision The scheduler uses a basic time management approach with loops and sleep intervals. This approach is lack precision compared to more advanced scheduling algorithms or libraries.

Scalability: The use of ConcurrentHashMap ensures thread safety, but the design does not include advanced features such as dynamic job rescheduling or persistence.

# Example Usage

public class Job implements Runnable {


	public Job(Runnable task, long expectedInterval, long intervalMillis) {
		this.id = UUID.randomUUID().toString();
		this.task = task;
		this.intervalMillis = intervalMillis;
		this.expectedInterval = expectedInterval;
	}

	public String getId() {
		return id;
	}

	@Override
	public void run() {
		while (running) {
			long startTime = System.currentTimeMillis();
			try {
				logger.info(String.format("Job %s started at %d", id, startTime));
				// logger.info(String.format("Job %s expected Interval is between: %d and: %d",
				// id, startTime, startTime + expectedInterval));

				while (System.currentTimeMillis() - startTime < expectedInterval) {
				}
				task.run();

				long executionTime = System.currentTimeMillis() - startTime;
				logger.info(String.format("Job %s completed in %dms", id, executionTime));

				long intervalTime = intervalMillis - executionTime;
				if (intervalTime < 0) {
					intervalTime = 0;
				}
				Thread.sleep(intervalTime);
			} catch (InterruptedException e) {
				logger.warning(String.format("Job %s was interrupted.", id));
				Thread.currentThread().interrupt();
				break;
			} catch (Exception e) {
				logger.severe(String.format("Job %s encountered an exception: %s", id, e.getMessage()));
				break;
			}
		}
		logger.info(String.format("Job %s stopped.", id));
	}

# Possible Future Improvements
Dynamic Scheduling: Add support for updating job schedules or removing jobs without shutting down the scheduler.
Job Prioritization: Implement priority levels for jobs to manage execution order based on importance.
Precision : Enhance the timing mechanism to ensure more accurate job execution intervals, potentially using a more sophisticated scheduling approach.

# Testing
The project includes unit tests implemented using JUnit. Tests cover various scenarios including:

Basic scheduling and execution.
Concurrent execution of multiple jobs.
High-frequency job scheduling.
Jobs with long intervals compared to their frequency.
Tests are located in the test package and can be run using any JUnit-compatible test runner.

# NB single run expected interval was used to as the execution time of the task
