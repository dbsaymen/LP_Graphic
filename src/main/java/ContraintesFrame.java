import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContraintesFrame extends JFrame {
    public static double A[][] ;
    public static double b[];
    public static String op[];

    String[] operations = {"<=",">=","="};
    JComboBox[] operationsBox;

    JPanel container = new JPanel(new GridBagLayout());
    JButton validationBtn = new JButton("Validate");
    JTextField [][] JtfTable;

    int nbContraintes;
    public ContraintesFrame(final int nbContraintes){


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.nbContraintes=nbContraintes;
        setTitle("DGIMA");
        setSize(600, 400);

        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JtfTable=new JTextField[nbContraintes][3];
        operationsBox=new JComboBox[nbContraintes];
        A=new double[nbContraintes][2];
        b=new double[nbContraintes];
        op=new String[nbContraintes];

        for (int i = 0; i < nbContraintes; i++) {
            operationsBox[i]=new JComboBox(operations);
        }

        for (int i = 0; i < nbContraintes; i++) {
            for (int j = 0; j < 3; j++) {
                JtfTable[i][j]=new JTextField("");
                JtfTable[i][j].setPreferredSize(new Dimension(30, 30));
            }
        }


        container.setBackground(Color.WHITE);
        container.setPreferredSize(new Dimension( 400,nbContraintes*55));
        JScrollPane scrollFrame = new JScrollPane(container);
        container.setAutoscrolls(true);
        scrollFrame.setPreferredSize(new Dimension( 380,300));


        GridBagConstraints c =new GridBagConstraints();
        c.insets=new Insets(10,10,10,10);
        c.gridx=0;
        c.gridy=0;
        for (int i = 0; i < nbContraintes; i++) {
            c.gridy=i;
            c.gridx=0;
            container.add(JtfTable[i][0],c);
            c.gridx=4;
            container.add(new JLabel("x1+"),c);
            c.gridx=8;
            container.add(JtfTable[i][1],c);
            c.gridx=12;
            container.add(new JLabel("x2"),c);
            c.gridx=20;
            container.add(operationsBox[i],c);
            c.gridx=24;
            container.add(JtfTable[i][2],c);
        }


        validationBtn.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed
                for (int i = 0; i < nbContraintes; i++) {
                    try{
                        A[i][0]=Double.parseDouble(JtfTable[i][0].getText());
                        A[i][1]=Double.parseDouble(JtfTable[i][1].getText());
                        op[i]=(String)operationsBox[i].getSelectedItem();
                        b[i]=Double.parseDouble(JtfTable[i][2].getText());
                    }catch (Exception s){
                        new ErrorFrame();
                    }

                }

                setVisible(false);

            }
        });

        c.gridy=nbContraintes;
        container.add(validationBtn,c);

        this.add(scrollFrame);

    }


}
