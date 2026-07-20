package loggingframework.models;

import loggingframework.enums.LogLevel;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogMessage
{
    private final String message;
    private final LogLevel logLevel;
    private final LocalDateTime timestamp;
    private final String threadName;
    private final String loggerName;

    public LogMessage(String message, LogLevel logLevel,String loggerName)
    {
        this.message = message;
        this.logLevel = logLevel;
        this.timestamp = LocalDateTime.now();
        this.threadName = Thread.currentThread().getName();
        this.loggerName = loggerName;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getThreadName() {
        return threadName;
    }

    public String getLoggerName() {
        return loggerName;
    }
}
