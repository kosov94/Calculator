import javax.swing.*;
import java.awt.*;

public class MyJFrame extends JFrame {
    public MyJFrame(){
        setBounds((Toolkit.getDefaultToolkit().getScreenSize().width-160)/2,
                (Toolkit.getDefaultToolkit().getScreenSize().height-160)/2,300,400);
        setTitle("Calculator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(new MyJPanel());
        setVisible(true);
    }

}
