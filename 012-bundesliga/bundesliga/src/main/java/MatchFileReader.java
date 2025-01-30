import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class MatchFileReader {

    public static List<Match> readMatchesFromFile(Path filePath) {
        List<Match> matches = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            // Skip the first line
            br.readLine();
            while ((line = br.readLine()) != null) {
                // Process the line to create a Match object
                Match match = processLine(line);
                if (match != null) {
                    matches.add(match);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }

        return matches;
    }

    private static Match processLine(String line) {
        // Split the line by commas
        String[] values = line.split(",");

        // Convert the values to appropriate types
        Gruppe gruppe = Gruppe.valueOf(values[0].trim()); // Convert String to Gruppe enum
        int gameday = Integer.parseInt(values[1].trim()); // Convert to int
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(values[2].trim(), dateFormatter); // Convert to LocalDate
        LocalTime time = LocalTime.parse(values[3].trim()); // Convert to LocalTime
        String hometeam = values[4].trim(); // Home team (String)
        String guestteam = values[5].trim(); // Guest team (String)
        int goalsHT = Integer.parseInt(values[6].trim()); // Goals Home Team (int)
        int goalsGT = Integer.parseInt(values[7].trim()); // Goals Guest Team (int)
        int goalsHTZ = Integer.parseInt(values[8].trim()); // Goals Home Team Half-Time (int)
        int goalsHGT = Integer.parseInt(values[9].trim()); // Goals Guest Team Half-Time (int)

        // Create and return the Match object
        return new Match(gruppe, gameday, date, time, hometeam, guestteam, goalsHT, goalsGT, goalsHTZ, goalsHGT);
    }

}
