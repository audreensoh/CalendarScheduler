import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Audreen Soh
 * <p>
 * This class implements a schedule page of a specific day.
 * Schedule page shows the list of tasks for the selected day.
 * It also has a notes section and at the bottom of the page,
 * There is a motivational quote which changes based on the day of the year.
 */
public class DaySchedule extends JPanel {

    /**
     * Class constructor.
     *
     * @param frame       The main frame object
     * @param selectedDay The selected date
     * @param database    The database object
     */
    public DaySchedule(JFrame frame, LocalDate selectedDay, Database database) {
        //Set up day schedule panel
        setPreferredSize(new Dimension(900, 500));
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        setLayout(new GridBagLayout());
        setBackground(null);

        // Get current date and show on the top of the panel
        LocalDate date = selectedDay;
        String dateString = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy, EEEE"));
        JLabel todayLabel = new JLabel(dateString);
        todayLabel.setFont(new Font("Helvetica", Font.BOLD, 40));
        todayLabel.setForeground(Color.decode("#4D2508"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 0.2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.insets = new Insets(5, 15, 5, 15);
        add(todayLabel, constraints);

        // Show that today's Label is clickable
        todayLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        todayLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Clicking on the today's date label will return to home page.
                resetCalendarSchedular(frame, database);
            }
        });

        // Add DayScheduleTasks table to the right side in the center
        constraints.gridwidth = 1;
        constraints.weightx = 0.7;
        constraints.weighty = 3;
        constraints.gridx = 0;
        constraints.gridy = 1;
        DayScheduleTasks tasks = new DayScheduleTasks(selectedDay, database, frame, this);
        add(tasks, constraints);

        // Add notes section to left side in the center
        constraints.weightx = 0.3;
        constraints.gridx = 1;
        constraints.gridy = 1;
        JPanel notesPanel = createNotesSection(selectedDay, database);
        add(notesPanel, constraints);

        // Add quote of the day to the bottom
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.insets = new Insets(20, 35, 0, 0);
        String quoteString = database.getQuote(selectedDay.getDayOfYear());
        JTextArea quoteLabel = new JTextArea(quoteString);
        quoteLabel.setEditable(false);
        quoteLabel.setWrapStyleWord(true);
        quoteLabel.setLineWrap(true);
        quoteLabel.setFont(new Font("Helvetica", Font.PLAIN | Font.ITALIC, 13));
        quoteLabel.setPreferredSize(new Dimension(700, 50));
        add(quoteLabel, constraints);
    }

    /**
     * createNotesSection - Creates the add notes section.
     *
     * @param selectedDay The date and time of the task
     * @param database    The database object
     */
    private JPanel createNotesSection(LocalDate selectedDay, Database database) {
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setPreferredSize(new Dimension(150, 300));
        textAreaPanel.setBackground(null);

        JLabel notesLabel = new JLabel("Notes: ");
        notesLabel.setFont(new Font("Helvetica", Font.BOLD, 25));
        notesLabel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        textAreaPanel.add(notesLabel, BorderLayout.NORTH);

        JTextArea textArea = new JTextArea();
        Boolean isNew = !database.hasNotes(selectedDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        if (!isNew) {
            textArea.setText(database.getNotes(selectedDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
        }
        textArea.setFont(new Font("Helvetica", Font.PLAIN, 15));
        textArea.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        textArea.setLineWrap(true);
        textArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isNew) {
                    database.createNotes(selectedDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), textArea.getText());
                } else {
                    database.updateNotes(selectedDay.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), textArea.getText());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(textArea);
        textAreaPanel.add(scrollPane);
        return textAreaPanel;
    }

    /**
     * resetCalendarSchedular - Return to and reset the parent panel(Calendar Panel) to reflect the task changes made.
     *
     * @param frame    The main frame object
     * @param database The database object
     */
    private void resetCalendarSchedular(JFrame frame, Database database) {
        frame.getContentPane().removeAll();
        Home homePanel = new Home(frame, database, LocalDate.now());
        frame.add(homePanel);
        frame.revalidate();
    }
}
