import java.time.LocalDate;
import java.util.List;

public record Person(
        int id,
        String lastname,
        String firstname,
        char gender,
        LocalDate dayOfBirth,
        int height,
        double weight_T1,
        double weight_T2) {


    public String toString() {
        return String.format("%-4d %-15s %-10s %c %s (%d) BMI %.1f-%s %4d %5.1f | %5.1f (%+.1f)",
                id, lastname, firstname, gender, dayOfBirth, getAge(), getBMI(), getBMIClass(), height, weight_T1, weight_T2, (weight_T2 - weight_T1));
    }

    public boolean hasAborted() {
        if(weight_T2 == 0) {
            return true;
        }
        return false;
    }

    public int getAge() {
        final LocalDate today = LocalDate.now();
        final int days = today.getDayOfYear();
        final int yearDiff = today.getYear() - dayOfBirth.getYear();
        if(today.getDayOfYear() < dayOfBirth.getDayOfYear()){
            return yearDiff - 1;
        }else {
            return yearDiff;
        }

    }

    public double getBMI() {
        return weight_T1 / ((double) height / 100) * ((double) height / 100);
    }

    public String getBMIClass() {
        final double BMIIndex = getBMI();
        if(BMIIndex <   18.5) {return "underweight";}
        else if (BMIIndex >= 18.5 && BMIIndex < 25 ) {return "normal weight" ;}
        else if(BMIIndex >= 25 && BMIIndex < 30){return "overweight";}
        else if (BMIIndex >= 30) {return "obese";}
        else {
            return "invalid BMI Index";
        }

    }

}
