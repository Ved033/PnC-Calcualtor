

public class Permutation {
    public static double permut(double n, double r){

        Factorial f = new Factorial(); // uses the factorial method from factorial class
        // the formula for npr = n!/(n-r)!
        double n_fac = f.factoria(n);
        double n_r_fac = f.factoria(n-r);

        double npr = n_fac/n_r_fac;
        return npr;
    }
    public static void main( String [] args){
        System.out.println(permut(8, 4));
    } 
}
