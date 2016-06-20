package it.disim.tlp.webreputation.plugin.socialnetworkplugin.googleplus;

import it.disim.tlp.webreputation.exceptions.pluginexceptions.QuotaExceededException;
import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.SocialNetworkPlugin;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication.AuthRequest;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication.AuthResponse;
import it.disim.tlp.webreputation.plugin.socialnetworkplugin.googleplus.authentication.GooglePlusAuthRequest;
import it.disim.tlp.webreputation.plugin.socialnetworkplugin.googleplus.authentication.GooglePlusAuthResponse;
import it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin.authentication.LinkedInAuthRequest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusRequestInitializer;
import com.google.api.services.plus.model.Activity;
import com.google.api.services.plus.model.ActivityFeed;

public class GooglePlusPlugin implements
		SocialNetworkPlugin<GooglePlusAuthRequest, GooglePlusAuthResponse> {

	@Override
	public List<AggregatorPost> getPosts(String resource, String type, Date from)
			throws QuotaExceededException {

		GooglePlusAuthRequest googleauth = new GooglePlusAuthRequest();

		List<AggregatorPost> result = new LinkedList<AggregatorPost>();
		String id = getId(resource);

		/** API_KEY Authentication */
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		Plus plus = new Plus.Builder(httpTransport, jsonFactory, null)
				.setApplicationName("My Project")
				.setGoogleClientRequestInitializer(
						new PlusRequestInitializer(googleauth.getAPI_KEY()))
				.build();

		try {
			Plus.Activities.List listActivities = plus.activities().list(id,
					"public");
			listActivities.setMaxResults(5L);
			ActivityFeed activityFeed = listActivities.execute();
			List<Activity> activities = activityFeed.getItems();
			while (activities != null) {
				for (Activity activity : activities) {

					Date activityDate = parseRFC3339Date(activity
							.getPublished().toString());
					if (from.after(activityDate)) {
						System.out.println("Activity " + activity.getId()
								+ " out of date");
						continue;
					}

					AggregatorPost a = new AggregatorPost();

					a.setTitle(activity.getTitle());
					a.setText(activity.getObject().getContent());
					a.setAuthor(activity.getActor().getDisplayName());
					a.setDate(activityDate);
					a.setVisibility(safeLongToInt(activity.getObject()
							.getPlusoners().getTotalItems()));
					a.setSource("Google+");
					a.setLink(activity.getUrl());

					result.add(a);

					System.out.println("Title: " + a.getTitle());
					System.out.println("Text: " + a.getText());
					System.out.println("Author: " + a.getAuthor());
					System.out.println("Date: " + a.getDate());
					System.out.println("Visibility: " + a.getVisibility());
					System.out.println("Source: Google+");
					System.out.println("Link: " + a.getLink());
					System.out.println();

				}
				if (activityFeed.getNextPageToken() == null) {
					break;
				}
				listActivities.setPageToken(activityFeed.getNextPageToken());
				activityFeed = listActivities.execute();
				activities = activityFeed.getItems();
			}
		} catch (IOException | IndexOutOfBoundsException | ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public List<AggregatorPost> getPosts(String resource, String type,
			Date from, Date to) throws QuotaExceededException {

		GooglePlusAuthRequest googleauth = new GooglePlusAuthRequest();

		List<AggregatorPost> result = new LinkedList<AggregatorPost>();
		String id = getId(resource);

		/** API_KEY Authentication */
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		Plus plus = new Plus.Builder(httpTransport, jsonFactory, null)
				.setApplicationName("My Project")
				.setGoogleClientRequestInitializer(
						new PlusRequestInitializer(googleauth.getAPI_KEY()))
				.build();

		try {
			Plus.Activities.List listActivities = plus.activities().list(id,
					"public");
			listActivities.setMaxResults(5L);
			ActivityFeed activityFeed = listActivities.execute();
			List<Activity> activities = activityFeed.getItems();
			while (activities != null) {
				for (Activity activity : activities) {

					Date activityDate = parseRFC3339Date(activity
							.getPublished().toString());
					if (from.after(activityDate) || to.before(activityDate)) {
						System.out.println("Activity " + activity.getId()
								+ " out of date");
						continue;
					}

					AggregatorPost a = new AggregatorPost();

					a.setText(activity.getObject().getContent());
					a.setAuthor(activity.getActor().getDisplayName());
					a.setDate(activityDate);
					a.setVisibility(safeLongToInt(activity.getObject()
							.getPlusoners().getTotalItems()));
					a.setSource("Google+");
					a.setLink(activity.getUrl());

					result.add(a);

					System.out.println("Text: " + a.getText());
					System.out.println("Author: " + a.getAuthor());
					System.out.println("Date: " + a.getDate());
					System.out.println("Visibility: " + a.getVisibility());
					System.out.println("Source: Google+");
					System.out.println("Link: " + a.getLink());
					System.out.println();

				}
				if (activityFeed.getNextPageToken() == null) {
					break;
				}
				listActivities.setPageToken(activityFeed.getNextPageToken());
				activityFeed = listActivities.execute();
				activities = activityFeed.getItems();
			}
		} catch (IOException | IndexOutOfBoundsException | ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public AuthResponse<GooglePlusAuthResponse> authentication(
			AuthRequest<GooglePlusAuthRequest> request) {

		return null;
	}

	private String getId(String resource) {
		String id2 = new String();
		if (resource.contains("communities")) {
			id2 = resource.substring(resource.lastIndexOf("/")+1);
		} else {
			String id1 = resource.substring(0, resource.lastIndexOf("/"));
			id2 = id1.substring(id1.lastIndexOf("/") + 1);
		}
		return id2;
	}

	public static java.util.Date parseRFC3339Date(String datestring)
			throws java.text.ParseException, IndexOutOfBoundsException {
		Date d = new Date();
		if (datestring.endsWith("Z")) {
			try {
				SimpleDateFormat s = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss'Z'");
				d = s.parse(datestring);
			} catch (java.text.ParseException pe) {
				SimpleDateFormat s = new SimpleDateFormat(
						"yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
				s.setLenient(true);
				d = s.parse(datestring);
			}
			return d;
		}
		String firstpart = datestring.substring(0, datestring.lastIndexOf('-'));
		String secondpart = datestring.substring(datestring.lastIndexOf('-'));
		secondpart = secondpart.substring(0, secondpart.indexOf(':'))
				+ secondpart.substring(secondpart.indexOf(':') + 1);
		datestring = firstpart + secondpart;
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
		try {
			d = s.parse(datestring);
		} catch (java.text.ParseException pe) {
			s = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSZ");
			s.setLenient(true);
			d = s.parse(datestring);
		}
		return d;
	}

	public static int safeLongToInt(long l) {
		if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(l
					+ " cannot be cast to int without changing its value.");
		}
		return (int) l;
	}

}
