import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Audreen Soh
 * <p>
 * This class implements Task object
 */
public class Task {
    private int ID;
    private String title;
    private String description;
    private String category;
    private boolean isDone;
    private LocalDateTime dateTime;

    /**
     * Class constructor.
     *
     * @param ID          The task ID
     * @param title       The task title
     * @param description The task description
     * @param category    The task category
     * @param isDone      The state(Done/not done) of the task
     * @param dateTime    The date and time of the task
     */
    public Task(int ID, String title, String description, String category, boolean isDone, LocalDateTime dateTime) {
        this.ID = ID;
        this.title = title;
        this.description = description;
        this.category = category;
        this.isDone = isDone;
        this.dateTime = dateTime;
    }

    /**
     * Class constructor.
     *
     * @param date The date of the task
     */
    public Task(LocalDate date) {
        this.dateTime = LocalDateTime.of(date, LocalTime.now());
    }

    /**
     * Class constructor.
     */
    public Task() {
    }

    /**
     * Getters and setters
     */
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    //Custom date and time getters and setters

    /**
     * getDate returns dateTime as a LocalDate object.
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * getDateTimeToString returns the string of formatted datetime.
     */
    public String getDateTimeToString() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm"));
    }

    /**
     * getDateToString returns the string of formatted date.
     */
    public String getDateToString() {
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    /**
     * getDateToString returns the string of formatted time.
     */
    public String getTimeToString() {
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    /**
     * setDateTimeFromString Converts a datetime string to LocalDateTime object.
     *
     * @param dt the string value of a datetime
     */
    public void setDateTimeFromString(String dt) {
        this.dateTime = LocalDateTime.parse(dt, DateTimeFormatter.ofPattern("dd-MM-yyyy | HH:mm"));
    }

    /**
     * setTime Converts a time string to LocalDateTime object.
     *
     * @param time The string value of a time
     */
    public void setTime(String time) {
        this.dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.parse(time, DateTimeFormatter.ofPattern("HH:mm")));
    }
}
