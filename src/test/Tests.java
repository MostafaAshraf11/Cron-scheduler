package test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.Timeout.ThreadMode;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import task.Scheduler;


@Timeout(value = 10, unit = TimeUnit.SECONDS, threadMode = ThreadMode.SEPARATE_THREAD)
public class Tests {
	// base case
    @Test
    public void testSchedule1() {
        Scheduler scheduler = new Scheduler();

        AtomicInteger counter = new AtomicInteger(0);
	     scheduler.scheduleJob(() -> {
	        System.out.println("Job 1 is running at: " + System.currentTimeMillis());
	        counter.incrementAndGet();
	    }, 1000, 2000 );
	    
	    try {
	        Thread.sleep(5050);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }


	    scheduler.shutdown();
	    System.out.println(counter.get());

        assertEquals(3, counter.get());
    }
    
    // Schedule two jobs with different intervals
    @Test
    public void testSchedule2() {
        Scheduler scheduler = new Scheduler();

        AtomicInteger counter1 = new AtomicInteger(0);
        AtomicInteger counter2 = new AtomicInteger(0);

         scheduler.scheduleJob(() -> {
            System.out.println("Job 1 is running at: " + System.currentTimeMillis());
            counter1.incrementAndGet();
        }, 1000, 3000); 

         scheduler.scheduleJob(() -> {
            System.out.println("Job 2 is running at: " + System.currentTimeMillis());
            counter2.incrementAndGet();
        }, 1000, 2000); 

        try {
            Thread.sleep(7050);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.shutdown();

        assertEquals(3, counter1.get());
        assertEquals(4, counter2.get());
    }
    //test tasks that high frequency
    @Test
    public void testSchedule3() {
        Scheduler scheduler = new Scheduler();

        AtomicInteger counter = new AtomicInteger(0);

      
        scheduler.scheduleJob(() -> {
            System.out.println("Frequent job running at: " + System.currentTimeMillis());
            counter.incrementAndGet();
        }, 0, 200);

        try {
            Thread.sleep(3050); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        
        scheduler.shutdown();

        
        assertEquals(15, counter.get(), 1); 
    }
    
    
    //test task with higher interval than frequency
    @Test
    public void testSchedule4() {
        Scheduler scheduler = new Scheduler();

        AtomicInteger counter = new AtomicInteger(0);

         scheduler.scheduleJob(() -> {
            System.out.println("Delayed job running at: " + System.currentTimeMillis());
            counter.incrementAndGet();
        }, 3000, 1000);

        try {
            Thread.sleep(5050); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        scheduler.shutdown();

        
        assertEquals(1, counter.get());
    }
    
    
    //test four tasks same execution time and interval
    @Test
    public void testSchedule5() {
        Scheduler scheduler = new Scheduler();

        AtomicInteger counter1 = new AtomicInteger(0);
        AtomicInteger counter2 = new AtomicInteger(0);
        AtomicInteger counter3 = new AtomicInteger(0);
        AtomicInteger counter4 = new AtomicInteger(0);


         scheduler.scheduleJob(() -> {
            System.out.println("Job 1 is running at: " + System.currentTimeMillis());
            counter1.incrementAndGet();}, 1000, 2000);

         scheduler.scheduleJob(() -> {
            System.out.println("Job 2 is running at: " + System.currentTimeMillis());
            counter2.incrementAndGet();}, 1000, 2000);

         scheduler.scheduleJob(() -> {
            System.out.println("Job 3 is running at: " + System.currentTimeMillis());
            counter3.incrementAndGet();
        }, 1000, 2000);

         scheduler.scheduleJob(() -> {
            System.out.println("Job 4 is running at: " + System.currentTimeMillis());
            counter4.incrementAndGet();}, 1000, 2000);


        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }


        scheduler.shutdown();

 
        assertEquals(3, counter1.get());
        assertEquals(3, counter2.get());
        assertEquals(3, counter3.get());
        assertEquals(3, counter4.get());
    }
    




}
