import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class FileReader {

    public static List<Person> readPersonsFromFile(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            List<Person> persons = lines.stream()
                    .skip(1)
                    .map(FileReader::processLine)
                    .toList();
            return persons;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static Person processLine(String line) {
        String[] parts = line.split(";");
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        int id = Integer.parseInt(parts[0]);
        String lastname = parts[1];
        String firstname = parts[2];
        char gender = parts[3].charAt(0);
        LocalDate birthday = LocalDate.parse(parts[4],dateTimeFormatter);
        int height = Integer.parseInt(parts[5]);
        double weight_t1 = Double.parseDouble(parts[6]);
        double weight_t2 = Double.parseDouble(parts[7]);

        return new Person(id,lastname,firstname,gender,birthday,height,weight_t1,weight_t2);
    }

}
