import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Audreen Soh
 * <p>
 * This class implements home page.
 * Home page consists of an interactive calendar and
 * showing a list of task based on the date which the user clicked on in the calendar.
 */
public class Home extends JPanel {

    /**
     * Class constructor.
     *
     * @param frame       The main frame object
     * @param database    The database object
     * @param selectedDay The selected date
     */
    public Home(JFrame frame, Database database, LocalDate selectedDay) {

        // Set up home panel
        setPreferredSize(new Dimension(900, 500));
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setLayout(new GridBagLayout());
        setBackground(null);

        // Get current date and show on the top of the panel
        LocalDate date = LocalDate.now();
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE"));
        JLabel todayLabel = new JLabel(dateString);
        todayLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        todayLabel.setForeground(Color.decode("#4D2508"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.EAST;
        constraints.weighty = 0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 15, 5, 15);
        add(todayLabel, constraints);

        // Add calendar to the left side of the panel
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        add(new Calendar(date.getYear(), date.getMonthValue(), selectedDay, frame, this, database), constraints);

        // Add task to the right side of the panel
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(new Tasks(selectedDay, database, frame, this), constraints);
    }
}
