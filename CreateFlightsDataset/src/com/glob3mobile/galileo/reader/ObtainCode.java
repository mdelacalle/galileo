

package com.glob3mobile.galileo.reader;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;


public class ObtainCode {

   // Retrieve the CLIENT_ID and CLIENT_SECRET from an APIs Console project:
   //     https://code.google.com/apis/console
   static String       CLIENT_ID     = "795081586122-pb711k77pck9lraldi8k2eaj0p60b4qe.apps.googleusercontent.com";
   static String       CLIENT_SECRET = "9w9w0Rc0c7OuUB9CsnCc9nUK";
   // Change the REDIRECT_URI value to your registered redirect URI for web
   // applications.
   static String       REDIRECT_URI  = "http://www.glob3mobile.com";
   // Add other requested scopes.
   static List<String> SCOPES        = Arrays.asList("https://spreadsheets.google.com/feeds https://docs.google.com/feeds/");


   public static void main(final String[] args) throws IOException {

      final String authorizationUrl = new GoogleAuthorizationCodeRequestUrl(CLIENT_ID, REDIRECT_URI, SCOPES).build();

      // Point or redirect your user to the authorizationUrl.
      System.out.println("Go to the following link in your browser:");
      System.out.println(authorizationUrl);

   }

}
