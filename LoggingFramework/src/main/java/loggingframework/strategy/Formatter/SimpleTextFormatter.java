package loggingframework.strategy.Formatter;

import loggingframework.models.LogMessage;

import java.time.format.DateTimeFormatter;

public class SimpleTextFormatter implements LogFormatter
{
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String format(LogMessage logMessage)
    {
        return String.format("[%s] [%s] [%s] [%s] - %s",
                logMessage.getTimestamp().format(dateFormatter),
                logMessage.getLogLevel(),
                logMessage.getThreadName(),
                logMessage.getLoggerName(),
                logMessage.getMessage());
    }

}
