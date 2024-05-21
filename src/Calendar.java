import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

/**
 * @author Audreen Soh
 * <p>
 * This class implements an interactive calendar.
 * Click on a date - to manage the task of that specific date.
 * Double click on a date - to open the today's page.
 */
public class Calendar extends JPanel {

    /**
     * Class constructor.
     *
     * @param year        The interger representation of the year
     * @param month       The interger representation of the month
     * @param selectedDay The selected date
     * @param frame       The main frame object
     * @param parentPanel The parent panel object
     * @param database    The database object
     */
    public Calendar(int year, int month, LocalDate selectedDay, JFrame frame, JPanel parentPanel, Database database) {

        // set up calendar panel
        setPreferredSize(new Dimension(400, 400));
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 0));
        setBackground(null);

        // top panel which will hold the "< MONTH >" above the calendar
        JPanel top = new JPanel(new BorderLayout());
        top.setPreferredSize(new Dimension(350, 50));
        top.setBackground(null);

        // Add the months
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        JLabel date = new JLabel(LocalDate.of(year, month, 1).format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        date.setHorizontalAlignment(JLabel.CENTER);
        date.setFont(new Font("Helvetica", Font.BOLD, 25));
        date.setForeground(Color.decode("#c1380a"));
        top.add(date, BorderLayout.CENTER);

        // Add '<' arrow label which acts like a button to go to the previous month
        JLabel left = new JLabel(new ImageIcon("resources/arrow-left.png"));
        left.setCursor(new Cursor(Cursor.HAND_CURSOR));
        left.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                parentPanel.removeAll();

                // Checks when the left '<' clicked. this decides if it is moving left by one month/one year
                // if it is january, we have to perform year - 1, else month - 1
                if (month != 1) {
                    resetMainPanel(frame, parentPanel, selectedDay, database, new Calendar(year, month - 1, selectedDay, frame, parentPanel, database));
                } else {
                    resetMainPanel(frame, parentPanel, selectedDay, database, new Calendar(year - 1, 12, selectedDay, frame, parentPanel, database));
                }
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
            }
        });
        top.add(left, BorderLayout.WEST);

        // Add '>' arrow label which acts like a button to go to the next month
        JLabel right = new JLabel(new ImageIcon("resources/arrow-right.png"));
        right.setCursor(new Cursor(Cursor.HAND_CURSOR));
        right.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Checks for right '>' clicked. this decides if it is moving right by one month/one year
                // if it is december, we have to perform year + 1, else month + 1
                if (month != 12) {
                    resetMainPanel(frame, parentPanel, selectedDay, database, new Calendar(year, month + 1, selectedDay, frame, parentPanel, database));
                } else {
                    resetMainPanel(frame, parentPanel, selectedDay, database, new Calendar(year + 1, 1, selectedDay, frame, parentPanel, database));
                }
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
            }
        });
        top.add(right, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // Starting to build the calendar
        JPanel days = new JPanel(new GridLayout(7, 7));
        days.setBackground(null);

        // Add days header
        Color header = Color.decode("#aa6231");
        days.add(new DayLabel("S", header, Color.white, false));
        days.add(new DayLabel("M", header, Color.white, false));
        days.add(new DayLabel("T", header, Color.white, false));
        days.add(new DayLabel("W", header, Color.white, false));
        days.add(new DayLabel("T", header, Color.white, false));
        days.add(new DayLabel("F", header, Color.white, false));
        days.add(new DayLabel("S", header, Color.white, false));

        String[] weekDays = new String[]{"SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"};

        // Get the first day(Mon - Sun) of the month.
        LocalDate firstDay = LocalDate.of(year, month, 1);
        int j = 0;

        // Fill up the empty days before 1st day of the month
        while (!firstDay.getDayOfWeek().toString().equals(weekDays[j])) {
            days.add(new DayLabel("", Color.decode("#e3deca"), Color.BLACK, false));
            j++;
        }

        // Get the length of the month(How many days in a specific month)
        int daysNum = YearMonth.of(year, month).lengthOfMonth();
        // Do a loop to populate the days in the calendar
        for (int i = 1; i <= daysNum; i++) {
            final int day = i;
            DayLabel dayLabel;

            if (selectedDay.getYear() == year && selectedDay.getMonthValue() == month && selectedDay.getDayOfMonth() == i) {
                // Set the label of ONE user selected day
                dayLabel = new DayLabel(i + "", Color.decode("#bfbeba"), Color.BLACK, true);
            } else if (database.hasTasks(dateFormatter.format(LocalDate.of(year, month, i)))) {
                // Set the label of the days which have tasks added
                dayLabel = new DayLabel(i + "", Color.decode("#dda35d"), Color.WHITE, true);
            } else {
                // Set the label for the rest of the days
                dayLabel = new DayLabel(i + "", Color.decode("#e3deca"), Color.BLACK, true);
            }

            // Set the label for today
            LocalDate today = LocalDate.now();
            if (today.getYear() == year && today.getMonthValue() == month && today.getDayOfMonth() == i) {
                dayLabel = new DayLabel(i + "", Color.decode("#3c3a1e"), Color.WHITE, true);
            }

            // Add mouse listener to the day labels.
            dayLabel.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Single click - refresh the calendar to show selected day and show the tasks for the selected day.
                    LocalDate selected = LocalDate.of(year, month, day);
                    resetMainPanel(frame, parentPanel, selected, database, new Calendar(year, month, selected, frame, parentPanel, database));

                    if (e.getClickCount() == 2) {
                        // Double click - Open the daySchedule page for the selected day.
                        parentPanel.setVisible(false);
                        DaySchedule daySchedulePanel = new DaySchedule(frame, selected, database);
                        daySchedulePanel.setVisible(true);
                        frame.add(daySchedulePanel);
                    }
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
                }
            });
            days.add(dayLabel);
        }

        // Fill up the remaining empty days after the last day of the month
        for (int i = 0; i < (42 - (j + daysNum)); i++) {
            days.add(new DayLabel("", Color.decode("#e3deca"), Color.BLACK, false));
        }

        add(days, BorderLayout.CENTER);
    }

    /**
     * resetMainPanel - Reset the parent panel to reflect the task changes made.
     *
     * @param parentFrame The main frame object
     * @param mainPanel   The parent panel object
     * @param selectedDay The selected day in the calendar
     * @param database    The database object
     * @param c           The calendar object
     */
    private static void resetMainPanel(JFrame parentFrame, JPanel mainPanel, LocalDate selectedDay, Database database, Calendar c) {
        mainPanel.removeAll();
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
        mainPanel.add(todayLabel, constraints);

        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(c, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(new Tasks(selectedDay, database, parentFrame, mainPanel), constraints);
        mainPanel.revalidate();
    }
}
