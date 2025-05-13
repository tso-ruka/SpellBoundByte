import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;



public class Calculator {
    int boardWidth = 360;
    int boardiHeight = 540;

    Color customLightGray = new Color(212,212,210);
    Color customDarkGray = new Color(80,80,80);
    Color customBlack = new Color(28,28,28);
    Color customOrange = new Color(255,149,0);
    Color customWhite = new Color(247, 244, 243);
    Color customJet = new Color(45, 45, 42);

    String[] buttonValues = {
        "AC", "+/-", "%", "+", 
        "7", "8", "9", "×", 
        "4", "5", "6", "-", 
        "1", "2", "3", "+", 
        "0", ".", "√", "=", 
    };

    String [] rightSymbols = {"+" , "×" , "-" , "÷", "="};
    String [] topSymbols = {"AC" , "+/-" , "%" };

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    //varibles for calculations A+B, A-B, A*B, A/B
    String A = "0"; 
    String operator = null;
    String B = null;


    @SuppressWarnings("Convert2Lambda")
    Calculator(){
        //window
        //frame.setVisible(true);
        frame.setSize(boardWidth, boardiHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //first panel - diplay results
        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.white);
        displayLabel.setFont(new Font("Century Gothic", Font.PLAIN, 80));
        displayLabel.setHorizontalAlignment((JLabel.RIGHT));
        displayLabel.setText("0");
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        //buttons panel 
        buttonsPanel.setLayout(new GridLayout(5,4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for(int i=0; i< buttonValues.length; i++){
            JButton button = new JButton();
            String buttonValue = buttonValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);

            button.setBorder(new LineBorder(customBlack));

            if(Arrays.asList(topSymbols).contains(buttonValue)){
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)){
                button.setBackground(customOrange);
                button.setForeground(Color.white);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.white);
            }

            buttonsPanel.add(button);

            //Click buttons
            button.addActionListener(new ActionListener() {
                @Override
                @SuppressWarnings("StringEquality")
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    //identify the button clicked
                    String buttonValue = button.getText();
                    if (Arrays.asList(rightSymbols).contains(buttonValue)){
                        if (buttonValue == "="){
                            if (A != null){
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+"){
                                    displayLabel.setText(removeZeroDecimal(numA+numB));
                                } else if (operator == "-"){
                                    displayLabel.setText(removeZeroDecimal(numA-numB));
                                } else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA*numB));
                                } else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA/numB));
                                }
                                clearAll();

                            }
                        } else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;
                        }

                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (null != buttonValue)switch (buttonValue) {
                            case "AC":
                                clearAll();
                                displayLabel.setText("0");
                                break;
                            case "+/-":{
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay *= -1;
                                // 584 >> -584.0
                                //displayLabel.setText(Double.toString(numDisplay));
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                                    break;
                                }
                            case "%":{
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay /= 100;
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                                    break;
                                }
                            default:
                                break;
                        }

                    } else /*digits or . */ {
                        if (buttonValue == "."){
                            if (!displayLabel.getText().contains(buttonValue)){
                                 displayLabel.setText(displayLabel.getText() + buttonValue);
                            }

                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0"){
                                displayLabel.setText(buttonValue);
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            } 
                        } else if (buttonValue == "√") {
                            if (displayLabel.getText() == "0"){
                                displayLabel.setText("0");
                            } else {
                                //calcola la radice quadrata del numero 
                                double numDisplay = Double.parseDouble(displayLabel.getText());
                                numDisplay = Math.pow(numDisplay, 0.5);
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                    }
                }

            });
            frame.setVisible(true);
        }
    }

    void clearAll() {
        A = "0";
        operator = null; 
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            //whole number
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
