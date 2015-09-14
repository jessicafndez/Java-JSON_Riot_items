import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.json.JSONException;
import org.json.JSONObject;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONReader {
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static void readJsonFromUrl(String url) throws IOException, JSONException {
	    InputStream is = new URL(url).openStream();
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      JSONObject json = new JSONObject(jsonText);
	      Iterator<String> iter = json.keys();

	      while (iter.hasNext()) {
              String key = iter.next();
              System.out.println("KEY: "+key);
  
                  if (key.equalsIgnoreCase("data")) {
                	  JSONObject j2 = json.getJSONObject(key);
                      Iterator<String> iter2 = j2.keys();

                      while (iter2.hasNext()) {
                          String item = iter2.next() + ".png";
                          System.out.println("item: "+item);
                          SaveImage(item);
                      }
                  }     
	      }
	    } finally {
	      is.close();
	  }
	  }
	  
	  public static void SaveImage (String myItem) {
		  String myURL = "http://ddragon.leagueoflegends.com/cdn/5.17.1/img/item/" + myItem;
		  BufferedImage image = null;
	        try {
	          
	            URL url = new URL(myURL);
	            image = ImageIO.read(url);
	            
	            ImageIO.write(image, "png",new File("C:\\Riot_images\\"+myItem));
	        }catch (IOException e) {
	        	e.printStackTrace();
	        }
	        System.out.println("Done");
	    
	  }

	  public static void main(String[] args) throws IOException, JSONException {
		  readJsonFromUrl("http://ddragon.leagueoflegends.com/cdn/5.17.1/data/en_US/item.json");
	  }
}
