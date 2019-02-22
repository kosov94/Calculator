import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyJPanel extends JPanel {
    private JTextField display;
    private JPanel panel;
    private int countSkob=0;


    public MyJPanel() {
        setLayout(new BorderLayout());
        display = new JTextField();
        add(display, BorderLayout.NORTH);

        ActionListener action = new Action();
        ActionListener resault = new Resault();
        ActionListener clear = new Clear();
        ActionListener number = new Number();
        ActionListener minus = new Minus();
        ActionListener skobki = new Skobki();

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 5));

        addButton("7", number);
        addButton("8", number);
        addButton("9", number);
        addButton("(", skobki);
        addButton(")", skobki);

        addButton("4", number);
        addButton("5", number);
        addButton("6", number);
        addButton("+", action);
        addButton("-", action);

        addButton("1", number);
        addButton("2", number);
        addButton("3", number);
        addButton("*", action);
        addButton("/", action);

        addButton("+/-", minus);
        addButton("0", number);
        addButton(".", action);
        addButton("C", clear);
        addButton("=", resault);


        add(panel, BorderLayout.CENTER);
    }

    private class Number implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            display.setText(display.getText() + e.getActionCommand());
        }
    }

    private class Resault implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (display.getText().length() > 0) {
                double otvet = PolIZ.Calculate(display.getText());
                display.setText(String.valueOf(otvet));
            }
        }
    }

    private class Skobki implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if("(".indexOf(e.getActionCommand())!=-1)
                if((display.getText().length()==0)||(IsOperator(display.getText().charAt(display.getText().length()-1)))) {
                    display.setText(display.getText()+e.getActionCommand());
                countSkob++;
                }
                else {
                    display.setText(display.getText()+'*'+e.getActionCommand());
                    countSkob++;
                }
                else
                    if(countSkob>0&&Character.isDigit(display.getText().charAt(display.getText().length()-1))){
                        display.setText(display.getText()+e.getActionCommand());
                        countSkob--;
                    }

        }
    }

    private class Clear implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            display.setText("");
        }
    }

    private class Minus implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (display.getText().length() > 0) {
                int i = display.getText().length() - 1;
                while (i >= 0 && (Character.isDigit(display.getText().charAt(i)) || (".".indexOf(display.getText().charAt(i)) != -1)))
                    i--;
                i++;
                if (i == 0 || ("*/".indexOf(display.getText().charAt(i - 1)) != -1)) {
                    StringBuilder str = new StringBuilder(display.getText() + ' ');
                    for (int j = display.getText().length(); j > i; j--)
                        str.setCharAt(j, str.charAt(j - 1));
                    str.setCharAt(i, '-');
                    display.setText(str.toString());
                } else if (("+".indexOf(display.getText().charAt(i - 1)) != -1)) {
                    StringBuilder str = new StringBuilder(display.getText());
                    str.setCharAt(i - 1, '-');
                    display.setText(str.toString());
                } else if (("-".indexOf(display.getText().charAt(i - 1)) != -1)) {
                    if ((i - 2) < 0 || ("*/".indexOf(display.getText().charAt(i - 2)) != -1)) {
                        StringBuilder str = new StringBuilder(display.getText());
                        for (int j = (i - 2) < 0 ? 0 : i - 1; j < display.getText().length() - 1; j++)
                            str.setCharAt(j, str.charAt(j + 1));
                        str.setCharAt(str.length() - 1, ' ');
                        str.setLength(str.length() - 1);
                        display.setText(str.toString());

                    } else {
                        StringBuilder str = new StringBuilder(display.getText());
                        str.setCharAt(i - 1, '+');
                        display.setText(str.toString());
                    }
                }
            }
        }
    }

    private class Action implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (display.getText().length() > 0 && !IsOperator(display.getText().charAt(display.getText().length() - 1)))
                display.setText(display.getText() + e.getActionCommand());
        }
    }

    private void addButton(String text, ActionListener listener) {
        JButton button = new JButton(text);
        button.addActionListener(listener);
        //button.setMargin(new Insets(15,15,15,15));
        panel.add(button);
    }

    public boolean IsOperator(char ch) {
        if ("+-*/()".indexOf(ch) != -1)
            return true;
        return false;
    }


}

