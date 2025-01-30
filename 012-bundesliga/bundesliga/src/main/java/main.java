import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

public class main {

   public static void main(String[] args) {
      final Path path = Path.of("/home/henry/school/class_3/POSE/Java/012-bundesliga/files/bundesliga_23_24.csv");
      final List<Match> matches = MatchFileReader.readMatchesFromFile(path);
      final String favTeam = "LASK";
      

      Predicate<Match> isFavTeamPlaying = line -> 
      (line.hometeam().equals(favTeam) || line.guestteam().equals(favTeam));
      // a
      matches.stream()
         .filter(line -> line.hometeam().equals(favTeam))
         .forEach(System.out::print);
      System.out.println();
      // b
      matches.stream()
         .filter(line -> line.hometeam().equals(favTeam) && line.gruppe().equals(Gruppe.Gesamtgruppe))
         .forEach(System.out::print);
      // c 
      System.out.println();
      matches.stream()
         .filter(line -> line.hometeam().equals(favTeam) || line.guestteam().equals(favTeam))
         .filter(line -> line.gruppe().equals(Gruppe.Meistergruppe))
         .distinct()
         .forEach(System.out::print);      
      // d
      System.out.println();
      matches.stream()
         .filter(isFavTeamPlaying)
         .forEach(System.out::print);
      // e 
      matches.stream()
         .filter(line -> line.getWinner().equals("unentschieden"))
         .forEach(System.out::print);
      // f
      matches.stream()
         .filter(isFavTeamPlaying)
         .filter(line -> line.goalsGT() == 0 && line.goalsHT() == 0)
         .forEach(System.out::print);
      // g
      long count = matches.stream()
         .filter(isFavTeamPlaying)
         .filter(line -> line.getWinner().equals(favTeam))
         .count();
      System.out.println(count);
         
      // h 
      int totalGoals = matches.stream()
         .filter(isFavTeamPlaying)
         .mapToInt(line -> {
         if (line.hometeam().equals(favTeam)) {
            return line.goalsHT();
         } else {
            return line.goalsGT();
         }
         })
         .sum();
      System.out.println(totalGoals);

      // i
      int diff = matches.stream()
         .filter(isFavTeamPlaying)
         .mapToInt(line ->{
            return line.goalsHT() - line.goalsGT();
         })
         .sum();

      System.out.println(diff);

   }

}
