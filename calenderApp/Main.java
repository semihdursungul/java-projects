import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

class CalendarApp extends JFrame {

    private JLabel monthLabel;
    private JButton prevButton;
    private JButton nextButton;
    private JPanel calendarPanel;

    private int currentYear;
    private int currentMonth;

    public CalendarApp() {
        setTitle("Calendar Application");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        currentYear = Calendar.getInstance().get(Calendar.YEAR);
        currentMonth = Calendar.getInstance().get(Calendar.MONTH);

        monthLabel = new JLabel("", JLabel.CENTER);
        updateMonthLabel();
        add(monthLabel, BorderLayout.NORTH);

        prevButton = new JButton("<< Prev");
        nextButton = new JButton("Next >>");

        calendarPanel = new JPanel(new GridLayout(7, 7));
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

        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : daysOfWeek) {
            calendarPanel.add(new JLabel(day, JLabel.CENTER));
        }

        Calendar calendar = Calendar.getInstance();
        calendar.set(currentYear, currentMonth, 1);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK);

        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i < firstDayOfMonth; i++) {
            calendarPanel.add(new JLabel(""));
        }

        for (int day = 1; day <= daysInMonth; day++) {
            JButton button = new JButton(String.valueOf(day));

            // Highlight the current day in a different color
            if (currentYear == Calendar.getInstance().get(Calendar.YEAR) &&
                    currentMonth == Calendar.getInstance().get(Calendar.MONTH) &&
                    day == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
                button.setBackground(Color.YELLOW); // Change to your preferred color
            }

            calendarPanel.add(button);
        }

        calendarPanel.revalidate();
        calendarPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalendarApp app = new CalendarApp();
            app.setVisible(true);
        });
    }
}
