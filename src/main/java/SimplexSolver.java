import org.apache.commons.math3.optim.OptimizationData;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.LinearConstraint;
import org.apache.commons.math3.optim.linear.LinearConstraintSet;
import org.apache.commons.math3.optim.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optim.linear.Relationship;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Collection;


public class SimplexSolver {
    double c[];
    double z0;
    double A[][];
    double b[];
    String op[];
    String Goal;

    public SimplexSolver(double[] c, double z0, double[][] a, double[] b, String[] op, String goal) {
        this.c = c;
        this.z0 = z0;
        A = a;
        this.b = b;
        this.op = op;
        Goal = goal;
    }

    public void Solving(){
        OptimizationData f = new LinearObjectiveFunction(c,z0);
        OptimizationData constraintSet = getConstraints(A,op,b,c.length);

        OptimizationData goalType= GoalType.MAXIMIZE;

        if(Goal.equals("Min")){
            goalType= GoalType.MINIMIZE;
        }

        try {
            PointValuePair solution = new org.apache.commons.math3.optim.linear.SimplexSolver().optimize(f,constraintSet,goalType);
            Graphe m = new Graphe(z0,c,A,b,solution.getValue(),Max(solution.getPoint()));
        }catch (org.apache.commons.math3.optim.linear.NoFeasibleSolutionException e){
            System.out.println("Aucune Solution réalisable");
        }



    }
    public static double Max(double[] A){
        double max=A[0];
        for (int i = 0; i < A.length; i++) {
            if (A[i]>max) {
                max=A[i];
            }
        }
        return max;
    }

    private static LinearConstraintSet getConstraints(double A[][],String op[],double b[],int nbVar){
        Collection constraints = new ArrayList();
        for (int i = 0; i < b.length; i++) {
            constraints.add(new LinearConstraint(A[i],relation(op[i]),b[i]));
        }

        double[][] id=MatriceIdentité(nbVar);
        for (int i = 0; i < nbVar; i++) {
            constraints.add(new LinearConstraint(id[i],Relationship.GEQ,0));
        }
        LinearConstraintSet constraintSet = new LinearConstraintSet(constraints);
        return constraintSet;
    }

    private static Relationship relation(String s) {
        if(s.equals("<=")){
            return Relationship.LEQ;
        }
        if(s.equals(">=")){
            return Relationship.GEQ;
        }
        if(s.equals("=")){
            return Relationship.EQ;
        }
        return null;
    }


    public static double[][] MatriceIdentité(int nb_ligne){
        double[][] identité=new double[nb_ligne][nb_ligne];
        for(int i=0; i<nb_ligne;i++)
            for (int j=0;j<nb_ligne;j++)
                if(i==j)
                    identité[i][j]=1;
                else
                    identité[i][j]=0;
        return identité;
    };

}
