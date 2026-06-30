package infrastructure.config;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;



public final class ResourceLoader {
    private ResourceLoader() {
    }

    public static void loadFonts() {
        registerFont("/fonts/PlaywriteGBJ-Italic-VariableFont_wght.ttf");
        registerFont("/fonts/BlackOpsOne-Regular.ttf");
    }

    private static void registerFont(String resource) {
        try (InputStream fontFile = ResourceLoader.class.getResourceAsStream(resource)) {

            if (fontFile == null) {
                System.out.println("The font didn't found " + resource);
                return;
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);

        } catch (IOException | FontFormatException e) {
            System.err.println("Error when we try to load the data " + resource + " : " + e.getMessage());
        }

    }
}