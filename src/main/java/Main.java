import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame {
    double c[];
    double z0;
    double A[][] ;
    double b[];
    String op[];
    String Goal="Max";

    String[] GoalString = { "Max", "Min"};
    JButton calculationBtn = new JButton("Calculer");
    JButton b2 = new JButton("Ajouter Contraintes");
    JComboBox Goals = new JComboBox(GoalString);
    JPanel container = new JPanel(new GridBagLayout());
    JPanel ConstraintsContainer = new JPanel(new GridBagLayout());
    JPanel container2 = new JPanel(new GridBagLayout());
    JScrollPane ConstraintsContainerscrollFrame = new JScrollPane(ConstraintsContainer);

    JTextField jtf1 = new JTextField("0");
    JTextField jtf2 = new JTextField("0");
    JTextField jtf3 = new JTextField("0");
    JTextField jtf4 = new JTextField("3");



    public Main(){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        setTitle("DGIMA");
        setSize(600, 180);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        container.setBackground(Color.WHITE);
        container2.setBackground(Color.BLACK);
        container2.setPreferredSize(new Dimension( 200,80));


        calculationBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try{
                    c[0]=Double.parseDouble(jtf1.getText());
                    c[1]=Double.parseDouble(jtf2.getText());
                    z0=Double.parseDouble(jtf3.getText());
                    SimplexSolver s= new SimplexSolver(c,z0,A,b,op,Goal);
                    s.Solving();
                }catch (Exception s){
                    new ErrorFrame();
                }
            }
        });

        ConstraintsContainer.setPreferredSize(new Dimension( 200,100));

        ConstraintsContainer.setBackground(Color.BLUE);

        ConstraintsContainerscrollFrame.setPreferredSize(new Dimension( 200,100));
        ConstraintsContainer.setAutoscrolls(true);

        container.setPreferredSize(new Dimension( 500,150));



        setLocationRelativeTo(null);

        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // display/center the jdialog when the button is pressed

                try{
                    int nbSc=Integer.parseInt(jtf4.getText());
                    ContraintesFrame cts=new ContraintesFrame (nbSc);
                    c=new double[2];
                    A=cts.A;
                    b=cts.b;
                    op=cts.op;
                }catch (Exception s){
                    new ErrorFrame();
                }
            }
        });

        Goals.setSelectedIndex(0);
        Goals.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                try{
                    JComboBox cb = (JComboBox)e.getSource();
                    String s = (String)cb.getSelectedItem();
                    Goal=s;
                }catch (Exception f){
                    new ErrorFrame();
                }
            }
        });

        jtf1.setPreferredSize(new Dimension(30, 30));
        jtf2.setPreferredSize(new Dimension(30, 30));
        jtf3.setPreferredSize(new Dimension(30, 30));
        jtf4.setPreferredSize(new Dimension(40, 30));

        GridBagConstraints c =new GridBagConstraints();
        c.insets=new Insets(10,10,10,10);
        c.gridx=0;
        c.gridy=0;
        container.add(Goals,c);
        c.gridx=1;
        container.add(new JLabel("(Z="),c);
        c.gridx=2;
        container.add(jtf1,c);
        c.gridx=3;
        container.add(new JLabel("X1+"),c);
        c.gridx=4;
        container.add(jtf2,c);
        c.gridx=5;
        container.add(new JLabel("X2+"),c);
        c.gridx=6;
        container.add(jtf3,c);
        c.gridx=7;
        container.add(new JLabel(")"));

        c.gridx=0;
        c.gridy=1;
        container.add(new JLabel("Nombre contraintes: "),c);
        c.gridx=1;
        container.add(jtf4,c);
        c.gridx=2;
        container.add(b2,c);
        c.gridy=2;
        container.add(calculationBtn,c);

        this.add(container, BorderLayout.NORTH);
    }


    public static void main(String[] args){
        Main m=new Main();
    }
}
