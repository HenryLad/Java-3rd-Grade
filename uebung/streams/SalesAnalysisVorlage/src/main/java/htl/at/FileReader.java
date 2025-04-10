package htl.at;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<Sale> readSalesFormFile(Path filePath) {
        try {
            List<String> lines = Files.readAllLines(filePath);
            return lines.stream()
                    .skip(1)
                    .map(FileReader::processLine)
                    .toList();
        } catch (RuntimeException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Sale processLine(String line) {

        final DateTimeFormatter DATETIMEFORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    try {
        var parts = line.split(";");
        int orderId = Integer.parseInt(parts[0]);
        String OrderPrio = parts[1];
        LocalDate OrderDate = LocalDate.parse(parts[2], DATETIMEFORMATTER);
        int CoustomerID = Integer.parseInt(parts[3]);
        String country = parts[4];
        int ProductID = Integer.parseInt(parts[5]);
        String Catogory = parts[6];
        int Quantity = Integer.parseInt(parts[7]);
        double Price = Double.parseDouble(parts[8]);
        String Payment = parts[9];

        return new Sale(orderId,OrderPrio,OrderDate,CoustomerID,country,ProductID,Catogory,Quantity,Price,Payment);
    } catch (Exception ignore) {
        System.out.println("Invalid Line : " + line);
        return  null;
    }

    }
}
