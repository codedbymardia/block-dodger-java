package hu.bme.iit.blockdodger.io;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileHandler {
    private static final String FILE = "highscores.ser";

    //save one new score into the top list
    public static void saveScore(int score) {
        List<Integer> scores = loadTopScoresSafe();
        scores.add(score);

        // sorted descending and keep top 3 only
        Collections.sort(scores, Collections.reverseOrder());

        if (scores.size() > 3) {
            scores = new ArrayList<>(scores.subList(0, 3));
        }

        try (ObjectOutputStream out = new ObjectOutputStream(
                Files.newOutputStream(Path.of(FILE)))) {
            out.writeObject(scores);
        } catch (IOException ignored) {
            // ignoring write errors in this simple game
        }
    }

    // Load list of top scores
    public static List<Integer> loadTopScoresSafe() {
        try (ObjectInputStream in = new ObjectInputStream(
                Files.newInputStream(Path.of(FILE)))) {
            Object o = in.readObject();
            if (o instanceof List) {
                @SuppressWarnings("unchecked")
                List<Integer> scores = (List<Integer>) o;
                Collections.sort(scores, Collections.reverseOrder());
                if (scores.size() > 3) {
                    scores = new ArrayList<>(scores.subList(0, 3));
                }
                return scores;
            }
        } catch (Exception e) {
            // file missing or invalid, return empty list
        }
        return new ArrayList<>();
    }


    public static void saveHighScoreSafe(int score) {
        saveScore(score);
    }

    public static int loadHighScoreSafe() {
        List<Integer> scores = loadTopScoresSafe();
        if (scores.isEmpty()) return 0;
        return scores.get(0);
    }
}
