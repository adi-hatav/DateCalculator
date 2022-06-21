public interface Lock extends AutoCloseable {
    void acquire();
    boolean tryAcquire();
    void release();
}
