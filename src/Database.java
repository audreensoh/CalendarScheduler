import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * @author Audreen Soh
 * <p>
 * This database class connects to a H2 database and executes the database queries.
 */
public class Database {
    private String url = "jdbc:h2:mem:test";
    private String user = "sa";
    private String pass = "";
    private Statement statement;

    Connection connection;

    String snapShotFile = "CalendarSchedulerDBSnapshot";

    /**
     * Class constructor which sets up the H2 database connection.
     */
    public Database() {
        try {
            connection = DriverManager.getConnection(url, user, pass);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            // Checks if there is an existing snapshot file to load database data from.
            File f = new File(snapShotFile + ".sql");
            if (f.isFile()) {
                // Set up database with snapshot file.
                DriverManager.getConnection(url + ";INIT=RUNSCRIPT FROM '" + snapShotFile + ".sql'", user, pass);
            } else {
                // First time running the application
                // Create database and create the tables
                createDatabase();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * createDatabase - Creates all the required tables
     */
    public void createDatabase() {
        // Create Tasks table
        String createTaskTable = "CREATE TABLE `task` (`ID` bigint NOT NULL AUTO_INCREMENT, `title` VARCHAR NOT NULL, `description` VARCHAR, `category` VARCHAR, `isDone` BOOLEAN  NOT NULL, `date` VARCHAR NOT NULL,`time` VARCHAR NOT NULL,PRIMARY KEY (`ID`))";
        try {
            statement.execute(createTaskTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create Quotes table
        String createQuoteTable = "CREATE TABLE `quotes` (`ID` bigint NOT NULL AUTO_INCREMENT, `quote` VARCHAR NOT NULL,PRIMARY KEY (`ID`))";
        try {
            statement.execute(createQuoteTable);
            loadQuotes();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Create Notes table
        String createNotesTable = "CREATE TABLE `notes` (`ID` bigint NOT NULL AUTO_INCREMENT, `date` VARCHAR NOT NULL, `note` VARCHAR NOT NULL,PRIMARY KEY (`ID`))";
        try {
            statement.execute(createNotesTable);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * loadQuotes - Retrieve quotes.
     */
    public void loadQuotes() {
        File file = new File("database/quotes.txt");
        // Read in quotes from file and add them to database
        if (file.isFile()) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String quote = br.readLine();
                quote = quote.replace("'", "''");

                while (quote != null) {
                    String insert = "INSERT INTO `quotes`(`quote`) VALUES ('" + quote + "')";
                    quote = br.readLine();
                    try {
                        statement.execute(insert);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * getTasks - Get tasks for a specific date
     *
     * @param date The date string
     *             <p>
     *             return an arrayList of tasks
     */
    public ArrayList<Task> getTasks(String date) {
        ArrayList<Task> tasks = new ArrayList<>();
        String select = "SELECT * FROM `task` WHERE date = '" + date + "'";
        try {
            ResultSet rs = statement.executeQuery(select);
            while (rs.next()) {
                Task t = new Task();
                t.setID(rs.getInt("ID"));
                t.setTitle(rs.getString("Title"));
                t.setDescription(rs.getString("Description"));
                t.setCategory(rs.getString("Category"));
                t.setDone(rs.getBoolean("isDone"));
                t.setDateTimeFromString(rs.getString("Date") + " | " + rs.getString("Time"));
                tasks.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * hasTasks - Check if there is any task for a specific date
     *
     * @param date The date string
     *             <p>
     *             return true if there are tasks, else return false
     */
    public boolean hasTasks(String date) {
        boolean hasEvents = false;
        String select = "SELECT * FROM `task` WHERE date = '" + date + "'";
        try {
            ResultSet rs = statement.executeQuery(select);
            hasEvents = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasEvents;
    }

    /**
     * createTask - Create a task
     *
     * @param t The task to be created
     */
    public void createTask(Task t) {
        t.setTitle(t.getTitle().replace("'", "''"));
        t.setDescription(t.getDescription().replace("'", "''"));
        String insert = "INSERT INTO `task`(`title`, `description`, `category`, `isDone`, `date`, `time`) VALUES ('" + t.getTitle() + "','" + t.getDescription() + "','" + t.getCategory() + "','" + t.isDone() + "','" + t.getDateToString() + "','" + t.getTimeToString() + "')";
        try {
            statement.execute(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateTask - Update a task
     *
     * @param t The task to be updated
     */
    public void updateTask(Task t) {
        t.setTitle(t.getTitle().replace("'", "''"));
        t.setDescription(t.getDescription().replace("'", "''"));
        String update = "UPDATE `task` SET title='" + t.getTitle() + "',description='" + t.getDescription() + "',category='" + t.getCategory() + "',isDone='" + t.isDone() + "',date='" + t.getDateToString() + "',time='" + t.getTimeToString() + "' WHERE ID = " + t.getID() + ";";
        try {
            statement.execute(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * deleteTask - Delete a task
     *
     * @param ID The id of the task to be deleted
     */
    public void deleteTask(int ID) {
        String update = "DELETE FROM `task` WHERE `ID` = " + ID + ";";
        try {
            statement.execute(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * getQuote - Get quote for a specific day in the year
     *
     * @param day The number of the day in the year
     */
    public String getQuote(int day) {
        String select = "SELECT * FROM `quotes` WHERE ID = " + day + ";";
        try {
            ResultSet rs = statement.executeQuery(select);
            if (rs.next()) {
                return rs.getString("quote");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * hasNotes - Check if there is any notes for a specific date
     *
     * @param date The date string
     *             <p>
     *             return true if there are tasks, else return false
     */
    public boolean hasNotes(String date) {
        boolean hasNotes = false;
        String select = "SELECT * FROM `notes` WHERE date = '" + date + "';";
        try {
            ResultSet rs = statement.executeQuery(select);
            hasNotes = rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hasNotes;
    }

    /**
     * getNotes - Get notes for a specific date
     *
     * @param date The date string
     *             <p>
     *             return a string of notes
     */
    public String getNotes(String date) {
        String select = "SELECT * FROM `notes` WHERE date = '" + date + "';";
        try {
            ResultSet rs = statement.executeQuery(select);
            if (rs.next()) {
                return rs.getString("note");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * createNotes - Create notes for a specific date
     *
     * @param date The date string
     * @param note The notes string
     */
    public void createNotes(String date, String note) {
        note = note.replace("'", "''");
        String insert = "INSERT INTO `notes`(`date`,`note`) VALUES ('" + date + "','" + note + "')";
        try {
            statement.execute(insert);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * updateNotes - Update notes for a specific date
     *
     * @param date The date string
     * @param note The notes string
     */
    public void updateNotes(String date, String note) {
        note = note.replace("'", "''");
        String update = "UPDATE `notes` SET note='" + note + "' WHERE date = '" + date + "';";
        try {
            statement.execute(update);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * closeConnection - Close connection and persist data
     */
    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                statement = connection.createStatement();
                // Persist data by writting the data out to a snapshot file
                statement.executeQuery("SCRIPT TO '" + snapShotFile + ".sql'");
                // Close database connection
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
