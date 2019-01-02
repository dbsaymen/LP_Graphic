import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Random;
import javax.swing.*;

public class Graphe extends JFrame {
    double Ct[];
    double z0;
    double result;
    double Solution;
    double M[][];
    double b[];
    double coef;
    boolean first;
    double Step;
    Color ColorsTable[];
    JButton closeBtn = new JButton("Fermer");


    public Graphe(double z0, double Ct[], double M[][], double b[], double Solution, double maxPt) {
        setTitle("DGIMA");
        setSize(460,560);
        setResizable(false);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setVisible(true);
        first=true;
        this.Ct=Ct;
        this.result=z0;
        this.M=M;
        this.b=b;
        if(maxPt<20){
            this.coef=20;
        }else{
            this.coef=460/(maxPt*2);
        }
        this.Solution=Solution-z0;
        this.Step=(this.Solution-result)/5;
        this.ColorsTable=new Color[M.length];
        for(int i=0;i<M.length;i++){
            ColorsTable[i]=RandomColor();
        }
        this.z0=z0;
    }
    public void setResult(double result){
        this.result=result;
    }
    public void paint(Graphics g2){
        super.paint(g2);
        Graphics2D g = (Graphics2D) g2;
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        g.drawString("X2",45,65);
        g.drawString("X1",460-45,480-20);

        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(4));
        //X-Axis
        Line2D X=new Line2D.Double(0,440,460,440);
        g.draw(X);
        //Y-Axis
        Line2D Y =new Line2D.Double(40,0,40,480);
        g.draw(Y);

        g.draw(new Line2D.Double(40,25,50,40));
        g.draw(new Line2D.Double(40,25,30,40));
        g.draw(new Line2D.Double(460,440,460-15,440-10));
        g.draw(new Line2D.Double(460,440,460-15,440+10));
        //g.draw();

        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(1));
        double NbYaxes=(480/coef)+1;
        double NbXaxes=(460/coef)+1;

        for(int i=1;i<NbYaxes;i++){
            Line2D L=new Line2D.Double(0,440-(i*coef),460,440-(i*coef));
            g.draw(L);
        }
        for(int i=1;i<NbXaxes;i++){
            Line2D L=new Line2D.Double(40+(i*coef),0,40+(i*coef),480);
            g.draw(L);
        }

        g.setStroke(new BasicStroke(2));

        for(int i=0;i<M.length;i++){
            g.setColor(ColorsTable[i]);
            try {
                if(first) Thread.sleep(1000);

                if((M[i][0]!=0) || (M[i][1]!=0)){
                    if(M[i][0]==0){
                        Line2D L=new Line2D.Double(0,440-(b[i]/M[i][1]*coef),460,440-(b[i]/M[i][1]*coef));
                        g.draw(L);
                    }
                    if(M[i][1]==0){
                        Line2D L=new Line2D.Double(40+(b[i]/M[i][0]*coef),0,40+(b[i]/M[i][0]*coef),480);
                        g.draw(L);
                    }
                    if(b[i]==0){
                        double Z[][] = {{calculX(440, M[i], b[i]), 440}, {calculX(-440, M[i], b[i]), -440}};
                        Line2D L=new Line2D.Double(40 + (Z[0][0] * coef), 440 - (Z[0][1] * coef), 40 + (Z[1][0] * coef), 440 - (Z[1][1] * coef));
                        g.draw(L);
                    }
                    else{
                        double cord[][]={{0,b[i]/M[i][1]},{b[i]/M[i][0],0}};
                        double vect[]={cord[0][0]-cord[1][0],cord[0][1]-cord[1][1]};
                        double a=(cord[0][1]-440)/vect[1];
                        double b=(cord[0][1]+440)/vect[1];
                        double edges[][]={{cord[0][0]-a*vect[0],440},{cord[0][0]-b*vect[0],-440}};
                        Line2D L=new Line2D.Double(40+(edges[0][0]*coef), 440-(edges[0][1]*coef), 40+(edges[1][0]*coef), 440-(edges[1][1]*coef));
                        g.draw(L);
                        //g.draw(new Line2D.Double(230+(cord[0][0]*coef), 240-(cord[0][1]*coef), 230+(cord[1][0]*coef), 240-(cord[1][1]*coef)));
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        g.setColor(Color.BLUE);
        g.setStroke(new BasicStroke(4));
        try {
            if(first) Thread.sleep(2000);
            if((Ct[0]!=0) || (Ct[1]!=0)) {
                if (Ct[0] == 0) {
                    double y=((result-z0)/Ct[1])*coef;
                    Line2D L=new Line2D.Double(0,440-y,460,440-y);
                    g.draw(L);
                }
                if (Ct[1] == 0) {
                    double x=((result-z0)/Ct[0])*coef;
                    Line2D L= new Line2D.Double(40+x,0,40+x,480);
                    g.draw(L);
                } else {
                    double Z[][] = {{calculX(440, Ct, result), 440}, {calculX(-440, Ct, result), -440}};
                    Line2D L=new Line2D.Double(40 + (Z[0][0] * coef), 440 - (Z[0][1] * coef), 40 + (Z[1][0] * coef), 440 - (Z[1][1] * coef));
                    g.draw(L);
                    Thread.sleep(800);
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        first=false;
        if(result<Solution){
            result+=Step;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
        }
        g.setColor(Color.RED);
        g.drawString("Z= "+(Solution+z0),200,530);
    }

    public double calculX(double y,double F[],double Z){
        double x=0;
        try{
            x=F[2];
        }catch (Exception e){
            x=0;
        }
        return ((Z-(F[1]*y+x))/F[0]);
    }
    public Color RandomColor(){
        Random rand = new Random();
        int R=rand.nextInt(250);
        int G=rand.nextInt(250);
        int B=rand.nextInt(250);
        return new Color(R,G,B);
    }






    /*public static void main(String[] args) {
        double c[]={3,1};
        double z0=0;
        double A[][] ={{-2,1},{1,0},{-1,1},{-2,-1}};
        double b[]={4,6,-5,-2};
        String op[]={"<=","<=",">=","<="};
        double max=18;
        double result=34;

        Graphe Draw = new Graphe(z0,c,A,b,result,max);
    }*/
}
