package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import model.CalculatorModel;

public class CalculatorView {
    private JTextField display;
    private JButton[] digitButtons;
    private JButton[] operatorButtons;
    private JButton[] memoryButtons;
    private JButton exitButton;
    private JButton clearMemoryButton; // New button for clearing memory
    private CalculatorModel calculatorModel;

    public CalculatorView() {
        JFrame frame = new JFrame("Simple Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        display = new JTextField();
        display.setEditable(false);
        frame.add(display, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(6, 4));
        String[] buttonLabels = {
                "0", "1", "2", "3",
                "4", "5", "6", "7",
                "8", "9", "*", "+",
                "/", ".", "=", "-",
                "√", "x^2", "M+", "M-",
                "MRC", "MC", "Delete", "Clear"
        };

        digitButtons = new JButton[10];
        operatorButtons = new JButton[8];
        memoryButtons = new JButton[4];

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i]);
            buttonsPanel.add(button);

            if (i >= 0 && i <= 9) {
                digitButtons[i] = button;
            } else if (i >= 10 && i <= 17) {
                operatorButtons[i - 10] = button;
            } else if (i >= 18 && i <= 21) {
                memoryButtons[i - 18] = button;
            }
        }

        frame.add(buttonsPanel, BorderLayout.CENTER);
        frame.setVisible(true);

        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0)); // Close the application when clicked
        buttonsPanel.add(exitButton);

        calculatorModel = new CalculatorModel();

        // Add memory functionality
        memoryButtons[0].addActionListener(e -> {
            double displayedNumber = Double.parseDouble(getDisplayText());
            calculatorModel.addToMemory(displayedNumber);
            setDisplay("Memory is " + calculatorModel.recallMemory() );
        });

        memoryButtons[1].addActionListener(e -> {
            double displayedNumber = Double.parseDouble(getDisplayText());
            calculatorModel.subtractFromMemory(displayedNumber);
            setDisplay("Memory is " + calculatorModel.recallMemory());
        });

        memoryButtons[3].addActionListener(e -> {
            calculatorModel.clearMemory();
            setDisplay("");
        });

        memoryButtons[2].addActionListener(e -> {
           
            setDisplay("Memory is " + calculatorModel.recallMemory());
        });

        // Add listener for clearing memory
        clearMemoryButton = getButtonByLabel("MC");
        clearMemoryButton.addActionListener(e -> {
            calculatorModel.clearMemory();
            setDisplay(""); // Clear display when memory is cleared
        });
    }

    public void setDisplay(String text) {
        display.setText(text);
    }

    public String getDisplayText() {
        return display.getText();
    }

    public void addDigitListener(ActionListener listener) {
        for (JButton button : digitButtons) {
            button.addActionListener(listener);
        }
    }

    public void addOperatorListener(ActionListener listener) {
        for (JButton button : operatorButtons) {
            button.addActionListener(e -> listener.actionPerformed(e));
        }
    }

    public void addMemoryListener(ActionListener listener) {
        for (JButton button : memoryButtons) {
            button.addActionListener(listener);
        }
    }

    public void addDeleteListener(ActionListener listener) {
        JButton deleteButton = getButtonByLabel("Delete");
        deleteButton.addActionListener(listener);
    }

    public void addClearListener(ActionListener listener) {
        JButton clearButton = getButtonByLabel("Clear");
        clearButton.addActionListener(e -> {
            setDisplay(""); // Set display to empty string when "Clear" button is clicked
            listener.actionPerformed(e); // Delegate the action event to the provided listener
        });
    }


    private JButton getButtonByLabel(String label) {
        Component[] components = ((JPanel) ((JFrame) SwingUtilities.getWindowAncestor(display)).getContentPane().getComponent(1)).getComponents();
        for (Component component : components) {
            if (component instanceof JButton && ((JButton) component).getText().equals(label)) {
                return (JButton) component;
            }
        }
        return null;
    }
}
