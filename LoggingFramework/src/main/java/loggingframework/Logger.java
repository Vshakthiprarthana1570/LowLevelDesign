package loggingframework;

import loggingframework.enums.LogLevel;
import loggingframework.models.LogMessage;
import loggingframework.strategy.Appender.LogAppender;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Logger
{
    private final String name;
    private final Logger parent;
    private LogLevel level;
    private boolean additivity;
    private List<LogAppender> logAppenders;


    public Logger(String name, Logger parent)
    {
        this.name = name;
        this.parent = parent;
        this.logAppenders = new CopyOnWriteArrayList<>();
        this.additivity = true;
    }
    public LogLevel getEffectiveLevel()
    {
        for(Logger logger = this;logger != null;logger = logger.parent)
        {
            LogLevel currentLevel = logger.getLevel();
            if(currentLevel != null)
            {
                return currentLevel;
            }
        }

        return LogLevel.DEBUG;
    }
    public void log(LogLevel logLevel,String message)
    {
        if(logLevel.isGreaterOrEqual(getEffectiveLevel()))
        {
            LogMessage logMessage = new LogMessage(message,logLevel,name);
            callAppenders(logMessage);
        }
    }

    public void callAppenders(LogMessage logMessage)
    {
        if(!logAppenders.isEmpty())
        {
           LogManager.getInstance().getAsyncProcessor().process(logAppenders, logMessage);
        }
        if(additivity && parent != null)
        {
            parent.callAppenders(logMessage);
        }

    }

    public void addAppender(LogAppender logAppender)
    {
        logAppenders.add(logAppender);
    }

    public void debug(String message)
    {
        log(LogLevel.DEBUG, message);
    }
    public void info(String message) {
        log(LogLevel.INFO, message);
    }
    public void warn(String message) {
        log(LogLevel.WARN, message);
    }
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }
    public void fatal(String message) {
        log(LogLevel.FATAL, message);
    }



    public String getName()
    {
        return name;
    }

    public Logger getParent() {
        return parent;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public boolean isAdditivity() {
        return additivity;
    }

    public void setAdditivity(boolean additivity) {
        this.additivity = additivity;
    }

    public List<LogAppender> getLogAppenders() {
        return logAppenders;
    }

    public void setLogAppenders(List<LogAppender> logAppenders) {
        this.logAppenders = logAppenders;
    }


}
