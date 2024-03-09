public class Angle {

    private int degrees;
    private int minutes;
    private int seconds;

    public Angle() {
        this(0, 0, 0);
    }

    public Angle(int degrees, int minutes, int seconds) {
        this.degrees = degrees;
        this.minutes = minutes;
        this.seconds = seconds;
        normalize();
    }

    public static Angle format(String angle) {
        String[] parts = angle.split(":");
        
        int degrees = (parts.length >= 1) ? Integer.parseInt(parts[0]) : 0;
        int minutes = (parts.length >= 2) ? Integer.parseInt(parts[1]) : 0;
        int seconds = (parts.length == 3) ? Integer.parseInt(parts[2]) : 0;

        return new Angle(degrees, minutes, seconds);
    }

    public void normalize() {
        while (seconds >= 60) {
            seconds -= 60;
            minutes++;
        }

        while (minutes >= 60) {
            minutes -= 60;
            degrees++;
        }

        while (seconds < 0) {
            seconds += 60;
            minutes--;
        }

        while (minutes < 0) {
            minutes += 60;
            degrees--;
        }

        while (degrees < 0) {
            degrees += 360;
        }

        degrees %= 360;
    }

    public Angle suma(Angle other) {
        return new Angle(degrees + other.degrees, minutes + other.minutes, seconds + other.seconds);
    }

    public Angle resta(Angle other) {
        return new Angle(degrees - other.degrees, minutes - other.minutes, seconds - other.seconds);
    }

    public int getDegrees() {
        return degrees;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        return String.format("%d:%02d:%02d", degrees, minutes, seconds);
    }
}
