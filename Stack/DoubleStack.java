import jdk.swing.interop.SwingInterOpUtils;

import java.math.MathContext;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class DoubleStack {

   private static List<String> operations = Arrays.asList("-", "+", "*", "/", "SWAP", "ROT");

   public static void main (String[] argum) {
       String s = "2 5 9 ROT + SWAP -";
      System.out.println(DoubleStack.interpret (s));
   }
   private LinkedList<Double> stack;

   DoubleStack() {
      stack = new LinkedList<>();
   }

   /**Make a copy of a stack.*/
   @Override
   public Object clone() throws CloneNotSupportedException {
      /*maximal array length */
      DoubleStack clone = new DoubleStack();
      clone.stack.addAll(stack);
      return clone;
   }

   /**Check if stack is empty.*/
   public boolean stEmpty() {
      return stack.size() == 0;
   }


   /**
    * Add an element to stack.
    *
    * @param a
    *           double to be added
    */
   public void push (double a) {
      stack.add(a);
   }

   /**Remove an element from stack.*/
   public double pop() {
      if (this.stEmpty()) {
         throw new IndexOutOfBoundsException("Stack is empty");
      }
      return stack.removeLast();
   }

   /**
    * Arithmetic operation between two topmost elements of the stack
    *
    * @param s
    *           arithmetic operation
    */
   public void op (String s) {
      if (!operations.contains(s)) {
         throw new RuntimeException("Incorrect operator '" + s + "'. Correct operations are '-', '+', '*', '/'");
      } else if (stack.size() < 2) {
         throw new RuntimeException("Arithmetic expression does not have enough numbers for operation '" + s + "'.");
      } else if (s.equals("ROT") && stack.size() < 3) {
         throw new RuntimeException("Arithmetic expression does not have enough numbers for operation '" + s + "'.");
      }
      double last = pop();

      double secondLast = pop();

      double result = 0.0;
      if (s.equals("+")) {
         result = last + secondLast;
         push(result);
      } else if (s.equals("-")) {
         result = secondLast - last;
         push(result);
      } else if (s.equals("/")) {
         result = secondLast / last;
         push(result);
      } else if (s.equals("*")) {
         result = secondLast * last;
         push(result);
      }
      if (s.equals("SWAP")) {
         push(last);
         push(secondLast);
      }
      if (s.equals("ROT")) {
         if (stEmpty()) {
            throw new RuntimeException("Arithmetic expression does not have enough numbers for operation '" + s + "'.");
         }
         double thirdLast = pop();
         push(secondLast);
         push(last);
         push(thirdLast);
      }
   }

   /**Read the top without removing it from stack.*/
   public double tos() {
      if (this.stEmpty()) {
         throw new NoSuchElementException("Stack is empty.");
      }
      return stack.getLast();
   }


   /**
    * Check whether two stacks are equal.
    *
    * @param o
    *           Stack to be compared to
    */
   @Override
   public boolean equals (Object o) {
      DoubleStack newStack = (DoubleStack) o;
      if (newStack.stack.size() != stack.size()) {
         return false;
      }
      for (int i = 0; i < stack.size(); i++) {
         if ((double) stack.get(i) != newStack.stack.get(i)){
            return false;
         }
      }
      return true;
   }

   /**Conversion of the stack to string.*/
   @Override
   public String toString() {
      if (this.stEmpty()) {
         return "Empty stack";
      }
      StringBuilder result = new StringBuilder();
      for (int i=0; i<=stack.size() - 1; i++)
         result.append(stack.get(i)).append(" ");
      return result.toString();
   }


   /**
    * Calculate the value of an arithmetic  expression in reverse polish notation.
    *
    * @param pol
    *           arithmetic expression
    */
   public static double interpret (String pol) {
      DoubleStack myStack = new DoubleStack();
      if (pol == null || pol.length() == 0) {
         throw new RuntimeException("Arithmetic expression does not have any numbers or operators.");
      }
      String[] elements = pol.trim().split("\\s+" );

      for (int i = 0; i < elements.length; i++) {
         String str = elements[i];
         if (operations.contains(str)) {
                myStack.op(str);
         } else {
            try {
               Double.parseDouble(str);
            } catch (RuntimeException e){
               throw new RuntimeException("Arithmetic expression '" + pol + "' has an unknown symbol: " + str);
            }
            if (i != elements.length - 1 || elements.length == 1) {
               myStack.push(Double.parseDouble(str));
            } else {
               throw new RuntimeException("Arithmetic expression '" + pol + "' has too many numbers.");
            }
         }
      }
      return myStack.tos();
   }
}
