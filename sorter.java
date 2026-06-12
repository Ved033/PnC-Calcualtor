
import java.util.*;
/**
 * The functioning file that calculates the input string with the help of BODMAS rule.
 * @author Ved Kumar
 * @version 1.1
 * 1.0 Normal operations
 * 1.1 Added exponential function
 * 1.2 Added Logarithmic Calculator
 * @since 2025
 */

public class sorter {
    /**
     * This method sorts the input streing into a list of string along with the operators.
     * @param input The input string by the user
     * @return inList The list of strings
     */
    public static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static List<String> log_calc(String value, 
                                String input,
                                String toVal,
                                int startIndex, 
                                int firstBraceIndex,
                                int firstLIndex, 
                                int j,
                                List<String> inList){
        try{
            Double calcLog = 0.0;
            Double dob_value = Double.valueOf(value);
            System.out.println("dobVal: " + dob_value);

            System.out.println(input.substring(startIndex, firstBraceIndex).equals("ln")); // Debugging

            if (input.substring(startIndex, firstBraceIndex).equals("ln")) {
                calcLog = logarithm.ln(toVal);
            
            }else if (input.substring(startIndex, startIndex+3).equals("log")) {
                calcLog = logarithm.logb(toVal);

            }else{
                System.out.println("Check if you mentioned the log and ln terms as per rules.");
            }
            inList.subList(firstLIndex, inList.size()).clear();
            inList.add(String.valueOf(calcLog));
        }catch (Exception e){
            System.out.println(e);
        }
        return inList;
        
    }
    public static int logAdder(String input, int startIndex, List<String> inList){
        
        int in_length = input.length();
        int braceCount = 0; 
        int firstBraceIndex = 0;
        boolean foundOpeningBrace = false;
        int j = startIndex;
        int firstLIndex = 0;
        // int size = inList.size();

        System.out.println("sI: " + startIndex);

        outerloop:
        for (; j < in_length; j++) {
            if (input.charAt(j) == '(') {
                braceCount++;

                
                if(!foundOpeningBrace){
                    firstBraceIndex=j;
                }
                foundOpeningBrace = true;
                
            } else if (input.charAt(j) == ')') {
                braceCount--;
                
                if (foundOpeningBrace && braceCount == 0) {

                    inList.add(input.substring(startIndex, firstBraceIndex));
                    String contentInside = input.substring(firstBraceIndex+1, j);

                    System.out.println("log val: "+ input.substring(startIndex, firstBraceIndex)); // Debugging

                    firstLIndex = inList.size()-1;
                    
                    if (isDouble(contentInside)){

                    }
                    int h = firstBraceIndex;
                    int inListCount = 0;
                    for (; h < j+1; h++){ 

                        if (h==firstBraceIndex){
                            inListCount = inList.size();
                        }

                        String sub = input.substring(firstBraceIndex+1, j);
                        if (  j < in_length && isDouble(sub)  ){
                            
                            inList.add(input.substring(firstBraceIndex, j+1));
                            System.out.println("dobSub: " + sub); //Debugging

                            
                            System.out.println("val: " + sub); // Debugging 
                            System.out.println("sI: " + startIndex + " | fBI: " + firstBraceIndex); // Debugging
                        
                            String toVal = input.substring(startIndex, j+1);
                            System.out.println("toVal: " + toVal);

                            inList = log_calc(sub, input, toVal, startIndex, firstBraceIndex, firstLIndex, j, inList);
                            System.out.println("inList: " + inList);

                            break outerloop;
                        }else {
                            inList.add(input.substring(h, h+1));  
                        }
                        
                    }


                    System.out.println("inList2: " + inList);

                    String value = String.join("", inList.subList(inListCount+1, inList.size()-1));
                    System.out.println("val2: " + value); // Debugging 
                    System.out.println("sI2: " + startIndex + " | fBI2: " + firstBraceIndex); // Debugging

                    String toVal = input.substring(startIndex, j+1);

                    inList = log_calc(value, input, toVal, startIndex, firstBraceIndex, firstLIndex, j, inList);

                    // inList.add(input.substring(firstBraceIndex, j+1));
                    return j; // Return the exact index of the closing ')'
                }    
            }
        }
        return j;


    }
    public static ArrayList<String> sortInput(String input) {
        int in_length = input.length();
        int opCache = -1; // Start with -1 to handle the beginning of the string
        ArrayList<String> inList = new ArrayList<>();
        // HashSet<Integer>  braceIndex = new HashSet<>();

        for (int i = 0; i < in_length; i++) {
            char ch = input.charAt(i);

           
            // METHOD 3 ------------------
            if (ch == 'l' && i + 2 < in_length) {
                // Run logAdder and instantly jump 'i' forward to the closing ')'
                i = logAdder(input, i, inList);
                opCache = i; // Correctly sets cache to the end of the log function
        
            } 
             else if (ch == '+' || ch == '-' || ch == '/' || ch == '*' || ch == '(' || ch == ')' || ch == 'e') {
                if (opCache + 1 < i) {
                    inList.add(input.substring(opCache + 1, i));
                }
                inList.add(Character.toString(ch));
                opCache = i;
            }
        }
        if (opCache+1< in_length) {
            inList.add(input.substring(opCache + 1, in_length));}
        
        System.out.println(inList);
        
        
        return inList;
    }
    /**
     * It breaks down the input string into smaller parts acc to braces,
     * Then initiates the BODMAS rule to of to solve first brackets.
     * If the input string has only one element then it returns the calculated value of factorial, permutaion, combination.
     * @param inputList The input string in the form of list of strings
     */
    public static void indicissor(List<String> inputList) {
        // List to hold indices of all "(" and ")"
        int lastIndex = inputList.size();

        ArrayList<Integer> openIndices = new ArrayList<>();
        ArrayList<Integer> closeIndices = new ArrayList<>();

        for (int i = 0; i < inputList.size(); i++) {
            if (inputList.get(i).equals("(") || inputList.get(i).equals("l")) {
                openIndices.add(i);
            } else if (inputList.get(i).equals(")")) {
                closeIndices.add(i);
            }
        }

        if(inputList.size() == 1){
            String ans = operations("+", inputList.get(0), "0");
            System.out.println("final answer: " + ans); // Debugging output
            System.exit(0);
        }

        Collections.reverse(openIndices); // reverses the order of close indexes to initiate the corresponding braces
        openIndices.add(-1); // Adding 0 to the beginning of the list to handle the beginning of the string
        closeIndices.add(lastIndex); // Adding the last index to the end of the list to handle the end of the string
        invoker(openIndices, closeIndices, inputList);
    }
    /*
     * Invokes the BODMAS rule to solve the first brackets.
     * @param openIndices The list of open indices
     * @param closeIndices The list of close indices
     * @param inputList The input string in the form of list of strings
     */
    public static void invoker(List<Integer> openIndices, List<Integer> closeIndices, List<String> inputList) {
        // Calculating the sublist 
        System.out.println(openIndices);
        System.out.println(closeIndices);

        List<String> tempo;
        for (int i = 0; i < openIndices.size(); i++) {
            int fromIndex = openIndices.get(i);
            int toIndex = closeIndices.get(i);
            tempo = inputList.subList(fromIndex + 1, toIndex);
            System.out.println("i: "+ tempo); // Debugging output
            System.out.println("fI " + fromIndex); // Debugging output
            System.out.println("cI " + toIndex); // Debugging output
            
            bodmasInitializer(inputList, tempo);
            break;
        }
    }
    
    /**
     * Initializes the BODMAS rule by prioritizing the operators.
     * @param inputList The input string in the form of list of strings
     * @param tempo The list of strings that contains the first brackets
     */
    public static void bodmasInitializer(List<String> inputList, List<String> tempo){
        System.out.println(tempo); // Debugging output
        // creating new lists for prioritizing BODMAS rule

        List<Integer> divide = new ArrayList<>();
        List<Integer> multiply = new ArrayList<>();
        List<Integer> addition = new ArrayList<>();
        List<Integer> subtraction = new ArrayList<>();
        List<Integer> exponent = new ArrayList<>();

        for (int i = 0; i < tempo.size(); i++) {
            String term = tempo.get(i);
            if (Objects.equals(term, "/")) {
                System.out.println("op-" + i); // Debugging output
                divide.add(i);
            }if(Objects.equals(term, "*")){
                System.out.println("op-" + i); // Debugging output
                multiply.add(i);
            }if(Objects.equals(term, "+")){
                System.out.println("op-" + i); // Debugging output
                addition.add(i);
            }if(Objects.equals(term, "-")) {
                System.out.println("op-" + i); // Debugging output
                subtraction.add(i);
            }if(Objects.equals(term, "^")) {
                System.out.println("op-" + i); // Debugging output
                exponent.add(i);
            }
        }
        System.out.println("divide-" + divide); // Debugging output
        System.out.println("multiply-" + multiply); // Debugging output
        System.out.println("addition-" + addition); // Debugging output
        System.out.println("subtraction-" + subtraction); // Debugging output
        System.out.println("exponent-" + exponent); // Debugging output

        calculator(inputList, divide, multiply, addition, subtraction, exponent, tempo);

    }
    /**
     * Calculates the permutaion of the input number.
     * @param input The input string
     * @return permuted
     */
    public static double permuter(String input){
        int indexOf_p = input.indexOf('p');
        double permuted;

        double max_of_p = Integer.parseInt(String.valueOf(input.substring(0, indexOf_p)));
        double min_of_p = Integer.parseInt(String.valueOf(input.substring(indexOf_p + 1)));
        permuted = Permutation.permut(max_of_p, min_of_p);

        return permuted;
    }
    /**
     * Calculates the combination of the input number
     * @param input The input string
     * @return combined
     */
    public static double combiner(String input){
        int indexOf_c = input.indexOf('c');
        double combined;

        double max_of_c = Integer.parseInt(String.valueOf(input.substring(0, indexOf_c)));
        double min_of_c = Integer.parseInt(String.valueOf(input.substring(indexOf_c + 1)));
        combined = Combination.comb(max_of_c, min_of_c);

        return combined;
    }
    /**
     * Performs the operation on the input numbers,
     * By calling the permuter, combiner and factorial functions,
     * And performing the operations at instant.
     * @param op The operator in String
     * @param left The left value in String
     * @param right The right value in String
     * @return Answer
     */
    public static String operations(String op, String left, String right){
        double ans = 0; 

        // Determine if left or right contains '!'
        boolean fleft = left.contains("!"); 
        boolean fright = right.contains("!");

        // Determine if left contains 'p' or 'c' 
        boolean pLeft = left.contains("p"); 
        boolean cLeft = left.contains("c"); 

        // Determine if right contains 'p' or 'c' 
        boolean pRight = right.contains("p"); 
        boolean cRight = right.contains("c");
        
        // Determine if left or right contains 'e'
        boolean eLeft = left.contains("e");
        boolean eRight = right.contains("e");

        // Determine if left or right contains 'l'
        // boolean lLeft = left.contains("l");
        // boolean rLeft = right.contains("l");

        double IntOfLeft = 0; 
        double IntOfRight = 0; 

        // for left value 
        if (pLeft) { 
            IntOfLeft = permuter(left); 
            System.out.println("lp-" + IntOfLeft); // Debugging Output
        } 
        else if (cLeft) { 
            IntOfLeft = combiner(left); 
            System.out.println("lc-" + IntOfLeft); // Debugging Output
        }
        else if (fleft) {
            IntOfLeft = Double.parseDouble(left.substring(0, left.length()-1));
            System.out.println("lf-" + IntOfLeft); // Debugging Output
            IntOfLeft = Factorial.factoria(IntOfLeft);
            System.out.println("clf-" + IntOfLeft); // Debugging Output
        }
        else if (eLeft){
            IntOfLeft = Math.E;
            System.out.println("le" + IntOfLeft);
        }else { 
            IntOfLeft = Double.parseDouble(left); 
            System.out.println("ln-" + IntOfLeft); // Debugging Output
        }  

        // for right value 
        if (pRight) { 
            IntOfRight = permuter(right); 
            System.out.println("rp-" + IntOfRight); // Debugging Output
        } 
        else if (cRight) { 
            IntOfRight = combiner(right); 
            System.out.println("rc-" + IntOfRight); // Debugging Output
        }
        else if (fright) {
            IntOfRight = Double.parseDouble(left.substring(0, left.length()-1));
            System.out.println("rf-" + IntOfRight); // Debugging Output
            IntOfRight = Factorial.factoria(IntOfRight);
            System.out.println("crf-" + IntOfRight); // Debugging Output
        }
        else if (eRight){
            IntOfLeft = Math.E;
            System.out.println("re" + IntOfRight);
        }
        else { 
            IntOfRight = Double.parseDouble(right);
            System.out.println("rn-" + IntOfRight); // Debugging Output
        } 

        // Perform the operation 
        switch (op) { 
            case "+" -> ans = IntOfLeft + IntOfRight;
            case "-" -> ans = IntOfLeft - IntOfRight;
            case "*" -> ans = IntOfLeft * IntOfRight;
            case "/" -> ans = IntOfLeft / IntOfRight;
            case "^" -> ans = (double)Math.pow(IntOfLeft, IntOfRight);
            default -> throw new IllegalArgumentException("Invalid operation: " + op);
        } 
        String finalAns = String.valueOf(ans);
        return finalAns;
    }
    /**
     * Swaps the leftValue, operator and rightValue with the answer,
     * If the tempo list contains the braces then it removes the braces and replaces the answer,
     * And repeats the process until the inputList has only one element by calling the indicissor method.
     * At last we might encounter the situation where the inputList has only one element then we print the final answer.
     * @param inputList The input string in the form of list of strings
     * @param tempo The list of strings that contains the first brackets 
     * @param leftIndex Left index of the inputList
     * @param rightIndex Right index of the inputList
     * @param ans The answer of the operation
     */ 
    public static void replaceElements(List<String> inputList, List<String> tempo, int leftIndex, int rightIndex, String ans) {
        int li = 0;
        int ri = 0;
        int braceleft = 0;
        int braceright = 0;
        List<String> listToBeSeeked = tempo.subList(leftIndex, rightIndex+1);
        System.out.println("inputList- " + inputList); // Debugging output
        System.out.println("listToBeSeeked- " + listToBeSeeked); // Debugging output

        int index = Collections.indexOfSubList(inputList, listToBeSeeked);
        boolean found = false;

        if(index != -1){
            found = true;
            System.out.println("the tempo is in the list at index: " + index);
            li = index;
            System.out.println("li- "+ li);
            
        }else{
            System.out.println("the tempo doesn't found in the inputList");
        }

        if(found){
            String leftElement = inputList.get(li);
            String rightElement = "";
            String leftBrace = "";
            String rightBrace = "";
            if(li+3<inputList.size()){ // to avoid index out of bound exception
                rightBrace = inputList.get(li+3);
            }
            if(li+2<inputList.size()){ // to avoid index out of bound exception
                rightElement = inputList.get(li+2);
            }
            if(li>0){ // to avoid index out of bound exception
                leftBrace = inputList.get(li-1);
            }
            
            

            if(leftElement.equals(tempo.get(leftIndex)) && rightElement.equals(tempo.get(rightIndex))){
                System.out.println("li- " + li); // Debugging output
                System.out.println("lE- " + leftElement); // Debugging output
                System.out.println("rE- " + rightElement); // Debugging output
                // li = li;
                ri = li+2;

                if (index-2>0 && 
                    (inputList.get(index-2).equals("ln") ||  
                    inputList.get(index-2).startsWith("log"))
                    ){

                    System.out.println("Entered into log calibration: success");
                    System.out.println("list to be removed- " + inputList.subList(li, ri+1)); // Debugging output
                    inputList.subList(li, ri+1).clear();

                    System.out.println("removed- " + inputList); // Debugging output
    
                    inputList.add(li, ans.trim());
                    System.out.println("updated- " + inputList);

                    String reInput = String.join("", inputList);
                    System.out.println("reIN: " + reInput);

                    indicissor(sortInput(reInput));

                }
                if(leftBrace.equals("(") && rightBrace.equals(")")){
                    braceleft = li-1;
                    braceright = li+3;
                    System.out.println("bl- " + braceleft); // Debugging output
                    System.out.println("br- " + braceright); // Debugging output
                    System.out.println("list to be removed- " + inputList.subList(braceleft, braceright+1)); // Debugging output
                    li = li-1;

                    inputList.subList(braceleft, braceright+1).clear();
                }
                else{
                    System.out.println("list to be removed- " + inputList.subList(li, ri+1)); // Debugging output
                    inputList.subList(li, ri+1).clear();
                }

                System.out.println("removed- " + inputList); // Debugging output
    
                inputList.add(li, ans.trim());
                System.out.println("updated- " + inputList); // Debugging output

                // System.exit(0); // Debugging output

                if(inputList.size() == 1){
                    System.out.println("final answer: " + inputList.get(0)); // Debugging output
                    System.exit(0);
                }
                if(inputList.size() == 2){
                    inputList.removeLast();
                    System.out.println("final answer: " + inputList.get(0)); // Debugging output
                    System.exit(0);
                }
                else{
                    indicissor(inputList);
                }
            } 
            
        }

        // && (!inputList.get(index-2).equals("ln") && !inputList.get(index-2).startsWith("log"))
        
    }
    /**
     * Calculates the input string by prioritizing the operators,
     * Extract out the left and right values and call the operations method,
     * If there is any exception then it catches the exception and prints the error message.
     * @param inputList The input string in the form of list of strings
     * @param divide List of divide operators
     * @param multiply List of multiply operators
     * @param addition List of addition operators
     * @param subtraction List of subtraction operators 
     * @param tempo The list of strings that contains the first brackets
     */  
    public static void calculator(List <String> inputList,
                            List<Integer> divide,
                            List<Integer> multiply,
                            List<Integer> addition,
                            List<Integer> subtraction,
                            List<Integer> exponent,
                            List<String> tempo){
        
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i<divide.size(); i++) {
            try {
                String left = tempo.get(divide.get(i)-1);
                String right = tempo.get(divide.get(i)+1);

                int leftIndex = tempo.indexOf(left);
                int rightIndex = tempo.indexOf(right);
    
                System.out.println("l-" + left); // Debugging output
                System.out.println("r-" + right); // Debugging output

                if (right.equals("0")){
                    System.out.println("Error: Division by zero is not allowed.");
                    System.out.print("Do you want to still continue with the operation? (yes/no) -->");
                    String userPermit = sc.nextLine();
                    if (userPermit.equalsIgnoreCase("yes") || userPermit.equalsIgnoreCase("y")){
                    }
                    else{
                        System.out.println("Operation aborted by the user.");
                        sc.close();
                        System.exit(0);
                        
                    }
                    
                }
    
                String output = operations("/", left, right);
                System.out.println("d- " + output); // Debugging Output

                // if(output.equals("Infinity") || output.equals("-Infinity")){
                //     System.out.println("Error: Division by zero is not allowed.");
                //     System.out.print("Do you want to still continue with the operation? (yes/no) -->");
                //     String userPermit = sc.nextLine();
                //     if (userPermit.equalsIgnoreCase("yes") || userPermit.equalsIgnoreCase("y")){
                        
                //     }
                //     else{
                //         System.out.println("Operation aborted by the user.");
                //         System.exit(0);
                //     }
                // }
    
                System.out.println("tempo in d"+ tempo); // Debugging Output
                replaceElements(inputList, tempo, leftIndex, rightIndex, output);
     
                break;

            } catch (Exception e) {
                System.out.println("some error occurred: " + e.getMessage());
            }

            
        }for (int i = 0; i<exponent.size(); i++) {
            try {
                String left = tempo.get(exponent.get(i)-1);
                String right = tempo.get(exponent.get(i)+1);

                int rightIndex = tempo.indexOf(right);
                int leftIndex = tempo.indexOf(left);

                System.out.println("r-" + right); // Debugging output
                System.out.println("l-" + left); // Debugging output
        
                String output = operations("^", left, right);
                System.out.println("s- " +  output); // Debugging Output
                
                replaceElements(inputList, tempo, leftIndex, rightIndex, output);
                System.out.println("tempo in s"+ tempo); // Debugging Output

                break;

            } catch (Exception e) {
                System.out.println("some error occurred: " + e.getMessage());
            }
            
        }for (int i = 0; i<multiply.size(); i++) {
            try {
                String left = tempo.get(multiply.get(i)-1);
                String right = tempo.get(multiply.get(i)+1);
    
                int leftIndex = tempo.indexOf(left);
                int rightIndex = tempo.indexOf(right);
    
                System.out.println("l-" + left); // Debugging output
                System.out.println("r-" + right); // Debugging output
    
                String output = operations("*", left, right);
                System.out.println("m- " + output); // Debugging Output
    
                System.out.println("tempo in m"+ tempo); // Debugging Output
                replaceElements(inputList, tempo, leftIndex, rightIndex, output); 

                break;

            }
             catch (Exception e) {
                System.out.println("some error occurred: " + e.getMessage());
                // System.out.println("some error occurred: " + e.getLocalizedMessage());
            }
            


        }for (int i = 0; i<addition.size(); i++) {
            try {
                String left = tempo.get(addition.get(i)-1);
                String right = tempo.get(addition.get(i)+1);

                int leftIndex = tempo.indexOf(left);
                int rightIndex = tempo.indexOf(right);

                System.out.println("l-" + left); // Debugging output
                System.out.println("r-" + right); // Debugging output
            

                String output = operations("+", left, right);
                System.out.println("a- "+ output); // Debugging Output

                System.out.println("tempo in a"+ tempo); // Debugging Output
                replaceElements(inputList, tempo, leftIndex, rightIndex, output);

                break;

            } catch (Exception e) {
                System.out.println("some error occurred: " + e.getMessage());
            }
            


        }for (int i = 0; i<subtraction.size(); i++) {
            try {
                String left = tempo.get(subtraction.get(i)-1);
                String right = tempo.get(subtraction.get(i)+1);

                int rightIndex = tempo.indexOf(right);
                int leftIndex = tempo.indexOf(left);

                System.out.println("r-" + right); // Debugging output
                System.out.println("l-" + left); // Debugging output
        
                String output = operations("-", left, right);
                System.out.println("s- " +  output); // Debugging Output
                
                replaceElements(inputList, tempo, leftIndex, rightIndex, output);
                System.out.println("tempo in s"+ tempo); // Debugging Output

                break;

            } catch (Exception e) {
                System.out.println("some error occurred: " + e.getMessage());
            }
            
        }
    }
    
    /**
     * The main function of the program,
     * It takes the input from the user and calls the sortInput method,
     * Stores the input in the inputList and calls the indicissor method,
     * Interfrace with the user to take the input.
     * @param args The command line arguments
     */
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        // String DebuggingInput = "7*89-65+66 -9p8 / 7c2 *47+89+56 *(7p4 * 6c3 -90/5 +45*(7*8p4))-90"; // Debugging input

        System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║              Welcome to Multi-term Functional Calculator                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("Instructions:");
        System.out.println("  1. Ensure that the input has valid operators (+, -, *, /, ^) ");
        System.out.println("  2. Operators must be placed beside parentheses to avoid errors");
        System.out.println("  3. The braces are needed to only parentheses '(',')'");
        System.out.println("  4. 'p' denotes Permutation (e.g., 7p4 = P(7,4))");
        System.out.println("  5. 'c' denotes Combination (e.g., 7c2 = C(7,2))");
        System.out.println("  6. '!' denotes Factorial (e.g., 5! = 120)");
        System.out.println("  7. 'ln' denotes Log with base 'e' (e.g., ln(2) = 0.693)");
        System.out.println("  8. 'log' denotes Log with base '10' (e.g., log(2) = 0.301)");
        System.out.println("  9. 'log2' denotes Log with base '2' (e.g., ln2(2) = 1)\n");

        
        System.out.println("Example Input:");
        System.out.println("  7*89-65+66-9p8/7c2*47+56*(7p4*6c3-90/5+45*(7*8p4))-90+ 3^4 -e^2\n");
        
        System.out.print("═══════════════════════════════════════════════════════════════════════════════════\n");
        System.out.print("Enter your mathematical expression here:\n");
        System.out.print("> ");
        
        String DebuggingInput = sc.nextLine();
        String input = DebuggingInput.replaceAll("\\s+", "");

        ArrayList<String> inputList = sortInput(input);

        System.out.println("input conv "+ inputList);
        indicissor(inputList);
        
        // Example input:
        //               7*89-65+66 -9p8 / 7c2 *47+89+56 *(7p4 * 6c3 -90/5 +45*(7*8p4))-90+ 3^4 

        sc.close();
    }
}
