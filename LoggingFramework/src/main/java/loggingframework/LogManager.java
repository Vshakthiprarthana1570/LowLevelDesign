package loggingframework;

import loggingframework.strategy.Appender.LogAppender;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LogManager
{
    private static LogManager instance;

    private final Logger rootLogger;
    private final Map<String, Logger> loggers;
    private final AsyncProcessor asyncProcessor;

    private LogManager()
    {
        this.rootLogger = new Logger("root",null);
        this.loggers = new ConcurrentHashMap<>();
        asyncProcessor = new AsyncProcessor();
    }

    public static synchronized LogManager getInstance()
    {
        if(instance == null)
        {
            instance = new LogManager();
        }
        return instance;
    }

    public Logger getLogger(String name)
    {

        return loggers.computeIfAbsent(name,this::createLogger);
    }

    public Logger createLogger(String name)
    {
        if(name.equals("root"))
        {
            return rootLogger;
        }
        int index = name.lastIndexOf(".");
        String parentName = index == -1 ? "root" : name.substring(0,index);
        Logger parentLogger = getLogger(parentName);
        return new Logger(name,parentLogger);
    }


    public Logger getRootLogger() {
        return rootLogger;
    }

    public Map<String, Logger> getLoggers() {
        return loggers;
    }

    AsyncProcessor getAsyncProcessor() {
        return asyncProcessor;
    }

    public void stop()
    {
        asyncProcessor.stop();
        loggers.values().stream()
                        .flatMap(logger -> logger.getLogAppenders().stream())
                        .distinct()
                        .forEach(LogAppender::close);
        System.out.println("Logging framework shut down gracefully.");

    }
}
