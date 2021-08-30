package com.example.reactivefunctional.functional;

import java.util.function.BiFunction;
import java.util.function.Function;

@FunctionalInterface
interface Converter {
   double convert(double input, int input2);
}

class ConverterC {

   public static void main(String[] args) {
      //Convert Fahrenheit to Celsius

      System.out.println(convert((input, value) -> ++input + value, 1));

      Function<Integer, String> c2 = x -> String.valueOf(x + 1);

      String p = c2.apply(10);
      System.out.println(p);

      BiFunction<Integer, String, Double> bif = (a, b) -> a + Double.parseDouble(b);
      System.out.println(bif.apply(10, "4"));
   }

   static double convert(final Converter c, final double input) {
      return c.convert(input, (int) input);
   }
}