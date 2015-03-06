package synchronizer.common.util;

import synchronizer.common.LoggerWrapper;

import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * GKislin
 * 05.03.2015.
 *
 * ScheduledExecutor with exception handler
 * @link http://stackoverflow.com/questions/2248131/handling-exceptions-from-java-executorservice-tasks
 */
public class ScheduledExecutor extends ScheduledThreadPoolExecutor {
    private static final LoggerWrapper LOG = LoggerWrapper.get(ScheduledExecutor.class);

    public ScheduledExecutor() {
        super(Runtime.getRuntime().availableProcessors() * 2);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t != null) {
            treatException(t.getCause());
        } else {
            try {
                Future<?> future = (Future<?>) r;
                future.get();
            } catch (Exception e) {
                treatException(e.getCause());
            }
        }
    }

    private void treatException(Throwable cause) {
        LOG.error("Error in file processing", cause);
        System.exit(1);
    }
}
