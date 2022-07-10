import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

public class MTCollatz {
    private static int counter = 2;
    private static final int [] stoppingTimes = new int [1000];
    private static final ReentrantLock lock = new ReentrantLock();
    private static int numberOfCalculations = 2;

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Missing necessary arguments");
            return;
        }

        if (!tryParseInt(args[0]) && !tryParseInt(args[1])) {
            System.out.println("Not a proper integer value was given");
            return;
        }

        numberOfCalculations = Integer.parseInt(args[0]);

        int numberOfThreads = Integer.parseInt(args[1]);

        Instant startTime = Instant.now();
        Thread[] threads = new Thread[numberOfThreads];
        for (int i = 0; i < numberOfThreads; i++) {
            threads[i] = new Task();
            threads[i].start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            }
            catch (Exception ex)
            {
                System.err.println("Error joining threads");
            }
        }

        Instant endTime = Instant.now();
        double elapsedTime = ((double) endTime.toEpochMilli() - (double) startTime.toEpochMilli()) / 1000.0;

        System.err.print(numberOfCalculations);
        System.err.print(",");
        System.err.print(numberOfThreads);
        System.err.print(",");
        System.err.println(elapsedTime);

        for (int i = 0; i < stoppingTimes.length; i++) {
            System.out.print(i);
            System.out.print(",");
            System.out.println(stoppingTimes[i]);
        }
    }

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static class Task extends Thread {
        public void run() {
            while(counter < numberOfCalculations) {
                lock.lock();
                long a;
                try {
                    a = counter;
                } catch (Exception ex) {
                    System.err.println("Error when reading counter");
                    return;
                } finally {
                    lock.unlock();
                }

                int stoppingTime = 1;
                while (a != 1) {
                    a = CalculateNextA(a);
                    stoppingTime++;
                }

                lock.lock();
                try {
                    stoppingTimes[stoppingTime] = stoppingTimes[stoppingTime] + 1;
                    counter ++;
                } catch (Exception ex) {
                    System.err.println("Error when writing to histogram");
                    return;
                } finally {
                    lock.unlock();
                }
            }
        }

        private long CalculateNextA(long a) {
            long nextA;
            if (a % 2 == 0) {
                nextA = a / 2;
            } else {
                nextA = (3 * a) + 1;
            }
            return nextA;
        }
    }
}
