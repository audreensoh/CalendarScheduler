import javax.swing.*;
import java.awt.*;

/**
 * @author Audreen Soh
 * <p>
 * This class creates the day label used in the calender.
 */
public class DayLabel extends JLabel {
    /**
     * Class constructor.
     *
     * @param s          The string value to show in the label
     * @param background The background color
     * @param foreground The text color
     * @param btn        Tells if the label is a button
     */
    public DayLabel(String s, Color background, Color foreground, boolean btn) {
        setText(s);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Helvetica", Font.BOLD, 15));
        setOpaque(true);
        setBackground(background);
        setForeground(foreground);
        // if the label is a valid day, make it display as clickable.
        if (btn) {
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }
}
