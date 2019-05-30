package main;

import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

public class Scraper extends Thread {

	
	public int minute = 99;
	
	@SuppressWarnings("deprecation")
	public void run() {
		File data = new File("/volume1/MYFX/Dax.csv");
	
		while(true) {
		
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			if(date.getDay()>0 && date.getDay()<6) {
				
				if(minute != date.getMinutes()) {
					try {
						FileWriter write = new FileWriter(data, true);
						int resultado = 0;
						String dataline = "";
						String URLSt = "https://www.myfxbook.com/community/outlook/GER30";
						try {
				            URL url = new URL(URLSt);
				            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				            String line;
				            while ((line = br.readLine()) != null) {
				            	if(line.contains("%") && line.contains("<td>")) {
				            		dataline = line;
				            	}
				            }
							dataline = dataline.replace("<td>","");
							dataline = dataline.replace("%</td>", "");
							dataline = dataline.replace(" ", "");
							resultado = Integer.valueOf(dataline);
							write.write( date.getDate() + "/" + date.getMonth() + "/" + (1900 + date.getYear()) + ","
							+ date.getHours() + ":" + date.getMinutes()  + "," + resultado  );
							write.write(System.getProperty( "line.separator" ));
							write.flush();
							minute = date.getMinutes();
							System.out.println("grabado");
							write.close();
						} catch (Exception e) {
							// TODO: handle exception
						}

					} catch (IOException e) {
					// TODO Auto-generated catch block
						e.printStackTrace();
				}	
				
			}
			}
		}
	}
	
}
