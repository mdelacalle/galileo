

package com.glob3mobile.galileo.reader;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;
import com.google.gdata.util.ServiceException;


public class PrintSheetTest {

   static void printDocuments(final Credential credential) throws IOException, ServiceException {
      // Instantiate and authorize a new SpreadsheetService object.

      final SpreadsheetService service = new SpreadsheetService("Galileo");
      service.setOAuth2Credentials(credential);
      // Send a request to the Documents List API to retrieve document entries.
      final URL SPREADSHEET_FEED_URL = new URL("https://spreadsheets.google.com/feeds/spreadsheets/private/full");
      // Make a request to the API and get all spreadsheets.
      final SpreadsheetFeed feed = service.getFeed(SPREADSHEET_FEED_URL, SpreadsheetFeed.class);
      final List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> spreadsheets = feed.getEntries();
      if (spreadsheets.isEmpty()) {
         // TODO: There were no spreadsheets, act accordingly.
      }
      final com.google.gdata.data.spreadsheet.SpreadsheetEntry spreadsheet = spreadsheets.get(0);
      System.out.println(spreadsheet.getTitle().getPlainText());
      // Get the first worksheet of the first spreadsheet.
      // TODO: Choose a worksheet more intelligently based on your
      // app's needs.
      final WorksheetFeed worksheetFeed = service.getFeed(spreadsheet.getWorksheetFeedUrl(), WorksheetFeed.class);
      final List<WorksheetEntry> worksheets = worksheetFeed.getEntries();
      final WorksheetEntry worksheet = worksheets.get(0);

      // Fetch the cell feed of the worksheet.
      final URL cellFeedUrl = worksheet.getCellFeedUrl();
      final CellFeed cellFeed = service.getFeed(cellFeedUrl, CellFeed.class);

      // Iterate through each cell, printing its value.
      for (final CellEntry cell : cellFeed.getEntries()) {
         // Print the cell's address in A1 notation
         System.out.print(cell.getTitle().getPlainText() + "\t");
         // Print the cell's address in R1C1 notation
         System.out.print(cell.getId().substring(cell.getId().lastIndexOf('/') + 1) + "\t");
         // Print the cell's formula or text value
         System.out.print(cell.getCell().getInputValue() + "\t");
         // Print the cell's calculated value if the cell's value is numeric
         // Prints empty string if cell's value is not numeric
         System.out.print(cell.getCell().getNumericValue() + "\t");
         // Print the cell's displayed value (useful if the cell has a formula)
         System.out.println(cell.getCell().getValue() + "\t");
      }

   }


}
