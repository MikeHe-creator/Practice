package com.mikelearner.caculatorui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CalculatorUIcontroller {
    @FXML
    private GridPane ButtonBJ;
    public Label screenTxt;

    public void initialize() {
        for(int i=0; i<4;i++){
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            ButtonBJ.getColumnConstraints().add(col);
        }
        for (Node node : ButtonBJ.getChildren()) {
            if(node instanceof Button b){
                b.setOnAction(event -> {
                    Set<String>variousNum=Set.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9","÷","×","-","+",".");
                    Set<String>jisuanFH=Set.of("÷","×","-","+");
                    if (variousNum.contains(b.getText())) {
                        NumberInput(b, jisuanFH);
                    } else if (b.getText().equals("AC")) {
                        screenTxt.setText("0");
                    } else if (b.getText().equals("CE")) {
                        if(screenTxt.getText().length()-1==0){
                            screenTxt.setText("0");
                        }else{
                            String newScreenTxt =screenTxt.getText().substring(0, screenTxt.getText().length()-1);
                            screenTxt.setText(newScreenTxt);
                        }
                    } else if (b.getText().equals("%")) {
                        PrecentNum(b, jisuanFH);
                    }else if (b.getText().equals("=")) {
                        String ScreenTxt2 = screenTxt.getText();
                        if(!ScreenTxt2.equals("÷0")){
                            CalculationNum(ScreenTxt2);
                        }else{
                            screenTxt.setText("Error");
                        }
                    }
                });
            }
        }
    }

    private void NumberInput(Button b, Set<String> jisuanFH) {
        String currentText = screenTxt.getText();
        if (currentText.endsWith(".") && b.getText().equals(".")) {
            return;
        }
        if (!currentText.isEmpty() && jisuanFH.contains(currentText.substring(currentText.length() - 1))) {
            if (jisuanFH.contains(b.getText())) {
                String newScreenTxt = currentText.substring(0, currentText.length() - 1) + b.getText();
                screenTxt.setText(newScreenTxt);
            } else {
                String newScreenTxt = currentText + b.getText();
                screenTxt.setText(newScreenTxt);
            }
        } else if (!currentText.equals("0") || b.getText().equals(".")) {
            String newScreenTxt = currentText + b.getText();
            screenTxt.setText(newScreenTxt);
        } else {
            String newScreenTxt = b.getText();
            screenTxt.setText(newScreenTxt);
        }
    }

    private void PrecentNum(Button b, Set<String> jisuanFH) {
        if (b.getText().equals("%")) {
            String currentText = screenTxt.getText();
            boolean containsOperator = false;
            int lastOperatorIndex = -1;
            for (String operator : jisuanFH) {
                int index = currentText.lastIndexOf(operator);
                if (index != -1 && index > lastOperatorIndex) {
                    lastOperatorIndex = index;
                    containsOperator = true;
                }
            }
            if (!containsOperator) {
                String newScreenTxt = String.valueOf(Double.parseDouble(currentText) / 100);
                screenTxt.setText(newScreenTxt);
            } else if (lastOperatorIndex != currentText.length() - 1) {
                String beforeOperator = currentText.substring(0, lastOperatorIndex + 1);
                String afterOperator = currentText.substring(lastOperatorIndex + 1);
                String newScreenTxt = beforeOperator + Double.parseDouble(afterOperator) / 100;
                screenTxt.setText(newScreenTxt);
            } else {
                screenTxt.setText(currentText);
            }
        }
    }

    private void CalculationNum(String ScreenTxt2) {
        char[] chars = ScreenTxt2.toCharArray();
        List<Double> numbers = new ArrayList<>();
        List<Character> operators = new ArrayList<>();

        StringBuilder numBuilder = new StringBuilder();
        for (char c : chars) {
            if (Character.isDigit(c) || c == '.') {
                numBuilder.append(c);
            } else if (c == '+' || c == '-' || c == '×' || c == '÷') {
                numbers.add(Double.parseDouble(numBuilder.toString()));
                numBuilder.setLength(0);
                operators.add(c);
            }
        }
        numbers.add(Double.parseDouble(numBuilder.toString()));
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            if (op == '×' || op == '÷') {
                double a = numbers.get(i);
                double b = numbers.get(i + 1);
                double result = (op == '×') ? a * b : a / b;
                numbers.set(i, result);
                numbers.remove(i + 1);
                operators.remove(i);
                i--;
            }
        }

        double finalResult = numbers.getFirst();
        for (int i = 0; i < operators.size(); i++) {
            char op = operators.get(i);
            double b = numbers.get(i + 1);
            finalResult = (op == '+') ? finalResult + b : finalResult - b;
        }
        String formattedResult = formatResult(finalResult);
        screenTxt.setText(formattedResult);
    }

    private String formatResult(double result) {
        BigDecimal bd = new BigDecimal(result);
        return bd.setScale(15, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();
    }
}
