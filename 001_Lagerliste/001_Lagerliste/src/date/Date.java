package date;

public class Date implements Cloneable {
    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day)
    {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    @Override
    public String toString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }
    @Override
    public Date clone()
    {
        return new Date(year, month, day);
    }
}
