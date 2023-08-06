import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

class BMICalculatorApp extends JFrame {

    private JTextField heightField;
    private JTextField weightField;
    private JLabel resultLabel;

    public BMICalculatorApp() {
        setTitle("BMI Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel heightLabel = new JLabel("Enter Height (cm):");
        add(heightLabel, gbc);

        gbc.gridy++;
        heightField = new JTextField(10);
        add(heightField, gbc);

        gbc.gridy++;
        JLabel weightLabel = new JLabel("Enter Weight (kg):");
        add(weightLabel, gbc);

        gbc.gridy++;
        weightField = new JTextField(10);
        add(weightField, gbc);

        gbc.gridy++;
        JButton calculateButton = new JButton("Calculate BMI");
        add(calculateButton, gbc);

        gbc.gridy++;
        resultLabel = new JLabel("", JLabel.CENTER);
        add(resultLabel, gbc);

        // Calculate BMI on button click
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        // Calculate BMI on pressing Enter key in either height or weight field
        KeyAdapter keyAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    calculateBMI();
                }
            }
        };
        heightField.addKeyListener(keyAdapter);
        weightField.addKeyListener(keyAdapter);
    }

    private void calculateBMI() {
        try {
            double height = Double.parseDouble(heightField.getText()) / 100; // convert cm to meters
            double weight = Double.parseDouble(weightField.getText());

            double bmi = weight / (height * height);
            String result;

            if (bmi < 18.5) {
                result = "You are underweight.";
            } else if (bmi < 25) {
                result = "You are normal weight.";
            } else if (bmi < 30) {
                result = "You are overweight.";
            } else if (bmi < 35) {
                result = "You are obese.";
            } else {
                result = "You are extremely obese.";
            }

            resultLabel.setText(result);
        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter valid height and weight.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BMICalculatorApp app = new BMICalculatorApp();
            app.setVisible(true);
        });
    }
}
