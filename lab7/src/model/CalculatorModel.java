package model;import java.util.ArrayList;
import java.util.List;

public class CalculatorModel {
    private double memory;
    private double currentOperand;
    private String currentOperation;
    private List<Double> memoryHistory;

    public CalculatorModel() {
        memory = 0;
        currentOperand = 0;
        currentOperation = "";
        memoryHistory = new ArrayList<>();
    }

    public void setOperand(double operand) {
        this.currentOperand = operand;
    }

    public void setOperation(String operation) {
        this.currentOperation = operation;
    }

    public double executeOperation(double operand2) {
        double result = 0;
        switch (currentOperation) {
            case "+":
                result = currentOperand + operand2;
                break;
            case "-":
                result = currentOperand - operand2;
                break;
            case "*":
                result = currentOperand * operand2;
                break;
            case "/":
                if (operand2 != 0)
                    result = currentOperand / operand2;
                else
                    throw new ArithmeticException("Division by zero");
                break;
            case "√":
                if (currentOperand >= 0)
                    result = Math.sqrt(currentOperand);
                else
                    throw new ArithmeticException("Square root of negative number");
                break;
            case "x^2":
                result = Math.pow(currentOperand, 2);
                break;
        }
        memory = result; // Update memory with the result of the operation
        memoryHistory.add(result);
        return result;
    }

    public double addToMemory(double value) {
        memory += value;
        memoryHistory.add(memory);
        return memory;
    }

    public double subtractFromMemory(double value) {
        memory -= value;
        memoryHistory.add(memory);
        return memory;
    }

    public void clearMemory() {
        memory = 0;
        memoryHistory.clear();
    }

    public double recallMemory() {
        return memory;
    }

    public List<Double> getMemoryHistory() {
        return memoryHistory;
    }

    public double getCurrentOperand() {
        return currentOperand;
    }

    public String getCurrentOperation() {
        return currentOperation;
    }
}
