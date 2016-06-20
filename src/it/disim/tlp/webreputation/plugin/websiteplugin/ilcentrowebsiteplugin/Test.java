package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin;

import it.disim.tlp.webreputation.exceptions.pluginexceptions.QuotaExceededException;
import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websitepages.IlCentroHomePage;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websitepages.IlCentroSingleArticlePage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Test {

	public static void main(String[] args) {

		try {

			List<AggregatorPost> result = new ArrayList<AggregatorPost>();

			System.out.println("-----------------------------------------------");
			System.out.println("GETTING ALL ARTICLES FROM \"ilCentro\" HOMEPAGE");
			System.out.println("-----------------------------------------------\n");
			IlCentroHomePage homepage = new IlCentroHomePage();
			result.addAll(homepage.getPosts(
					"http://ilcentro.gelocal.it/pescara", null, new Date()));
			
			System.out.println("----------------------------------------------");
			System.out.println("GETTING AN ARTICLE FROM INPUT ARTICLE PAGE URL");
			System.out.println("----------------------------------------------\n");

			IlCentroSingleArticlePage singleArticlePage = new IlCentroSingleArticlePage();
			result.addAll(singleArticlePage
					.getPosts(
							"http://ilcentro.gelocal.it/pescara/cronaca/2014/07/03/news/pescara-scontro-toto-alessandrini-sulla-teodoro-assessore-1.9532727",
							null, new Date()));
			
			for (AggregatorPost aggregatorPost : result) {

			}

		} catch (QuotaExceededException e) {
			e.printStackTrace();
		}
	}
}
