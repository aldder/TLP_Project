package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.singlearticlepageparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser.WebsiteParser;
import it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser.WebsiteParserResponse;

public class IlCentroSingleArticlePageParser implements
		WebsiteParser<IlCentroSingleArticlePageParserResponse> {

	@Override
	public WebsiteParserResponse<IlCentroSingleArticlePageParserResponse> getPostsFromResource(
			String resource) {

		List<AggregatorPost> responseContent = new ArrayList<AggregatorPost>();

		try {
			AggregatorPost a = new AggregatorPost();

			Document doc = Jsoup.connect(resource).get();

			String title = doc.getElementsByTag("h1").text();
			String author = doc.getElementsByClass("author").text();
			String text = doc.select("div.fontsize > p").text();
			Date data = toDate(doc.getElementsByClass("date").text());
			int visibility = 0;
			String source = doc.title();
			String link = resource;

			a.setTitle(title);
			a.setAuthor(author);
			a.setText(text);
			a.setDate(data);
			a.setVisibility(visibility);
			a.setSource(source);
			a.setLink(link);

			System.out.println("Title: " + a.getTitle());
			System.out.println("Author: " + a.getAuthor());
			System.out.println("Text: " + a.getText());
			System.out.println("Date: " + a.getDate());
			System.out.println("Visibility: " + a.getVisibility());
			System.out.println("Source: " + a.getSource());
			System.out.println("Link: " + a.getLink());
			System.out.println();

			responseContent.add(a);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		IlCentroSingleArticlePageParserResponse ilCentroSingleArticlePageParserResponse = new IlCentroSingleArticlePageParserResponse(
				responseContent);
		WebsiteParserResponse<IlCentroSingleArticlePageParserResponse> parserResponse = new WebsiteParserResponse<IlCentroSingleArticlePageParserResponse>(
				ilCentroSingleArticlePageParserResponse);
		return parserResponse;

	}

	public Date toDate(String s) throws IOException {
		Calendar cal = Calendar.getInstance();
		int month = 0;

		String months = s.substring(3, s.length() - 5);
		switch (months) {
		case "gennaio":
			month = 0;
			break;
		case "febbraio":
			month = 1;
			break;
		case "marzo":
			month = 2;
			break;
		case "aprile":
			month = 3;
			break;
		case "maggio":
			month = 4;
			break;
		case "giugno":
			month = 5;
			break;
		case "luglio":
			month = 6;
			break;
		case "agosto":
			month = 7;
			break;
		case "settembre":
			month = 8;
			break;
		case "ottobre":
			month = 9;
			break;
		case "novembre":
			month = 10;
			break;
		case "dicembre":
			month = 11;
			break;
		default: throw new IOException();
		}

		int day = Integer.parseInt(s.substring(0, 2));
		int year = Integer.parseInt(s.substring(s.length() - 4));

		cal.set(year, month, day);
		Date data = cal.getTime();
		return data;
	}

}
