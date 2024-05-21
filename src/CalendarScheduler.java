import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;

/**
 * @author Audreen Soh
 * <p>
 * This class implements a calendar task schedular.
 * <p>
 * Main Functionalities:
 * This includes an interactive calendar.
 * Manage(Create,edit and delete) task.
 * Add notes of the day.
 * One motivational auote a day for 365 days.
 */
public class CalendarScheduler {
    public static void main(String[] args) {
        // Set up frame
        JFrame frame = new JFrame("Calendar Scheduler");

        //Change frame icon
        ImageIcon img = new ImageIcon("resources/calendar-icon.png");
        frame.setIconImage(img.getImage());
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // Start database
        Database database = new Database();

        // Show home panel
        Home homePanel = new Home(frame, database, LocalDate.now());
        frame.getContentPane().add(homePanel);
        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                // Close database connection and persist data
                database.closeConnection();
                // exit application.
                System.exit(0);
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {

            }
        });
        frame.setVisible(true);
    }
}
