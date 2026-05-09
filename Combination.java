

public class Combination {
    public static double comb(double n, double r){

        Factorial f = new Factorial();// uses the factorial method from factorial class
        // the formula of ncr =  n!/r!(n-r)!
        double n_fac = f.factoria(n);
        double r_fac = f.factoria(r);
        double n_r_fac = f.factoria(n-r);

        double comb = n_fac/(r_fac*n_r_fac);
        return comb;
    }
}
