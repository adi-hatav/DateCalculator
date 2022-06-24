/**
 * represents IllegalReleaseAttempt exception to raise when a thread tries to
 * release a lock which is not his own lock or when the lock is not locked.
 */
public class IllegalReleaseAttempt extends IllegalMonitorStateException {

    /**
     * IllegalReleaseAttempt constructor without parameters.
     */
    public IllegalReleaseAttempt() {
    }

    /**
     * IllegalReleaseAttempt constructor with message parameter
     *
     * @param msg the exception message
     */
    public IllegalReleaseAttempt(String msg) {
        super(msg);
    }

}
