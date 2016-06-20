package it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin;

import it.disim.tlp.webreputation.exceptions.pluginexceptions.QuotaExceededException;
import java.util.Date;


public class Test {

	public static void main(String args[]) {

		LinkedInPlugin linkedInPlugin = new LinkedInPlugin();
		try {
			Date date = new Date((long) 1329830400 * 1000);
			linkedInPlugin.getPosts(
					"https://www.linkedin.com/groups?home=&gid=150030",
					"group_posts", date);
			linkedInPlugin.getPosts(null, "user_groups_posts", date);
		} catch (QuotaExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
