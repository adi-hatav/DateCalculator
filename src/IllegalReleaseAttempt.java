public class IllegalReleaseAttempt extends IllegalMonitorStateException{

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
