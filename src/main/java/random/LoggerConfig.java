package random;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerConfig {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    private static final String LOG_FORMAT = "[%1$tF %1$tT] %3$s %n";
    private static final String KEY_LOG = "Key Log";
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String PROP_FILE_PATH = "FilePath";
    private static final String DEFAULT_LOG_NAME = "default-log.txt";

    private static volatile LoggerConfig instance = null;

    private LoggerConfig() {
    }

    public static LoggerConfig getInstance() {
        if (instance == null) {
            synchronized (LoggerConfig.class) {
                if (instance == null) {
                    instance = new LoggerConfig();
                }
            }
        }
        return instance;
    }

    public Logger getLogger(String logFilePath) throws IOException {
        Logger logger = Logger.getLogger(KEY_LOG);
        logger.addHandler(getFileHandler(logFilePath));
        return logger;
    }

    private String getLogFilePath(String logFilePath) {
        return logFilePath.isEmpty()
                ? getLogFilePathFromPropFile()
                : logFilePath;
    }

    private String getLogFilePathFromPropFile() {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(CONFIG_PROPERTIES)) {
            properties.load(inputStream);
            return properties.getProperty(PROP_FILE_PATH);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
            return DEFAULT_LOG_NAME;
        }
    }

    private FileHandler getFileHandler(String logFilePath) throws IOException {
        FileHandler fileHandler = new FileHandler(getLogFilePath(logFilePath));
        fileHandler.setFormatter(getCustomSimpleFormatter());
        return fileHandler;
    }

    private SimpleFormatter getCustomSimpleFormatter() {
        return new SimpleFormatter() {
            @Override
            public synchronized String format(LogRecord logRecord) {
                return String.format(LOG_FORMAT,
                        new Date(logRecord.getMillis()),
                        logRecord.getLevel().getLocalizedName(),
                        logRecord.getMessage()
                );
            }
        };
    }
}
