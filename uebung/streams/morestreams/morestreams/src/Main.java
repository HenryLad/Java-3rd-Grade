import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {
        System.out.println("Drucken Sie alle geraden Zahlen von 2 bis 10, getrennt durch Leerzeichen.");
        System.out.println(
                Stream.iterate(2, n -> n + 2)
                        .limit(5)
                        .map(String::valueOf)
                        .collect(Collectors.joining(", ")));
        System.out.println("Drucken Sie alle Primzahlen von 1 – 100, getrennt durch Leerzeichen.");
        System.out.println(
                Stream.iterate(0, n -> n + 1)
                        .limit(100)
                        .filter(new Main()::isPrimayNumber)
                        .map(String::valueOf)
                        .collect(Collectors.joining(" "))

        );
        System.out.println("Drucken Sie die ersten 100 Primzahlen, getrennt durch Leerzeichen.");
        System.out.println(
                Stream.iterate(1, n -> n + 1)
                        .filter(new Main()::isPrimayNumber)
                        .limit(100)
                        .map(String::valueOf)
                        .collect(Collectors.joining(" ")));
        System.out.println("Drucken Sie die Summe ersten 10 Primzahlen.");
        System.out.println(
                IntStream.iterate(1, n -> n + 1)
                        .filter(new Main()::isPrimayNumber)
                        .limit(10)
                        .sum());
        System.out.println(
                "Nutzen Sie iterate, um ein int-Array mit den Primzahlen von 1 bis 59 zu erzeugen. Drucken Sie diese");
        System.out.println(
                Stream.iterate(0, n -> n + 1)
                        .limit(60)
                        .collect(Collectors.toList()));
        System.out.println(
                "Nutzen Sie range, um ein int-Array mit den Primzahlen von 1 bis 59 zu erzeugen. Drucken Sie diese.");
        System.out.println(
                IntStream.range(0, 60)
                        .mapToObj(Integer::valueOf)
                        .collect(Collectors.toList())

        );
        System.out.println(
                "Nutzen Sie rangeClosed, um ein int-Array mit den Primzahlen von 1 bis 59 zu erzeugen. Drucken Sie diese");
        System.out.println(
                IntStream.rangeClosed(0, 59)
                        .mapToObj(Integer::valueOf)
                        .collect(Collectors.toList()));
        System.out.println(
                "Drucken Sie 10 Zufallszahlen von 1 bis 100 (Duplikate sind erlaubt). Nutzen Sie Math.random()");
        System.out.println(
                IntStream.generate(() -> (int) (Math.random() * 100) + 1)
                        .limit(10)
                        .boxed()
                        .collect(Collectors.toList()));
        System.out.println(
                "Drucken Sie 10 Zufallszahlen von 1 bis 100 (Duplikate sind erlaubt). Nutzen Sie die Random-Klasse");
        Random rand = new Random();
        System.out.println(

                IntStream.generate(() -> (int) (rand.nextInt(1, 100)))
                        .mapToObj(Integer::valueOf)
                        .limit(10)
                        .collect(Collectors.toList()));
        System.out.println("Drucken Sie 10 Zufallszahlen von 1 bis 100 (ohne Duplikate).");
        System.out.println(
            IntStream.generate(() -> (new Random().nextInt(101))).limit(10).distinct().mapToObj(String::valueOf).collect(Collectors.joining(" "))
        );
        System.out.println("Drucken Sie 10 Zufallszahlen von 1 bis 100 (ohne Duplikate, in aufsteigender Reihenfolge sortiert)");
        System.out.println(
            IntStream.generate(() -> (new Random().nextInt(101)))
                .distinct()
                .limit(10)
                .boxed()
                .sorted((a, b) -> b - a)
                .map(String::valueOf)
                .collect(Collectors.joining(" "))
        );
        //12
        System.out.println(
            IntStream.iterate(0, n -> n = new Random().nextInt(101))
            .distinct()
            .limit(10)
            .filter(n -> n < 50)
            .boxed()
            .sorted((a, b) -> b - a)
            .map(String::valueOf)
            .collect(Collectors.joining(" "))
        );
        //13
        System.out.println(
            IntStream.generate(() -> (new Random().nextInt(101)))
            .limit(1000)
            .boxed()
            .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
        );

        // 14 
        System.out.println(
            IntStream.generate(() -> (new Random().nextInt(101)))
            .limit(1000)
            .boxed()
            .collect(Collectors.groupingBy(n -> n, Collectors.counting()))
            .entrySet()
            .stream()
            .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
            .limit(3)
            .collect(Collectors.toList())
        );

        // 15 
        System.out.println(Stream.iterate("A", s -> String.valueOf((char) (s.charAt(0) + 1)))
              .limit(26)
              .collect(Collectors.toList()
        ));



    }

    private boolean isPrimayNumber(int number) {
        boolean isPrime = true;
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0) {
                isPrime = false;
                return isPrime;
            }
        }
        return true;
    }
}