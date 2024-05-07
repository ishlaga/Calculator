package controller;

import model.CalculatorModel;
import view.CalculatorView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
    private CalculatorModel model;
    private CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;

        view.addDigitListener(new DigitListener());
        view.addOperatorListener(new OperatorListener());
        view.addMemoryListener(new MemoryListener());
        view.addDeleteListener(new DeleteListener());
        view.addClearListener(new ClearListener());
    }

    class DigitListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String digit = button.getText();
            String currentDisplay = view.getDisplayText();
            view.setDisplay(currentDisplay + digit);
        }
    }

    class OperatorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String operation = button.getText();
            double operand = Double.parseDouble(view.getDisplayText());
            if (operation.equals("=")) {
                double result = model.executeOperation(operand);
                view.setDisplay(String.valueOf(result));
                model.setOperation("");
            } else {
                model.setOperand(operand);
                if (!model.getCurrentOperation().equals("")) {
                    double result = model.executeOperation(operand);
                    view.setDisplay(String.valueOf(result));
                }
                model.setOperation(operation);
                view.setDisplay("");
            }
        }
    }

    class MemoryListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String label = button.getText();
            switch (label) {
                case "M+":
                    model.addToMemory(Double.parseDouble(view.getDisplayText()));
                    break;
                case "M-":
                    model.subtractFromMemory(Double.parseDouble(view.getDisplayText()));
                    break;
                case "MRC":
                    double memoryValue = model.recallMemory();
                    view.setDisplay(String.valueOf(memoryValue));
                    break;
                case "MC":
                    model.clearMemory();
                    break;
            }
        }
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String currentDisplay = view.getDisplayText();
            if (!currentDisplay.isEmpty()) {
                String newDisplay = currentDisplay.substring(0, currentDisplay.length() - 1);
                view.setDisplay(newDisplay);
            }
        }
    }
    class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            view.setDisplay(""); // Clear the display without affecting memory
        }
    }

    public static void main(String[] args) {
        CalculatorModel model = new CalculatorModel();
        CalculatorView view = new CalculatorView();
        CalculatorController controller = new CalculatorController(model, view);
    }
}
