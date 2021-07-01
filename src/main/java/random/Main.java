package random;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

    public static void main(String[] args) {

        String filePath = args.length > 0 ? args[0] : "";

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new KeyListener(filePath));
        } catch (NativeHookException | IOException e) {
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
