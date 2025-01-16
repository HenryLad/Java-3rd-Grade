package lambdas;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        // Teilaufgabe 1 - Beispielaufrufe
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Erhöhen Sie alle Werte um 1
        update(numbers, n -> n + 1);
        System.out.println("Erhöht um 1: " + numbers);

        // Verdoppeln Sie alle Werte
        update(numbers, n -> n * 2);
        System.out.println("Verdoppelt: " + numbers);

        // Vermindern Sie alle Werte um 2
        update(numbers, n -> n - 2);
        System.out.println("Vermindert um 2: " + numbers);

        // Ersetzen Sie die Werte durch den Abstand vom Mittelwert aller Werte
        double average = numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
        update(numbers, n -> (int) Math.abs(n - average));
        System.out.println("Abstand vom Mittelwert: " + numbers);

        // Weitere Anwendung: Werte quadrieren
        update(numbers, n -> n * n);
        System.out.println("Quadrate der Werte: " + numbers);

        // Teilaufgabe 2 - Beispielaufrufe
        numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        // Alle geraden Zahlen
        System.out.println("Gerade Zahlen: " + filterNumbers(numbers, n -> n % 2 == 0));

        // Alle ungeraden Zahlen
        System.out.println("Ungerade Zahlen: " + filterNumbers(numbers, n -> n % 2 != 0));

        // Alle Vielfachen von 4
        System.out.println("Vielfache von 4: " + filterNumbers(numbers, n -> n % 4 == 0));

        // Alle Primzahlen
        System.out.println("Primzahlen: " + filterNumbers(numbers, Main::isPrime));

        // Weitere Anwendung: Zahlen größer als 5
        System.out.println("Zahlen größer als 5: " + filterNumbers(numbers, n -> n > 5));

        // Teilaufgabe 3 - Beispielaufrufe
        List<String> strings = Arrays.asList("Java", "Lambda", "Funktion");

        // Strings in Großbuchstaben
        System.out.println("Großbuchstaben: " + transformStrings(strings, String::toUpperCase));

        // Strings in Kleinbuchstaben
        System.out.println("Kleinbuchstaben: " + transformStrings(strings, String::toLowerCase));

        // An jeden String "!" anhängen
        System.out.println("Anhängen von !: " + transformStrings(strings, s -> s + "!"));

        // Umgekehrte Strings
        System.out.println(
                "Umgekehrte Strings: " + transformStrings(strings, s -> new StringBuilder(s).reverse().toString()));

        // Weitere Anwendung: String-Länge als Präfix
        System.out.println("String-Länge als Präfix: " + transformStrings(strings, s -> s.length() + ": " + s));

        // Teilaufgabe 4 - Beispielaufrufe
        numbers = Arrays.asList(1, 2, 3, 4, 5);

        // Summe aller Zahlen
        System.out.println("Summe aller Zahlen: " + processNumbers(numbers, n -> true, n -> n));

        // Summe der ungeraden Zahlen
        System.out.println("Summe der ungeraden Zahlen: " + processNumbers(numbers, n -> n % 2 != 0, n -> n));

        // Summe des doppelten Wertes aller ungeraden Zahlen
        System.out.println("Summe des doppelten Wertes der ungeraden Zahlen: "
                + processNumbers(numbers, n -> n % 2 != 0, n -> n * 2));

        // Weitere Anwendung: Summe der Quadrate aller geraden Zahlen
        System.out.println(
                "Summe der Quadrate der geraden Zahlen: " + processNumbers(numbers, n -> n % 2 == 0, n -> n * n));

        // Teilaufgabe 5 - Beispielaufrufe
        numbers = Arrays.asList(-3, -2, -1, 0, 1, 2, 3, 4, 5);

        // Differenzieren nach gerade/ungerade
        System.out.println("Gerade/Ungerade: " + groupByCondition(numbers, n -> n % 2 == 0));

        // Differenzieren nach Zahlen < 5 und >= 5
        System.out.println("< 5 und >= 5: " + groupByCondition(numbers, n -> n < 5));

        // Differenzieren nach positiven und negativen Zahlen
        System.out.println("Positiv/Negativ: " + groupByCondition(numbers, n -> n >= 0));

        // Differenzieren nach Primzahl/nicht Primzahl
        System.out.println("Primzahl/Nicht Primzahl: " + groupByCondition(numbers, Main::isPrime));

        // Weitere Anwendung: Differenzieren nach Vielfachen von 3
        System.out.println("Vielfache von 3/Nicht Vielfache von 3: " + groupByCondition(numbers, n -> n % 3 == 0));
    }

    // Teilaufgabe 1 - update-Methode
    public static void update(List<Integer> numbers, Function<Integer, Integer> updater) {
        for (int i = 0; i < numbers.size(); i++) {
            numbers.set(i, updater.apply(numbers.get(i)));
        }
    }

    // Teilaufgabe 2 - filterNumbers-Methode
    public static List<Integer> filterNumbers(List<Integer> numbers, Predicate<Integer> condition) {
        return numbers.stream().filter(condition).collect(Collectors.toList());
    }

    // Teilaufgabe 3 - transformStrings-Methode
    public static List<String> transformStrings(List<String> strings, Function<String, String> transformer) {
        return strings.stream().map(transformer).collect(Collectors.toList());
    }

    // Teilaufgabe 4 - processNumbers-Methode
    public static Integer processNumbers(List<Integer> numbers, Predicate<Integer> filter,
            Function<Integer, Integer> transformer) {
        return numbers.stream().filter(filter).map(transformer).reduce(0, Integer::sum);
    }

    // Teilaufgabe 5 - groupByCondition-Methode
    public static Map<Boolean, List<Integer>> groupByCondition(List<Integer> numbers, Predicate<Integer> condition) {
        return numbers.stream().collect(Collectors.partitioningBy(condition));
    }

    // Hilfsmethode zur Primzahlprüfung
    private static boolean isPrime(int n) {
        if (n <= 1)
            return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}
