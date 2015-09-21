

package com.glob3mobile.aero;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.glob3mobile.aero.model.Airport;
import com.glob3mobile.aero.model.Constants;
import com.glob3mobile.aero.model.Flight;
import com.glob3mobile.aero.model.Position;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;


public class CSVToMongo {

   static String              _day   = "";
   static String              _month = "";
   static String              _year  = "";
   private static MongoWriter _mw;


   public static void main(final String[] args) throws FileNotFoundException, ParseException, UnknownHostException {


      _mw = new MongoWriter();
      _mw.cleanFlights();
      System.out.println("AeroG3M 0.1");
      System.out.println("Starting the proccess");
      System.out.println("Reading the CSV files");
      final File CSVFolder = new File(Constants.CSV_DATA_FOLDER);

      final File[] csvFiles = CSVFolder.listFiles();


      for (final File csv : csvFiles) {
         if (getExtension(csv.getName()).equals("csv")) {
            System.out.println(csv.getAbsolutePath());
            ParseCSVFile(csv.getAbsolutePath());
         }
      }

      _mw.closeMongo();

   }


   public static void ParseCSVFile(final String filename) throws FileNotFoundException, ParseException, UnknownHostException {


      final CsvParserSettings settings = new CsvParserSettings();
      settings.getFormat().setLineSeparator("\r");

      final String fn = filename.substring(filename.lastIndexOf("/") + 1);
      final String[] fileData = fn.split("#");
      _day = fileData[2];
      _month = fileData[3];
      _year = fileData[4].substring(0, fileData[4].length() - 4);

      final CsvParser parser = new CsvParser(settings);

      final CSVUtils csvUtils = new CSVUtils();

      final List<String[]> allRows = parser.parseAll(csvUtils.getReader(filename));


      final ArrayList<Position> positions = parseRows(allRows);


      final Flight f = new Flight();

      final Airport departureAirport = new Airport();
      departureAirport.setIcaoCode(fileData[0]);
      f.setDepartureAirport(departureAirport);

      final Airport arrivalAirport = new Airport();
      arrivalAirport.setIcaoCode(fileData[1]);
      f.setArrivalAirport(arrivalAirport);
      f.setTimeDeparture(positions.get(0).getTime());
      f.setTimeArrival(positions.get(positions.size() - 1).getTime());
      f.setPositions(positions);
      _mw.addFlight(f);

   }


   private static ArrayList<Position> parseRows(final List<String[]> allRows) throws ParseException {
      final ArrayList<Position> positions = new ArrayList<>();
      for (final String[] positionList : allRows) {
         final String row = positionList[0];
         final String[] columns = row.split(";");
         positions.add(parseColumn(columns));
      }
      return positions;
   }


   private static Position parseColumn(final String[] columns) throws ParseException {
      final Position position = new Position();

      final SimpleDateFormat sdf = new SimpleDateFormat("EEE hh:mm:ss a");

      final Date time = sdf.parse(columns[0]);
      final Calendar calendar = Calendar.getInstance();
      calendar.setTime(time);
      calendar.set(Integer.parseInt(_year), Integer.parseInt(_month), Integer.parseInt(_day));
      position.setTime(calendar.getTime());

      final String latitudeSt = columns[1].replaceAll("\\.", "");
      position.setLatitude((Double.parseDouble(latitudeSt) / 10000));

      final String longitudeSt = columns[2].replaceAll("\\.", "");
      position.setLongitude((Double.parseDouble(longitudeSt) / 10000));

      position.setHeading(Integer.parseInt(columns[3]));

      String speedKnots = columns[5];
      if (speedKnots.equals("")) {
         speedKnots = "0";
      }
      position.setSpeedKnots(Double.parseDouble(speedKnots));
      String speed = columns[6];
      if (speed.equals("")) {
         speed = "0";
      }
      position.setSpeed(Double.parseDouble(speed));

      String altitudeFeet = columns[7].replaceAll("\\.", "");
      if (altitudeFeet.equals("")) {
         altitudeFeet = "0";
      }
      position.setSpeed(Double.parseDouble(speed));
      position.setAltitudeFeets(Integer.parseInt(altitudeFeet));

      String rate = columns[8];
      rate = rate.substring(0, rate.indexOf(" "));
      if (rate.equals("")) {
         rate = "0";
      }
      position.setRateAltitudeChange(Long.parseLong(rate.replaceAll("\\.", "")));

      return position;
   }


   public static String getExtension(final String fileName) {

      String extension = "";

      final int i = fileName.lastIndexOf('.');
      if (i > 0) {
         extension = fileName.substring(i + 1);
      }


      return extension;
   }


}
