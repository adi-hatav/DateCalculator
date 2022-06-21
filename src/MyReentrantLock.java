import java.util.concurrent.atomic.AtomicBoolean;

public class MyReentrantLock implements Lock{
    AtomicBoolean isLocked;
    Thread lockedThread;
    int counterLock;

    public  MyReentrantLock(){
        this.isLocked=new AtomicBoolean();
        this.lockedThread=null;
        this.counterLock=0;
    }
    @Override
    public void acquire() {
        try {
            while (Thread.currentThread() != this.lockedThread || !this.tryAcquire()) {
                this.wait();
            }
        }
        catch (Exception e){}
        }

    @Override
    public boolean tryAcquire() {
    boolean locking =isLocked.compareAndSet(false,true);
    if (locking) {
        this.lockedThread = Thread.currentThread();
        this.counterLock++;
    }
        return locking;
    }

    @Override
    public void release() throws IllegalReleaseAttempt{
        if (this.lockedThread==Thread.currentThread()&&isLocked.compareAndSet(true,counterLock!=1)) {
            counterLock--;
            this.notify();
            return;
        }
        //throw new IllegalReleaseAttempt();
    }

    @Override
    public void close()  {
        try {
            this.release();
        }
        catch (IllegalReleaseAttempt e){
           // throw e;
        }
    }
}
