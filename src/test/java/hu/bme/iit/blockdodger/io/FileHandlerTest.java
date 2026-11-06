package hu.bme.iit.blockdodger.io;

import org.junit.jupiter.api.Test;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {
    @Test void saveThenLoadHighScore() throws Exception {
        // use a temporary working dir
        Path tmp = Files.createTempDirectory("bd");
        Path orig = Path.of("highscore.ser");
        Path backup = null;
        if (Files.exists(orig)) {
            backup = Files.createTempFile("bd_backup", ".ser");
            Files.copy(orig, backup, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
            Files.delete(orig);
        }
        try {
            FileHandler.saveHighScoreSafe(1234);
            int loaded = FileHandler.loadHighScoreSafe();
            assertEquals(1234, loaded);
        } finally {
            // cleanup
            Files.deleteIfExists(orig);
            if (backup != null) Files.move(backup, orig);
            Files.deleteIfExists(tmp);
        }
    }
}
