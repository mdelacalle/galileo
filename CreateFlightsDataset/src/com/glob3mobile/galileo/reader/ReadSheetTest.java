

package com.glob3mobile.galileo.reader;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;


public class ReadSheetTest {

   // Retrieve the CLIENT_ID and CLIENT_SECRET from an APIs Console project:
   //     https://code.google.com/apis/console
   static String CLIENT_ID     = "795081586122-pb711k77pck9lraldi8k2eaj0p60b4qe.apps.googleusercontent.com";
   static String CLIENT_SECRET = "9w9w0Rc0c7OuUB9CsnCc9nUK";
   // Change the REDIRECT_URI value to your registered redirect URI for web
   // applications.
   static String REDIRECT_URI  = "http://www.glob3mobile.com";

   static String       CODE   = "4/D2MkvmLAGJHpF0L6UWM4p6UKR7AbdmmJVifgqkssJL8#";
   // Add other requested scopes.
   static List<String> SCOPES = Arrays.asList("https://spreadsheets.google.com/feeds https://docs.google.com/feeds/");


   public static void main(final String[] args) throws AuthenticationException, IOException, MalformedURLException,
                                                ServiceException {

      final Credential credencial = getCredentials();
      PrintSheetTest.printDocuments(credencial);

   }


   /**
    * Retrieve OAuth 2.0 credentials.
    *
    * @return OAuth 2.0 Credential instance.
    */
   static Credential getCredentials() throws IOException {
      final HttpTransport transport = new NetHttpTransport();
      final JacksonFactory jsonFactory = new JacksonFactory();

      final GoogleTokenResponse response = new GoogleAuthorizationCodeTokenRequest(transport, jsonFactory, CLIENT_ID,
               CLIENT_SECRET, CODE, REDIRECT_URI).execute();
               // End of Step 2 <--

      // Build a new GoogleCredential instance and return it.
      return new GoogleCredential.Builder().setClientSecrets(CLIENT_ID, CLIENT_SECRET).setJsonFactory(jsonFactory).setTransport(
               transport).build().setAccessToken(response.getAccessToken()).setRefreshToken(response.getRefreshToken());
   }

}
