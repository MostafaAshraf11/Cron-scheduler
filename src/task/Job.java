package task;

import java.util.UUID;
import java.util.logging.Logger;

public class Job implements Runnable {
	private String id;
	private Runnable task;
	private long intervalMillis;
	private long expectedInterval;
	private Logger logger = Logger.getLogger(Job.class.getName());
	private boolean running = true;

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

}
