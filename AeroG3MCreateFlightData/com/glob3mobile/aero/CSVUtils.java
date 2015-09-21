

package com.glob3mobile.aero;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collection;


public class CSVUtils {


   /**
    * Prints a collection of rows to the standard output
    *
    * @param rows
    *           A collection of rows to be printed.
    */
   public void printAndValidate(final Collection<?> rows) {
      printAndValidate(null, rows);
   }


   /**
    * Prints a collection of rows to the standard output, with headings
    *
    * @param headers
    *           the description of each
    * @param rows
    *           the rows to print then validate
    */
   public void printAndValidate(final Object[] headers,
                                final Collection<?> rows) {

      if (headers != null) {
         System.out.println(Arrays.toString(headers));
         System.out.println("=======================");
      }

      int rowCount = 1;
      for (final Object row : rows) {
         System.out.println((rowCount++) + " " + Arrays.toString((Object[]) row));
         System.out.println("-----------------------");
      }
   }


   public Reader getReader(final String path) throws FileNotFoundException {
      try {


         return new InputStreamReader(new FileInputStream(path), "UTF-8");
      }
      catch (final UnsupportedEncodingException e) {
         throw new IllegalStateException("Unable to read input", e);
      }
   }

}
