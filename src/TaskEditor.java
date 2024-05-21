import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * @author Audreen Soh
 * <p>
 * TaskEditor This class shows a form UI to add or edit a task.
 */
public class TaskEditor {

    // Categories array for drop down menu
    String[] categories = {"General", "Holiday", "Personal", "Meeting", "Social"};

    /**
     * Class constructor.
     *
     * @param t           This can be a new task or an existing task
     * @param database    The database object
     * @param parentFrame The main frame object
     * @param parentPanel The parent panel object
     */
    public TaskEditor(Task t, Database database, JFrame parentFrame, JPanel parentPanel) {
        int year = t.getDate().getYear();
        int month = t.getDate().getMonthValue();

        // Set title
        JFrame frame = new JFrame("Add Task");
        // Edit mode - Set title as task name
        if (t.getTitle() != null) {
            frame.setTitle(t.getTitle());
        }

        //Set up frame for task editor
        ImageIcon img = new ImageIcon("resources/add.png");
        frame.setIconImage(img.getImage());
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setBackground(Color.WHITE);

        // Set up main panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setPreferredSize(new Dimension(300, 300));
        mainPanel.setBackground(Color.WHITE);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(15, 30, 0, 30);

        // Title
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        titleLabel.setPreferredSize(new Dimension(120, 40));
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(titleLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        titleField.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(titleField, constraints);

        // Time
        constraints.gridx = 0;
        constraints.gridy = 1;
        JLabel timeLabel = new JLabel("Time:");
        timeLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timeLabel.setPreferredSize(new Dimension(120, 40));
        timeLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(timeLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        JTextField timeField = new JTextField();
        timeField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        timeField.setPreferredSize(new Dimension(200, 40));
        mainPanel.add(timeField, constraints);

        // Category
        constraints.gridx = 0;
        constraints.gridy = 2;
        JLabel catagoriesLabel = new JLabel("Category:");
        catagoriesLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        catagoriesLabel.setPreferredSize(new Dimension(100, 40));
        catagoriesLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(catagoriesLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        final JComboBox<String> categoriesField = new JComboBox<String>(categories);
        categoriesField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        categoriesField.setPreferredSize(new Dimension(200, 40));
        // Set dropdown labels to align center
        ((JLabel) categoriesField.getRenderer()).setHorizontalAlignment(JLabel.CENTER);
        mainPanel.add(categoriesField, constraints);

        // Description
        constraints.gridx = 0;
        constraints.gridy = 3;
        JLabel descLabel = new JLabel("Description:");
        descLabel.setFont(new Font("Helvetica", Font.PLAIN, 20));
        descLabel.setPreferredSize(new Dimension(120, 40));
        descLabel.setHorizontalAlignment(JLabel.LEFT);
        mainPanel.add(descLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.weightx = 1;
        JTextArea descField = new JTextArea(3, 0);
        descField.setPreferredSize(new Dimension(200,300));
        descField.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        descField.setFont(new Font("Helvetica", Font.PLAIN, 20));
        JScrollPane scroll = new JScrollPane(descField);
        mainPanel.add(scroll, constraints);

        // set up bottomPanel to hold delete and save button
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 20, 30));
        bottomPanel.setBackground(null);

        //Add delete button
        JButton deleteTaskButton = new JButton("Delete");
        deleteTaskButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        deleteTaskButton.setBackground(Color.decode("#e3deca"));
        deleteTaskButton.setForeground(Color.decode("#3c3a1e"));
        deleteTaskButton.setPreferredSize(new Dimension(40,30));
        deleteTaskButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        bottomPanel.add(deleteTaskButton);

        //Add save button
        JButton saveTaskButton = new JButton("Save");
        saveTaskButton.setFont(new Font("Helvetica", Font.PLAIN, 15));
        saveTaskButton.setBackground(Color.decode("#3c3a1e"));
        saveTaskButton.setForeground(Color.WHITE);
        saveTaskButton.setPreferredSize(new Dimension(40,30));
        saveTaskButton.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        bottomPanel.add(saveTaskButton);

        timeField.setText(t.getTimeToString());

        // Update task
        if (t.getTitle() != null) {
            titleField.setText(t.getTitle());
            descField.setText(t.getDescription());
            categoriesField.setSelectedItem(t.getCategory());
            saveTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (titleField.getText().equals("")) {
                        JOptionPane.showMessageDialog(mainPanel, "Title cannot be empty.");
                        return;
                    }

                    t.setTitle(titleField.getText());
                    t.setDescription(descField.getText());
                    t.setCategory(categoriesField.getSelectedItem().toString());
                    try {
                        t.setTime(timeField.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(mainPanel, "Check time format HH:mm");
                        return;
                    }

                    database.updateTask(t);
                    resetMainPanel(parentFrame, parentPanel, t.getDate(), database, new Calendar(year, month, t.getDate(), parentFrame, parentPanel, database));
                    frame.dispose();
                }
            });

            deleteTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Check if user is sure to delete task
                    int result = JOptionPane.showConfirmDialog(mainPanel, new JLabel("Are you sure to delete " + t.getTitle() + "?"), "Delete " + t.getTitle() + "?", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.OK_OPTION) {
                        database.deleteTask(t.getID());

                        // refresh main view
                        resetMainPanel(parentFrame, parentPanel, t.getDate(), database, new Calendar(year, month, t.getDate(), parentFrame, parentPanel, database));
                        frame.dispose();
                    }
                }
            });
        } else {
            // Create new Task
            // disable delete button
            deleteTaskButton.setVisible(false);
            saveTaskButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (titleField.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Title cannot be empty.");
                        return;
                    }

                    t.setTitle(titleField.getText());
                    t.setDescription(descField.getText());
                    t.setCategory(categoriesField.getSelectedItem().toString());
                    t.setDone(false);
                    try {
                        t.setTime(timeField.getText());
                    } catch (Exception exception) {
                        JOptionPane.showMessageDialog(null, "Check time format HH:mm");
                        return;
                    }

                    database.createTask(t);
                    ArrayList<Task> tasks = database.getTasks(t.getDateToString());
                    // refresh main view
                    resetMainPanel(parentFrame, parentPanel, t.getDate(), database, new Calendar(year, month, t.getDate(), parentFrame, parentPanel, database));
                    frame.dispose();
                }
            });
        }

        frame.add(mainPanel);
        frame.add(bottomPanel,BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    /**
     * resetMainPanel - Reset the parent panel to reflect the task changes made.
     *
     * @param parentFrame The main frame object
     * @param parentPanel The parent panel object
     * @param selectedDay The selected day in the calendar
     * @param database    The database object
     * @param c           The calendar object
     */
    private static void resetMainPanel(JFrame parentFrame, JPanel parentPanel, LocalDate selectedDay, Database database, Calendar c) {
        String name = parentPanel.getClass().toString();

        // Decides which panel to reset and return to.
        // Checks if the parent panel is DaySchedule or Home
        if (name.contains("DaySchedule")) {
            parentFrame.getContentPane().removeAll();
            DaySchedule daySchedulePanel = new DaySchedule(parentFrame, selectedDay, database);
            daySchedulePanel.validate();
            parentFrame.add(daySchedulePanel);
            parentFrame.revalidate();
        } else {
            parentFrame.getContentPane().removeAll();
            Home home = new Home(parentFrame, database, selectedDay);
            home.validate();
            parentFrame.add(home);
            parentFrame.revalidate();
        }
    }
}
