package loggingframework;

import loggingframework.models.LogMessage;
import loggingframework.strategy.Appender.LogAppender;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncProcessor
{
    private final ExecutorService executorService;

    public AsyncProcessor()
    {
        this.executorService = Executors.newSingleThreadExecutor(runnable ->
        {
            Thread thread = new Thread(runnable,"AsyncLog Processor");
            thread.setDaemon(true);
            return thread;
        });
    }

    public void process(List<LogAppender> logAppenders, LogMessage logMessage)
    {
        if(executorService.isShutdown())
        {
            System.out.println("Executor service is shut down. Cannot process log message.");
        }
        executorService.submit(() ->
        {
            for(LogAppender logAppender: logAppenders)
            {
                logAppender.append(logMessage);
            }
        });
    }

    public void stop()
    {
        executorService.shutdown();

        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }
        catch (InterruptedException e)
        {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }

}
