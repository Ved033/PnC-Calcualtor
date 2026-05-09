

public class Factorial {
    public static double factoria (double arg){

        double product = arg; // it will store the first value in factorial as
        // n*n-1*n-2*n-3..., where first value is n

        for(int i=2; arg>=i; arg--){
            // the for loop will end on 2 b/c 0!,1! = 1
            if(arg==0 || arg==1){ // for 0!,1! = 1
                product = 1;
                break;
            }
            product = product*(arg-1); // it will produce the stored value
            // new successive value in product
        }
        return product;
    }
}
