import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class    Analysis {
    public static void main(String[] args) {
        // TODO: Read persons from file
        List<Person> persons = PersonMocks.getPersonMocks();
        //List<Person> personList = FileReader.readPersonsFromFile(Path.of("/home/henry/school/class_3/pose/Java/uebung/streams/PLF_Diet_Vorlage/files/data.csv"))

       /* // Task    a)
        System.out.println(
                persons.stream()
                        .filter(Person::hasAborted)
                        .collect(Collectors.counting())
        );

        // Task     b)
        System.out.println(
                persons.stream()
                        .filter(person -> person.getAge() > 60)
                        .filter(person ->  (person.weight_T2() - person.weight_T1()) >=  2)
                        .sorted(Comparator.comparingInt(person -> person.getAge()))
                        .map(String::valueOf)
                        .collect(Collectors.joining(" \n"))
        );

        // Task c)
        System.out.println(
                persons.stream()
                        .filter(person -> person.weight_T2() != 0)
                        .sorted(Comparator.comparingDouble(person -> (double) (person.weight_T2() - person.weight_T1())))
                        .map(String::valueOf)
                        .limit(3)
                        .collect(Collectors.joining(" \n"))
        );
        // Task d)

        System.out.println(
                persons.stream()
                        .filter(person -> person.gender() == 'M')
                        .filter(person -> person.height() >= 180)
                        .filter(person -> person.weight_T1() < 70)
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n"))
        );

        // Task e)

        System.out.println(
                persons.stream()
                        .filter(person -> person.weight_T2() - person.weight_T1() >= 5)
                        .collect(Collectors.groupingBy(person -> person.weight_T2() - person.weight_T1()))
                        .entrySet()
                        .stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n"))
        );

        // Task f

        System.out.println(
                persons.stream()
                        .filter(person -> person.gender() == 'W')
                        .filter(person -> person.firstname().charAt(0) == 'N')
                        .distinct()
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n"))
        );


        // Task g)

        System.out.println(
                persons.stream()
                        .filter(person -> person.gender() == 'M')
                        .filter(person -> person.height() > 180)
                        .collect(Collectors.averagingDouble(person -> person.weight_T2() - person.weight_T1()))
        );*/

        // Task h)

        System.out.println(
                persons.stream()
                        .filter(person -> person.weight_T2() != 0)
                        .filter(person -> person.weight_T2() - person.weight_T1() < 0)
                        .sorted(Comparator.comparing(person -> Math.random() ))
                        .limit(10)
                        .map(String::valueOf)
                        .collect(Collectors.joining("\n"))
        );





    }
}
