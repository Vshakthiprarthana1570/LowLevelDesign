package loggingframework.strategy.Appender;

import loggingframework.models.LogMessage;
import loggingframework.strategy.Formatter.LogFormatter;

public interface LogAppender
{
    void append(LogMessage logMessage);
    void close();
    LogFormatter getLogFormatter();
    void setLogFormatter(LogFormatter logFormatter);
}
