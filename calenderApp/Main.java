import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

class StyledCalendarApp extends JFrame {

    private JLabel monthLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel calendarPanel;

    private int currentYear;
    private int currentMonth;

    public StyledCalendarApp() {
        setTitle("Calendar Application by semihdursungul");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        monthLabel = new JLabel("", JLabel.CENTER);
        updateMonthLabel();
        monthLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(monthLabel, BorderLayout.NORTH);

        prevButton = new JButton("<< Prev");
        nextButton = new JButton("Next >>");

        calendarPanel = new JPanel(new GridBagLayout());
        updateCalendar();

        prevButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMonth == 0) {
                    currentMonth = 11;
                    currentYear--;
                } else {
                    currentMonth--;
                }
                updateMonthLabel();
                updateCalendar();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentMonth == 11) {
                    currentMonth = 0;
                    currentYear++;
                } else {
                    currentMonth++;
                }
                updateMonthLabel();
                updateCalendar();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.setBackground(Color.WHITE);
        add(buttonPanel, BorderLayout.SOUTH);
        add(calendarPanel, BorderLayout.CENTER);
    }

    private void updateMonthLabel() {
        String[] months = {
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        };
        monthLabel.setText(months[currentMonth] + " " + currentYear);
    }

    private void updateCalendar() {
        calendarPanel.removeAll();
        calendarPanel.setLayout(new GridBagLayout());

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;
        for (String day : daysOfWeek) {
            gbc.gridx = getDayOfWeekIndex(day);
            gbc.anchor = GridBagConstraints.CENTER;
            gbc.insets = new Insets(5, 5, 5, 5); // Add padding
            JLabel dayLabel = new JLabel(day);
            dayLabel.setFont(new Font("Arial", Font.BOLD, 14));
            calendarPanel.add(dayLabel, gbc);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(currentYear, currentMonth, 1);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        int row = 1;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5); // Add padding

        for (int i = 1; i < firstDayOfMonth; i++) {
            gbc.gridx = i - 1;
            calendarPanel.add(new JLabel(""), gbc);
        }

        for (int day = 1; day <= daysInMonth; day++) {
            gbc.gridx = (firstDayOfMonth + day - 2) % 7;
            JButton button = new JButton(String.valueOf(day));
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Arial", Font.PLAIN, 14));
            button.setFocusPainted(false); // Remove button focus border

            // Highlight the current day in a different color
            if (currentYear == Calendar.getInstance().get(Calendar.YEAR) &&
                    currentMonth == Calendar.getInstance().get(Calendar.MONTH) &&
                    day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                button.setBackground(new Color(255, 218, 121)); // Pale yellow
            }

            calendarPanel.add(button, gbc);

            if (gbc.gridx == 6) {
                gbc.gridy++;
            }
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    private int getDayOfWeekIndex(String dayName) {
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            if (dayName.equals(daysOfWeek[i])) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StyledCalendarApp app = new StyledCalendarApp();
            app.setVisible(true);
        });
    }
}
