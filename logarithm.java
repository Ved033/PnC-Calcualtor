public class logarithm {

    public static double ln(String input){
        double value = Double.valueOf(input.substring(3, input.length()-1));
        System.out.println(value);
        return Math.log(value);
    }
    public static double logb(String input){
        
        int indexBrace = input.indexOf('(');
        double value = Double.valueOf(input.substring(indexBrace+1, input.length()-1));
        String baseString = input.substring(3, indexBrace);
        // System.out.println(baseString);
        
    
        if (baseString == "" && input.substring(0, 3).equals("log")){
            return (Math.log(value))/Math.log(10);
        }else{
            double base = Double.valueOf(baseString);
            return (Math.log(value))/Math.log(base);
        }
        
        
    }
    public static void main(String[] args){

        System.out.println(ln("ln(2)"));
        // System.out.println(log("log(10)"));
        // System.out.println(logb("log2(2)"));
        System.out.println(logb("log(2)"));
        System.out.println(logb("log10(2)"));
        
    }
}
