package htl.at;

import java.io.File;
import java.nio.file.Path;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analysis {
    public static void main(String[] args) {
        List<Sale> sales = FileReader.readSalesFormFile(Path.of("/home/henry/school/class_3/pose/Java/uebung/streams/SalesAnalysisVorlage/files/sales.csv"));
        // a) Wie viele Verkäufe wurden im Jänner 2024 getätigt und was war der Gesamtumsatz?
        System.out.println("=== a) Wie viele Verkäufe wurden im Jänner 2024?");
        taskA_AnzahlVerkaeufe(sales);

        // b) Welche Verkäufe von Artikel der Kategorie "Lights" wurden im Jänner 2024 nach Italien getätigt, sortiert nach Umsatz
        System.out.println("=== b) Welche Verkäufe von Artikel der Kategorie \"Lights\" wurden im Jänner 2024 nach Italien getätigt, sortiert nach Umsatz?");
        taskB_Lights_Italien(sales);

        // c) Welche 3 Verkäufe haben den höchsten Umsatz?
        System.out.println("=== c) Welche 3 Verkäufe haben im Jänner 2024 den höchsten Umsatz?");
        taskC_Top3Umsatz(sales);

        // d) Gab eess im Jänner 2024 Verkäufe mit der Zahlungsart "Advance Payment" nach Ungarn?
        System.out.println("=== d) Gab es im Jänner 2024 Verkäufe mit der Zahlungsart \"Advance Payment\" nach Ungarn?");
        taskD_AdvancePayment_Ungarn(sales);

        // e) Wie hoch war der Umsatz im Jänner 2024, gruppiert nach Ländern?
        System.out.println("=== e) Wie hoch war der Umsatz im Jänner 2024, gruppiert nach Ländern?");
        taskE_Umsatz_NachLaender(sales);

        // f) Welche Kreditkarten wurden im Jänner 2024 in Deutschland verwendet?
        System.out.println("=== f) Welche Kreditkarten wurden im Jänner 2024 in Deutschland verwendet?");
        taskF_Kreditkarten_Deutschland(sales);

        // g) Wie hoch ist der durchschnittliche Umsatz pro Verkauf im Jänner 2024?
        System.out.println("=== g) Wie hoch ist der durchschnittliche Umsatz pro Verkauf im Jänner 2024?");
        taskG_DurchschnittlicherUmsatz(sales);

        // h) Geben Sie 10 zufällige Verkäufe aus Österreich im Jänner 2024 aus, sortiert nach Umsatz!
        System.out.println("=== h) Geben Sie 10 zufällige Verkäufe aus Österreich im Jänner 2024 aus, sortiert nach Umsatz!");
        taskH_10ZufaelligeVerkaeufe_Oesterreich(sales);

    }

    private static void taskA_AnzahlVerkaeufe(List<Sale> sales) {
        System.out.println(sales.stream()
                    .filter(sale -> sale.date().getMonth().equals(Month.JANUARY))
                    .count());

    }

    private static void taskB_Lights_Italien(List<Sale> sales) {
        System.out.println(
                sales.stream()
                        .filter(sale -> sale.date().getMonth().equals(Month.JANUARY) && sale.date().getYear() == 2024)
                        .filter(sale -> sale.category().equals("Lights") && sale.country().equals("Italy"))
                        .sorted(Comparator.comparing(sale -> sale.amount(), Comparator.reverseOrder()))
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n"))


        );
    }

    private static void taskC_Top3Umsatz(List<Sale> sales) {
            sales.stream()
                    .sorted(Comparator.comparing(sale -> sale.amount(), Comparator.reverseOrder()))
                    .limit(3)
                    .forEach(System.out::println);
    }

    private static void taskD_AdvancePayment_Ungarn(List<Sale> sales) {
        boolean isPayed = sales.stream()
                .filter(sale -> sale.country().equals("Hungary"))
                .filter(sale -> sale.date().getMonth().equals(Month.JANUARY))
                .anyMatch(sale -> sale.payment().equals("Advance Payment"));
        System.out.println(isPayed ? "ja" : "nein");
    }

    private static void taskE_Umsatz_NachLaender(List<Sale> sales) {
        sales.stream()
                .collect(Collectors.groupingBy(Sale::country, Collectors.summarizingDouble(Sale::amount)))
                .forEach((country,amount) ->{
                    System.out.println("Country : " + country + " amout : " + amount.getSum());
        });


    }

    private static void taskF_Kreditkarten_Deutschland(List<Sale> sales) {
        sales.stream()
                .filter(sale -> sale.country().equals("Germany"))
                .filter(sale -> sale.date().getMonth().equals(Month.JANUARY))
                .map(sale -> sale.payment())
                .distinct()
                .forEach(System.out::println);

    }

    private static void taskG_DurchschnittlicherUmsatz(List<Sale> sales) {
        System.out.println( sales.stream()
                .filter(sale -> sale.date().getMonth().equals(Month.JANUARY))
                .mapToDouble(Sale::amount)
                .average()
                .orElse(0));
    }

        private static void taskH_10ZufaelligeVerkaeufe_Oesterreich(List<Sale> sales) {
            Random rand = new Random();
            Stream.generate(() -> sales.get(rand.nextInt(0,sales.size())))
                    .filter(sale -> sale.date().getMonth().equals(Month.JANUARY))
                    .filter(sale -> sale.country().equals("Austria"))
                    .distinct()
                    .limit(10)
                    .forEach(System.out::println);
        }


}