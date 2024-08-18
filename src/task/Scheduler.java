package task;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Scheduler {
    private final Map<String, Thread> scheduledJobs;

    public Scheduler() {
        this.scheduledJobs = new ConcurrentHashMap<>();
    }
    
    public void scheduleJob(Runnable task, long expectedInterval, long frequencyMillis) {
        Job job = new Job(task,expectedInterval, frequencyMillis);
        Thread jobThread = new Thread(job);
        scheduledJobs.put(job.getId(), jobThread);
        jobThread.start();
        //return job;
    }

    
    
    public void shutdown() {
        for (Thread jobThread : scheduledJobs.values()) {
            jobThread.interrupt();
        }
        scheduledJobs.clear();
    }
}
