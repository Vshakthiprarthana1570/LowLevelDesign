package loggingframework.strategy.Appender;

import loggingframework.models.LogMessage;
import loggingframework.strategy.Formatter.LogFormatter;
import loggingframework.strategy.Formatter.SimpleTextFormatter;

import java.io.FileWriter;
import java.io.IOException;

public class FileAppender implements LogAppender
{
    private LogFormatter logFormatter;
    private FileWriter fileWriter;

    public FileAppender(String filePath)
    {
        logFormatter = new SimpleTextFormatter();
        try
        {
            fileWriter = new FileWriter(filePath, true);
        }
        catch (Exception e)
        {
            System.out.println("Failed to create writer for file logs, exception: " + e.getMessage());
        }
    }

    public void append(LogMessage logMessage)
    {
       try
       {
           fileWriter.write(logFormatter.format(logMessage) + "\n");
           fileWriter.flush();
       }
       catch (Exception e)
       {
           System.out.println("Failed to write log message to file, exception: " + e.getMessage());
       }
    }

    public void close()
    {
        try {
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to close logs file, exception: " + e.getMessage());
        }
    }

    public LogFormatter getLogFormatter()
    {
        return logFormatter;
    }
    public void setLogFormatter(LogFormatter logFormatter)
    {
        this.logFormatter = logFormatter;
    }
}
