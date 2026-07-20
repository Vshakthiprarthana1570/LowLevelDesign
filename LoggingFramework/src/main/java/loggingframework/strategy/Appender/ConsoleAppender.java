package loggingframework.strategy.Appender;

import loggingframework.models.LogMessage;
import loggingframework.strategy.Formatter.LogFormatter;
import loggingframework.strategy.Formatter.SimpleTextFormatter;

import java.util.logging.SimpleFormatter;

public class ConsoleAppender implements LogAppender
{
    private LogFormatter logFormatter;


    public ConsoleAppender()
    {
        this.logFormatter = new SimpleTextFormatter();
    }

    public void append(LogMessage logMessage)
    {
        System.out.println(logFormatter.format(logMessage));
    }

    public void setLogFormatter(LogFormatter logFormatter)
    {
        this.logFormatter = logFormatter;
    }

    public void close()
    {

    }
    public LogFormatter getLogFormatter()
    {
        return logFormatter;
    }
}
