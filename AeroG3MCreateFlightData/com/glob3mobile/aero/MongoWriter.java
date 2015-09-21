

package com.glob3mobile.aero;

import java.net.UnknownHostException;

import com.glob3mobile.aero.model.Constants;
import com.glob3mobile.aero.model.Flight;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;


public class MongoWriter {

   private MongoClient _mongoClient;
   private DB          _db;


   public void closeMongo() {
      _mongoClient.close();
   }


   private void initializeMongo() throws UnknownHostException {
      _mongoClient = new MongoClient(Constants.MONGO_IP, 27017);
      _db = _mongoClient.getDB("aerog3m");
   }


   public void addFlight(final Flight flight) throws UnknownHostException {


      if (_mongoClient == null) {
         initializeMongo();
      }
      final GsonBuilder builder = new GsonBuilder();
      final Gson gson = builder.create();
      System.out.println(gson.toJson(flight));
      final DBObject dbo = (DBObject) JSON.parse(gson.toJson(flight));
      final DBCollection flights = _db.getCollection("flights");
      flights.insert(dbo);
   }


   public void cleanFlights() throws UnknownHostException {
      if (_mongoClient == null) {
         initializeMongo();
      }
      final DBCollection flights = _db.getCollection("flights");
      flights.drop();
   }

}
