import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ErrorFrame extends JFrame {
    JPanel container = new JPanel(new GridBagLayout());
    JButton Btn1 = new JButton("Ok!");


    public ErrorFrame(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setTitle("DGIMA");
        setSize(100, 100);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        container.setBackground(Color.WHITE);

        Btn1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        GridBagConstraints c =new GridBagConstraints();
        c.insets=new Insets(10,10,10,10);
        c.gridx=0;
        c.gridy=0;
        container.add(new JLabel("Erreur de saisie"),c);
        c.gridy=1;
        container.add(Btn1,c);
        add(container,BorderLayout.CENTER);
    }
}
