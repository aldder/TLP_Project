package it.disim.tlp.webreputation.plugin.socialnetworkplugin.googleplus;

import it.disim.tlp.webreputation.exceptions.pluginexceptions.QuotaExceededException;

import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String args[]) {

		GooglePlusPlugin googleplus = new GooglePlusPlugin();
		try {
			Calendar cal = Calendar.getInstance();
			
			cal.set(2013, 5, 15); // date from 15 Jun 2013
			Date date1 = cal.getTime();
			
			System.out.println(date1);
			cal.set(2014, 5, 15); // date to 15 Jun 2014
			Date date2 = cal.getTime();

			googleplus.getPosts("https://plus.google.com/+AdrianByrne/posts", null, date1);
			System.out.println("------------------------------------------------------");
			googleplus.getPosts("https://plus.google.com/+AdrianByrne/posts", null, date1, date2);
			
		} catch (QuotaExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
