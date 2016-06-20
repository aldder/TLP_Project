package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.homepageparser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser.WebsiteParser;
import it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser.WebsiteParserResponse;

public class IlCentroHomePageParser implements
		WebsiteParser<IlCentroHomePageParserResponse> {

	@Override
	public WebsiteParserResponse<IlCentroHomePageParserResponse> getPostsFromResource(
			String resource) {

		List<AggregatorPost> responseContent = new ArrayList<AggregatorPost>();

		AggregatorPost a = new AggregatorPost();

		String articlePrefix = "http://ilcentro.gelocal.it";

		try {

			Document homepage = Jsoup.connect(resource).get();
			Elements articles = homepage.select("div.center-left");
			Elements list = articles.select("ul.linklist");
			Elements headers = list.select("h2, h3");

			for (Element el : headers) {

				Element linkwithTag = el.select("a[href]").first();

				String link = linkwithTag.attr("href");

				if (!link.startsWith("http")) {
					link = articlePrefix.concat(link);
				}

				AggregatorPost a1 = new AggregatorPost();

				Document article = Jsoup.connect(link).get();

				String title = article.getElementsByTag("h1").text();
				String author = article.getElementsByClass("author").text();
				String text = article.select("div.fontsize > p").text();
				Date data = toDate(article.getElementsByClass("date").text());
				int visibility = 2;
				String source = article.title();

				a1.setTitle(title);
				a1.setAuthor(author);
				a1.setText(text);
				a1.setDate(data);
				a1.setVisibility(visibility);
				a1.setSource(source);
				a1.setLink(link);

				System.out.println("Title: " + a1.getTitle());
				System.out.println("Author: " + a1.getAuthor());
				System.out.println("Text: " + a1.getText());
				System.out.println("Date: " + a1.getDate());
				System.out.println("Visibility: " + a1.getVisibility());
				System.out.println("Source: " + a1.getSource());
				System.out.println("Link: " + a1.getLink());
				System.out.println();

				responseContent.add(a1);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		IlCentroHomePageParserResponse ilCentroHomePageParserResponse = new IlCentroHomePageParserResponse(
				responseContent);
		WebsiteParserResponse<IlCentroHomePageParserResponse> parserResponse = new WebsiteParserResponse<IlCentroHomePageParserResponse>(
				ilCentroHomePageParserResponse);
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
