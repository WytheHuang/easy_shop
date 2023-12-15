package com.example;

import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
   public static JSONObject read_json(URI uri) throws JSONException, MalformedURLException, IOException {
      return new JSONObject(
         IOUtils.toString(
            uri.toURL(),
            Charset.forName("UTF-8")
         )
      );
   }

   public static void save_data(JSONObject data, String file_path) throws IOException {
      FileWriter file = new FileWriter(file_path);

      try {
         file.write(data.toString());
         file.flush();
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            file.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }
}
