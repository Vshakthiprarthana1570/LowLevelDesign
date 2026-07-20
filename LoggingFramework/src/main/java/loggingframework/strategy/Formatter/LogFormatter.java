package loggingframework.strategy.Formatter;

import loggingframework.models.LogMessage;

public interface LogFormatter
{
    String format(LogMessage message);
}
