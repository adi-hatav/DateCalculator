import java.util.concurrent.atomic.AtomicBoolean;

/**
 * represents Reentrant Lock that implements lock
 */
public class MyReentrantLock implements Lock {
    AtomicBoolean isLocked;
    Thread lockedThread;
    int counterLock;

    /**
     * MyReentrantLock constructor,
     * isLocked - type of atomicBooleam, true if the lock is locked (in use) and otherwise false
     * lockedThread - the current thread that uses the lock
     * counterLock - counts the number of time the current thread locked the lock, for reentrant use
     */
    public MyReentrantLock() {
        this.isLocked = new AtomicBoolean();
        this.lockedThread = null;
        this.counterLock = 0;
    }

    /**
     * acquires the lock. waits until the lock is available yo use it.
     */
    @Override
    public void acquire() {
        // if there are already locks from this thread then it increments the lock counter
        // if there is new thread that wants to be locked and there are no current locks
        if (this.counterLock == 0 || Thread.currentThread() != this.lockedThread) {
            // waits until the lock is released and when it happens, it atomically updates isLocked variable
            while (!isLocked.compareAndSet(false, true)) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                }
            }
            // updates lockedThread to the current thread and increments the lock counter
            this.lockedThread = Thread.currentThread();
        }
        // if there are already locks from this thread then it only increments the lock counter
        this.counterLock++;
    }

    /**
     * try to acquire the lock
     *
     * @return true if acquire is successful and false otherwise
     */
    @Override
    public boolean tryAcquire() {
        // try to lock the lock using atomic boolean compareAndSet
        boolean locking = isLocked.compareAndSet(false, true);
        // if we are in the locked thread then is_locked is true, and the variable locking should be true as well.
        if (lockedThread == Thread.currentThread())
            locking = true;
        // if locking is successful then change the lockedThread to this thread and increments the lock counter
        if (locking) {
            this.lockedThread = Thread.currentThread();
            this.counterLock++;
        }
        return locking;
    }

    /**
     * try to release the lock.
     * if locked a few times the decrease lock counter and if it is the last lock,
     * decrease lock counter and releases lock
     *
     * @throws IllegalReleaseAttempt if the current thread is not the locked thread or there is no lock to release
     */
    @Override
    public void release() {
        // if the current thread is not the locked thread or there is no lock to release, throws unchecked error
        if (Thread.currentThread() != this.lockedThread || !this.isLocked.get()) {
            throw new IllegalReleaseAttempt();
        }
        // if there are more than one lock right now, then the lock counter is decreased
        if (this.counterLock > 1) {
            this.counterLock--;
            return;
        }
        // lock counter is decreased id the lock is released
        this.counterLock--;
        isLocked.set(false);

    }

    /**
     * releases the lock, enables use of try with resources
     *
     * @throws IllegalReleaseAttempt if the current thread is not the locked thread or there is no lock to release
     */
    @Override
    public void close() {
        try {
            this.release();
        } catch (IllegalReleaseAttempt e) {
            throw e;
        }
    }
}
