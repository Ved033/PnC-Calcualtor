// Source code is decompiled from a .class file using FernFlower decompiler (from Intellij IDEA).
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class sorter {
   public sorter() {
   }

   public static ArrayList<String> sortInput(String var0) {
      int var1 = var0.length();
      int var2 = -1;
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < var1; ++var4) {
         char var5 = var0.charAt(var4);
         if (var5 == '+' || var5 == '-' || var5 == '/' || var5 == '*' || var5 == '(' || var5 == ')' || var5 == '^') {
            if (var2 + 1 < var4) {
               var3.add(var0.substring(var2 + 1, var4));
            }

            var3.add(Character.toString(var5));
            var2 = var4;
         }
      }

      var3.add(var0.substring(var2 + 1, var1));
      return var3;
   }

   public static void indicissor(List<String> var0) {
      int var1 = var0.size();
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();

      for(int var4 = 0; var4 < var0.size(); ++var4) {
         if (((String)var0.get(var4)).equals("(")) {
            var2.add(var4);
         } else if (((String)var0.get(var4)).equals(")")) {
            var3.add(var4);
         }
      }

      if (var0.size() == 1) {
         String var5 = operations("+", (String)var0.get(0), "0");
         System.out.println("final answer: " + var5);
         System.exit(0);
      }

      Collections.reverse(var2);
      var2.add(-1);
      var3.add(var1);
      invoker(var2, var3, var0);
   }

   public static void invoker(List<Integer> var0, List<Integer> var1, List<String> var2) {
      byte var4 = 0;
      if (var4 < var0.size()) {
         int var5 = (Integer)var0.get(var4);
         int var6 = (Integer)var1.get(var4);
         List var3 = var2.subList(var5 + 1, var6);
         System.out.println("i: " + String.valueOf(var3));
         System.out.println("fI " + var5);
         System.out.println("cI " + var6);
         bodmasInitializer(var2, var3);
      }

   }

   public static void bodmasInitializer(List<String> var0, List<String> var1) {
      System.out.println(var1);
      ArrayList var2 = new ArrayList();
      ArrayList var3 = new ArrayList();
      ArrayList var4 = new ArrayList();
      ArrayList var5 = new ArrayList();
      ArrayList var6 = new ArrayList();

      for(int var7 = 0; var7 < var1.size(); ++var7) {
         String var8 = (String)var1.get(var7);
         if (Objects.equals(var8, "/")) {
            System.out.println("op-" + var7);
            var2.add(var7);
         }

         if (Objects.equals(var8, "*")) {
            System.out.println("op-" + var7);
            var3.add(var7);
         }

         if (Objects.equals(var8, "+")) {
            System.out.println("op-" + var7);
            var4.add(var7);
         }

         if (Objects.equals(var8, "-")) {
            System.out.println("op-" + var7);
            var5.add(var7);
         }

         if (Objects.equals(var8, "^")) {
            System.out.println("op-" + var7);
            var6.add(var7);
         }
      }

      System.out.println("divide-" + String.valueOf(var2));
      System.out.println("multiply-" + String.valueOf(var3));
      System.out.println("addition-" + String.valueOf(var4));
      System.out.println("subtraction-" + String.valueOf(var5));
      System.out.println("exponent-" + String.valueOf(var6));
      calculator(var0, var2, var3, var4, var5, var6, var1);
   }

   public static double permuter(String var0) {
      int var1 = var0.indexOf(112);
      double var4 = (double)Integer.parseInt(String.valueOf(var0.substring(0, var1)));
      double var6 = (double)Integer.parseInt(String.valueOf(var0.substring(var1 + 1)));
      double var2 = Permutation.permut(var4, var6);
      return var2;
   }

   public static double combiner(String var0) {
      int var1 = var0.indexOf(99);
      double var4 = (double)Integer.parseInt(String.valueOf(var0.substring(0, var1)));
      double var6 = (double)Integer.parseInt(String.valueOf(var0.substring(var1 + 1)));
      double var2 = Combination.comb(var4, var6);
      return var2;
   }

   public static String operations(String var0, String var1, String var2) {
      double var3 = (double)0.0F;
      boolean var5 = var1.contains("!");
      boolean var6 = var2.contains("!");
      boolean var7 = var1.contains("p");
      boolean var8 = var1.contains("c");
      boolean var9 = var2.contains("p");
      boolean var10 = var2.contains("c");
      boolean var11 = var1.contains("e");
      boolean var12 = var2.contains("e");
      double var13 = (double)0.0F;
      double var15 = (double)0.0F;
      if (var7) {
         var13 = permuter(var1);
         System.out.println("lp-" + var13);
      } else if (var8) {
         var13 = combiner(var1);
         System.out.println("lc-" + var13);
      } else if (var5) {
         double var21 = Double.parseDouble(var1.substring(0, var1.length() - 1));
         System.out.println("lf-" + var21);
         var13 = Factorial.factoria(var21);
         System.out.println("clf-" + var13);
      } else if (var11) {
         var13 = Math.E;
         System.out.println("le" + var13);
      } else {
         var13 = Double.parseDouble(var1);
         System.out.println("ln-" + var13);
      }

      if (var9) {
         var15 = permuter(var2);
         System.out.println("rp-" + var15);
      } else if (var10) {
         var15 = combiner(var2);
         System.out.println("rc-" + var15);
      } else if (var6) {
         double var22 = Double.parseDouble(var1.substring(0, var1.length() - 1));
         System.out.println("rf-" + var22);
         var15 = Factorial.factoria(var22);
         System.out.println("crf-" + var15);
      } else if (var12) {
         var13 = Math.E;
         System.out.println("re" + var15);
      } else {
         var15 = Double.parseDouble(var2);
         System.out.println("rn-" + var15);
      }

      switch (var0) {
         case "+" -> var3 = var13 + var15;
         case "-" -> var3 = var13 - var15;
         case "*" -> var3 = var13 * var15;
         case "/" -> var3 = var13 / var15;
         case "^" -> var3 = Math.pow(var13, var15);
         default -> throw new IllegalArgumentException("Invalid operation: " + var0);
      }

      String var17 = String.valueOf(var3);
      return var17;
   }

   public static void replaceElements(List<String> var0, List<String> var1, int var2, int var3, String var4) {
      int var5 = 0;
      int var6 = 0;
      int var7 = 0;
      int var8 = 0;
      List var9 = var1.subList(var2, var3 + 1);
      System.out.println("inputList- " + String.valueOf(var0));
      System.out.println("listToBeSeeked- " + String.valueOf(var9));
      int var10 = Collections.indexOfSubList(var0, var9);
      boolean var11 = false;
      if (var10 != -1) {
         var11 = true;
         System.out.println("the tempo is int the list at index: " + var10);
         var5 = var10;
         System.out.println("li- " + var10);
      } else {
         System.out.println("the tempo doesn't found in the inputList");
      }

      if (var11) {
         String var12 = (String)var0.get(var5);
         String var13 = "";
         String var14 = "";
         String var15 = "";
         if (var5 + 3 < var0.size()) {
            var15 = (String)var0.get(var5 + 3);
         }

         if (var5 + 2 < var0.size()) {
            var13 = (String)var0.get(var5 + 2);
         }

         if (var5 > 0) {
            var14 = (String)var0.get(var5 - 1);
         }

         if (var12.equals(var1.get(var2)) && var13.equals(var1.get(var3))) {
            System.out.println("li- " + var5);
            System.out.println("lE- " + var12);
            System.out.println("rE- " + var13);
            int var16 = var5;
            var6 = var5 + 2;
            if (var14.equals("(") && var15.equals(")")) {
               var7 = var5 - 1;
               var8 = var5 + 3;
               System.out.println("bl- " + var7);
               System.out.println("br- " + var8);
               PrintStream var20 = System.out;
               List var21 = var0.subList(var7, var8 + 1);
               var20.println("list to be removed- " + String.valueOf(var21));
               --var16;
               var0.subList(var7, var8 + 1).clear();
            } else {
               PrintStream var10000 = System.out;
               List var10001 = var0.subList(var5, var6 + 1);
               var10000.println("list to be removed- " + String.valueOf(var10001));
               var0.subList(var5, var6 + 1).clear();
            }

            System.out.println("removed- " + String.valueOf(var0));
            var0.add(var16, var4.trim());
            System.out.println("updated- " + String.valueOf(var0));
            if (var0.size() == 1) {
               System.out.println("final answer: " + (String)var0.get(0));
               System.exit(0);
            }

            if (var0.size() == 2) {
               var0.removeLast();
               System.out.println("final answer: " + (String)var0.get(0));
               System.exit(0);
            } else {
               indicissor(var0);
            }
         }
      }

   }

   public static void calculator(List<String> var0, List<Integer> var1, List<Integer> var2, List<Integer> var3, List<Integer> var4, List<Integer> var5, List<String> var6) {
      Scanner var7 = new Scanner(System.in);

      for(int var8 = 0; var8 < var1.size(); ++var8) {
         try {
            String var9 = (String)var6.get((Integer)var1.get(var8) - 1);
            String var10 = (String)var6.get((Integer)var1.get(var8) + 1);
            int var11 = var6.indexOf(var9);
            int var12 = var6.indexOf(var10);
            System.out.println("l-" + var9);
            System.out.println("r-" + var10);
            if (var10.equals("0")) {
               System.out.println("Error: Division by zero is not allowed.");
               System.out.print("Do you want to still continue with the operation? (yes/no) -->");
               String var13 = var7.nextLine();
               if (!var13.equalsIgnoreCase("yes") && !var13.equalsIgnoreCase("y")) {
                  System.out.println("Operation aborted by the user.");
                  System.exit(0);
               }
            }

            String var39 = operations("/", var9, var10);
            System.out.println("d- " + var39);
            System.out.println("tempo in d" + String.valueOf(var6));
            replaceElements(var0, var6, var11, var12, var39);
            break;
         } catch (Exception var18) {
            System.out.println("some error occurred: " + var18.getMessage());
         }
      }

      for(int var19 = 0; var19 < var5.size(); ++var19) {
         try {
            String var23 = (String)var6.get((Integer)var5.get(var19) - 1);
            String var27 = (String)var6.get((Integer)var5.get(var19) + 1);
            int var31 = var6.indexOf(var27);
            int var35 = var6.indexOf(var23);
            System.out.println("r-" + var27);
            System.out.println("l-" + var23);
            String var40 = operations("^", var23, var27);
            System.out.println("s- " + var40);
            replaceElements(var0, var6, var35, var31, var40);
            System.out.println("tempo in s" + String.valueOf(var6));
            break;
         } catch (Exception var17) {
            System.out.println("some error occurred: " + var17.getMessage());
         }
      }

      for(int var20 = 0; var20 < var2.size(); ++var20) {
         try {
            String var24 = (String)var6.get((Integer)var2.get(var20) - 1);
            String var28 = (String)var6.get((Integer)var2.get(var20) + 1);
            int var32 = var6.indexOf(var24);
            int var36 = var6.indexOf(var28);
            System.out.println("l-" + var24);
            System.out.println("r-" + var28);
            String var41 = operations("*", var24, var28);
            System.out.println("m- " + var41);
            System.out.println("tempo in m" + String.valueOf(var6));
            replaceElements(var0, var6, var32, var36, var41);
            break;
         } catch (Exception var16) {
            System.out.println("some error occurred: " + var16.getMessage());
         }
      }

      for(int var21 = 0; var21 < var3.size(); ++var21) {
         try {
            String var25 = (String)var6.get((Integer)var3.get(var21) - 1);
            String var29 = (String)var6.get((Integer)var3.get(var21) + 1);
            int var33 = var6.indexOf(var25);
            int var37 = var6.indexOf(var29);
            System.out.println("l-" + var25);
            System.out.println("r-" + var29);
            String var42 = operations("+", var25, var29);
            System.out.println("a- " + var42);
            System.out.println("tempo in a" + String.valueOf(var6));
            replaceElements(var0, var6, var33, var37, var42);
            break;
         } catch (Exception var15) {
            System.out.println("some error occurred: " + var15.getMessage());
         }
      }

      for(int var22 = 0; var22 < var4.size(); ++var22) {
         try {
            String var26 = (String)var6.get((Integer)var4.get(var22) - 1);
            String var30 = (String)var6.get((Integer)var4.get(var22) + 1);
            int var34 = var6.indexOf(var30);
            int var38 = var6.indexOf(var26);
            System.out.println("r-" + var30);
            System.out.println("l-" + var26);
            String var43 = operations("-", var26, var30);
            System.out.println("s- " + var43);
            replaceElements(var0, var6, var38, var34, var43);
            System.out.println("tempo in s" + String.valueOf(var6));
            break;
         } catch (Exception var14) {
            System.out.println("some error occurred: " + var14.getMessage());
         }
      }

   }

   public static void main(String[] var0) {
      Scanner var1 = new Scanner(System.in);
      System.out.println("╔════════════════════════════════════════════════════════════════════════════════╗");
      System.out.println("║              Welcome to Multi-term Functional Calculator                       ║");
      System.out.println("╚════════════════════════════════════════════════════════════════════════════════╝\n");
      System.out.println("Instructions:");
      System.out.println("  1. Ensure that the input has valid operators (+, -, *, /, ^) ");
      System.out.println("  2. Operators must be placed beside parentheses to avoid errors");
      System.out.println("  3. The braces are needed to only parentheses '(',')'");
      System.out.println("  4. 'p' denotes Permutation (e.g., 7p4 = P(7,4))");
      System.out.println("  5. 'c' denotes Combination (e.g., 7c2 = C(7,2))");
      System.out.println("  6. '!' denotes Factorial (e.g., 5! = 120)\n");
      System.out.println("Example Input:");
      System.out.println("  7*89-65+66-9p8/7c2*47+56*(7p4*6c3-90/5+45*(7*8p4))-90+ 3^4 -e^2\n");
      System.out.print("═══════════════════════════════════════════════════════════════════════════════════\n");
      System.out.print("Enter your mathematical expression here:\n");
      System.out.print("> ");
      String var2 = var1.nextLine();
      String var3 = var2.replaceAll("\\s+", "");
      ArrayList var4 = sortInput(var3);
      System.out.println("input conv " + String.valueOf(var4));
      indicissor(var4);
      var1.close();
   }
}
