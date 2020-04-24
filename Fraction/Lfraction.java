import java.util.*;

/** This class represents fractions of form n/d where n and d are long integer 
 * numbers. Basic operations and arithmetics for fractions are provided.
 */
public class Lfraction implements Comparable<Lfraction> {

   /** Main method. Different tests. */
   public static void main (String[] param) {
      System.out.println(Lfraction.valueOf ("2/x"));
   }

   private long numerator;
   private long denominator;

   /** Constructor.
    * @param a numerator
    * @param b denominator > 0
    */
   public Lfraction (long a, long b) {
      if (b == 0) {
         throw new RuntimeException("The denominator can't be zero.");
      } else if (b < 0) {
         this.numerator = -1 * a;
         this.denominator = -1 * b;
      } else if (a == 0) {
         this.denominator = 1;
      } else {
         this.numerator = a;
         this.denominator = b;
      }
      reduceFraction();
   }

   /** Public method to access the numerator field.
    * @return numerator
    */
   public long getNumerator() {
      return numerator;
   }

   /** Public method to access the denominator field.
    * @return denominator
    */
   public long getDenominator() {
      return denominator;
   }

   /** Conversion to string.
    * @return string representation of the fraction
    */
   @Override
   public String toString() {
      if (denominator == 1) {
         return numerator + "";
      } else {
         return numerator + "/" + denominator;
      }
   }

   /** Equality test.
    * @param m second fraction
    * @return true if fractions this and m are equal
    */
   @Override
   public boolean equals (Object m) {
      if (!(m instanceof Lfraction)) {
         return false;
      }
      return compareTo((Lfraction) m) == 0;
   }

   /** Hashcode has to be equal for equal fractions.
    * @return hashcode
    */
   @Override
   public int hashCode() {
      return Objects.hash(numerator, denominator);
   }

   /** Sum of fractions.
    * @param m second addend
    * @return this+m
    */
   public Lfraction plus (Lfraction m) {
      long newNumerator = numerator * m.denominator + m.numerator * denominator;
      long newDenominator = denominator * m.denominator;
      return new Lfraction(newNumerator, newDenominator);
   }

   /** Multiplication of fractions.
    * @param m second factor
    * @return this*m
    */
   public Lfraction times (Lfraction m) {
      long newNumerator = numerator * m.numerator;
      long newDenominator = denominator * m.denominator;
      return new Lfraction(newNumerator, newDenominator);
   }

   /** Reducing of a fraction.*/

   public void reduceFraction () {
      int greatestCommonDivisor = 1;

      for (int i = 1; i <= Math.abs(numerator) && i <= denominator; i++) {
         if (numerator % i == 0 && denominator % i == 0) {
            greatestCommonDivisor = i;
         }
      }
      numerator = numerator / greatestCommonDivisor;
      denominator = denominator / greatestCommonDivisor;
   }

   /** Inverse of the fraction. n/d becomes d/n.
    * @return inverse of this fraction: 1/this
    */
   public Lfraction inverse() {
      if (numerator == 0) {
         throw new RuntimeException("When finding the inverse of a fraction, the numerator can't be 0.");
      }
      long newNumerator = denominator;
      long newDenominator = numerator;
      return new Lfraction(newNumerator, newDenominator);
   }

   /** Opposite of the fraction. n/d becomes -n/d.
    * @return opposite of this fraction: -this
    */
   public Lfraction opposite() {
      long newNumerator = -1 * numerator;
      return new Lfraction(newNumerator, denominator);
   }

   /** Difference of fractions.
    * @param m subtrahend
    * @return this-m
    */
   public Lfraction minus (Lfraction m) {
      return plus(m.opposite());
   }

   /** Quotient of fractions.
    * @param m divisor
    * @return this/m
    */
   public Lfraction divideBy (Lfraction m) {
      if (m.numerator == 0) {
         throw new RuntimeException("When dividing a fraction, the numerator of a divisor can't be 0.");
      }
      return times(m.inverse());
   }

   /** Comparision of fractions.
    * @param m second fraction
    * @return -1 if this < m; 0 if this==m; 1 if this > m
    */
   @Override
   public int compareTo (Lfraction m) {
      if (minus(m).numerator < 0) {
         return -1;
      } else if (minus(m).numerator > 0) {
         return 1;
      } else {
         return 0;
      }
   }

   /** Clone of the fraction.
    * @return new fraction equal to this
    */
   @Override
   public Object clone() throws CloneNotSupportedException {
      Lfraction clonedFraction = new Lfraction(numerator, denominator);
      return clonedFraction;
   }

   /** Integer part of the (improper) fraction.
    * @return integer part of this fraction
    */
   public long integerPart() {
      return numerator / denominator;
   }

   /** Extract fraction part of the (improper) fraction
    * (a proper fraction without the integer part).
    * @return fraction part of this fraction
    */
   public Lfraction fractionPart() {
      long newNumerator = numerator - (integerPart() * denominator);
      return new Lfraction(newNumerator, denominator);
   }

   /** Approximate value of the fraction.
    * @return numeric value of this fraction
    */
   public double toDouble() {
      double value = (double) numerator / denominator;
      return value;
   }

   /** Double value f presented as a fraction with denominator d > 0.
    * @param f real number
    * @param d positive denominator for the result
    * @return f as an approximate fraction of form n/d
    */
   public static Lfraction toLfraction (double f, long d) {
      long newNumerator = Math.round(f * d);
      return new Lfraction(newNumerator, d);
   }

   /** Conversion from string to the fraction. Accepts strings of form
    * that is defined by the toString method.
    * @param s string form (as produced by toString) of the fraction
    * @return fraction represented by s
    */
   public static Lfraction valueOf (String s) {
      long fractionNumerator;
      long fractionDenominator;
      if (s.length() == 1) {
         try {
            fractionNumerator = Long.parseLong(s);
            fractionDenominator = 1;
         } catch (Exception e) {
            throw new RuntimeException(" '" + s + "' is not a number!");
         }
      } else {
         String[] fractionElements = s.split("/", 2);
         try {
            fractionNumerator = Long.parseLong(fractionElements[0]);
            fractionDenominator = Long.parseLong(fractionElements[1]);
         } catch (Exception e) {
            throw new RuntimeException(" '" + s + "' is not a number!");
         }
      }
      return new Lfraction(fractionNumerator, fractionDenominator);
   }
}