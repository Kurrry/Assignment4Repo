package sait.mms.problemdomain;

public class Movie {
    private int duration;
    private int year;
    private String title;

    public Movie(int duration, int year, String title) {
        setDuration(duration);
        setYear(year);
        setTitle(title);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
