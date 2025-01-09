import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LotteryAnalyzer {
    private static final List<Integer> WINNING_NUMBERS = List.of(3, 8, 15, 22, 34, 42);

    public static void main(String[] args) {
        final String DIRECTORY_PATH = "files";
        Path directoryPath = Paths.get(DIRECTORY_PATH);

        if (!Files.exists(directoryPath) || !Files.isDirectory(directoryPath)) {
            System.err.printf("Directory '%s' does not exist.%n", DIRECTORY_PATH);
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(4);

        try {
            List<Path> files = getAllFiles(directoryPath, ".txt");
            for (Path file : files) {
                executor.submit(new LotteryAnalyzerTask(file, WINNING_NUMBERS));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    public static List<Path> getAllFiles(Path directory, String extension) throws IOException {
        List<Path> result = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory, "*" + extension)) {
            for (Path entry : stream) {
                if (Files.isRegularFile(entry)) {
                    result.add(entry);
                }
            }
        }
        return result;
    }
}