import java.time.LocalDate;
import java.time.LocalTime;

public record Match(Gruppe gruppe,int gameday,LocalDate date, LocalTime time, String hometeam, String guestteam, int goalsHT, int goalsGT, int goalsHTZ, int goalsHGT ){

    @Override
    public final String toString() {
       return String.format("Match Details: Group: %s Gameday: %d Date: %s Time: %s Home Team: %s Guest Team: %s Goals Home Team: %d Goals Guest Team: %d Goals Home Team Half-Time: %d Goals Guest Team Half-Time: %d\n",
        gruppe, gameday, date, time, hometeam, guestteam, goalsHT, goalsGT, goalsHTZ, goalsHGT);
    }


    public String getWinner(){
        if(goalsHT == goalsGT) {
            return "unentschieden";
        } else if (goalsHT > goalsGT) {
            return hometeam;
        } else {
            return guestteam;
        }
    }
}
