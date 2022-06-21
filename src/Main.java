public class Main {
    public static void main(String[] args) {
        testPartA();
        testPartB();
    }

    private static void testPartA() {
        System.out.println("Testing part A...");
        testPartA1();
        testPartA2();
    }

    private static void testPartA1() {
        System.out.println("Testing part A1...");
        Date[] dates = {new Date(16, 6, 2022), new Date(1, 1, 10), new Date(5, 5, 555)};
        int[] nums = {0, 1, -1, 100, -200, -12350, 21030};

        for (Date date : dates) {
            for (int num : nums) {
                testAddToDate(date, num);
            }
        }

        System.out.println();
    }

    private static void testAddToDate(Date date, int num) {
        Date result = DateCalculator.addToDate(date, num);
        if (num >= 0) {
            System.out.println("Adding " + num + " to " + date + " result: " + result);
        } else {
            System.out.println("Subtracting " + -num + " from " + date + " result: " + result);
        }
    }

    private static void testPartA2() {
        System.out.println("Testing part A2...");
        BinNode<Integer> root = new BinNode<>(5);
        root.setLeft(new BinNode<>(7));
        root.setRight(new BinNode<>(3));
        root.getLeft().setLeft(new BinNode<>(7, new BinNode<>(5), null));
        root.getLeft().setRight(new BinNode<>(2, new BinNode<>(9), new BinNode<>(5)));
        System.out.println("Level with most occurrences of 7: " + LevelMostOccurrences.getLevelWithMostOccurrences(root, 7));
        System.out.println("Level with most occurrences of 5: " + LevelMostOccurrences.getLevelWithMostOccurrences(root, 5));
        System.out.println("Level with most occurrences of 6: " + LevelMostOccurrences.getLevelWithMostOccurrences(root, 6));
        System.out.println("Level with most occurrences of 2: " + LevelMostOccurrences.getLevelWithMostOccurrences(root, 2));
        System.out.println();
    }

    private static void testPartB() {
        System.out.println("Testing part B...");
        for (int i = 0; i < 100; i++) {
            Counter.count = 0;
            MyReentrantLock myLock = new MyReentrantLock();
            Thread t1 = new Thread(new OneAcquireWorker(myLock));
            t1.start();
            Thread t2 = new Thread(new TryWithResourcesAcquireWorker(myLock));
            t2.start();

            // Wait for the completion of the workers
            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("Iteration " + (i + 1) + ", Counter = " + Counter.count);
        }

        try {
            Lock lock = new MyReentrantLock();
            lock.release();
        } catch (IllegalReleaseAttempt e) {
            System.out.println("Cannot release the lock!");
        }

        try {
            Lock lock = new MyReentrantLock();
            lock.release();
        } catch (IllegalMonitorStateException e) {
            System.out.println("Cannot release the lock!");
        }

        try (MyReentrantLock lock = new MyReentrantLock()) {
        } catch (IllegalReleaseAttempt e) {
            System.out.println("Cannot release the lock!");
        }

        Lock lock = new MyReentrantLock();
        boolean result = lock.tryAcquire();
        if (result) {
            System.out.println("Locked the lock, now releasing it.");
            lock.release();
        } else {
            System.out.println("You should not reach here!");
        }
    }
}


class Counter {
    public static int count = 0;
}

abstract class Worker implements Runnable {
    protected final MyReentrantLock lock;

    public Worker(MyReentrantLock lock) {
        this.lock = lock;
    }

    protected abstract void lockAndIncrement();

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++) {
            lockAndIncrement();
            if (i % 100 == 0) {
                Thread.yield();  // Give other threads a chance by giving up my time slice
            }
        }
    }
}

class OneAcquireWorker extends Worker {
    public OneAcquireWorker(MyReentrantLock lock) {
        super(lock);
    }

    @Override
    protected void lockAndIncrement() {
        try {
            lock.acquire();
            Counter.count++;
        } finally {
            lock.release();
        }
    }
}


class TryWithResourcesAcquireWorker extends Worker {
    public TryWithResourcesAcquireWorker(MyReentrantLock lock) {
        super(lock);
    }

    @Override
    protected void lockAndIncrement() {
        try (lock) {
            lock.acquire();
            try {
                lock.acquire();
                Counter.count++;
            } finally {
                lock.release();
            }
        }
    }
}