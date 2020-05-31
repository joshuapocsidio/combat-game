package controller.io;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Class ErrorLogger implemented as a Singleton
 * - Singleton because FileHandler is linked to a particular error.log file
 * - This is implemented so that it is not required to make filehandlers and loggers to
 * - communicate with each other in case multiple instances are trying to update
 * - one single log file simultaneously.
 */
public class ErrorLogger
{
    /* FileHandler that holds formatting and the error.log*/
    private FileHandler fileHandler;

    private static ErrorLogger instance = null;

    private ErrorLogger() {/*Empty*/}

    public static ErrorLogger getInstance()
    {
        if(instance == null)
        {
            instance = new ErrorLogger();
        }
        return instance;
    }

    /** Sets the file handler - simple formatting and contains link to error.log*/
    public void setFileHandler(FileHandler fileHandler)
    {
        this.fileHandler = fileHandler;
    }

    /** Creates a logger based on classname with the set file handler */
    public Logger createLogger(String className)
    {
        Logger logger =  Logger.getLogger(className);

        if(fileHandler != null)
        {
            logger.addHandler(fileHandler);
        }

        return logger;
    }
}
