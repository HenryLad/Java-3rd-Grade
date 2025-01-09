import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class LotteryAnalyzerTask implements Callable<Void> {
    private final Path filePath;
    private final List<Integer> winningNumbers;

    public LotteryAnalyzerTask(Path filePath, List<Integer> winningNumbers) {
        this.filePath = filePath;
        this.winningNumbers = winningNumbers;
    }

    @Override
    public Void call() {
        try {
            List<LotteryTip> tips = readLotteryTipsFromFile(filePath);
            for (LotteryTip tip : tips) {
                int correctCount = getCorrectCount(tip);
                if (correctCount >= 5) {
                    System.out.printf("Thread: %s, ID: %s: %s - Corr %d%n",
                            Thread.currentThread().getName(),
                            tip.id(),
                            formatTip(tip),
                            correctCount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<LotteryTip> readLotteryTipsFromFile(Path filePath) throws IOException {
        List<LotteryTip> tips = new ArrayList<>();
        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            tips.add(processLine(line));
        }
        return tips;
    }

    private LotteryTip processLine(String line) {
        String[] parts = line.split(",");
        String id = parts[0];
        int[] numbers = new int[6];
        for (int i = 0; i < 6; i++) {
            numbers[i] = Integer.parseInt(parts[i + 1]);
        }
        return new LotteryTip(id, numbers);
    }

    private int getCorrectCount(LotteryTip tip) {
        int count = 0;
        for (int number : tip.numbers()) {
            if (winningNumbers.contains(number)) {
                count++;
            }
        }
        return count;
    }

    private String formatTip(LotteryTip tip) {
        StringBuilder sb = new StringBuilder();
        for (int number : tip.numbers()) {
            if (winningNumbers.contains(number)) {
                sb.append(number).append("* ");
            } else {
                sb.append(number).append(" ");
            }
        }
        return sb.toString().trim();
    }
}