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
    public void acquire() {/*
        try {
            while (Thread.currentThread() != this.lockedThread || !this.tryAcquire()) {
                this.wait();
            }
        }
        catch (Exception e){}*/try {
        while(isLocked.get() && Thread.currentThread() != this.lockedThread){
            this.wait();
        }
        isLocked.set(true);
        this.lockedThread = Thread.currentThread();
        this.counterLock++;

    } catch (InterruptedException e){
    }
        }

    @Override
    public boolean tryAcquire() {
    boolean locking =isLocked.compareAndSet(false,true);
    if (lockedThread==Thread.currentThread())
        locking=true;
    if (locking) {
        this.lockedThread = Thread.currentThread();
        this.counterLock++;
    }
        return locking;
    }

    @Override
    public void release() throws IllegalReleaseAttempt{/*
        if (this.lockedThread==Thread.currentThread()&&isLocked.compareAndSet(true,false)) {
            //counterLock=0;
            this.lockedThread=null;
            this.notify();
            return;
        }
       // throw new IllegalReleaseAttempt();*/
        if(Thread.currentThread() == this.lockedThread){
            this.counterLock--;
        }
        if(this.counterLock == 0 && isLocked.compareAndSet(true,false)){
            this.notify();
        }
    }

    @Override
    public void close()  {
        try {
            this.release();
        }
        catch (IllegalReleaseAttempt e){
            //throw e;
        }
    }
}
