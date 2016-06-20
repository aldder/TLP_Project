package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websitepages;

import java.util.ArrayList;
import java.util.List;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.IlCentroWebsitePluginController;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.singlearticlepageparser.IlCentroSingleArticlePageParser;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.singlearticlepageparser.IlCentroSingleArticlePageParserResponse;

public class IlCentroSingleArticlePage extends IlCentroWebsitePluginController { 
		
		IlCentroSingleArticlePageParser parser = new IlCentroSingleArticlePageParser();
	
		@Override
		public List<AggregatorPost> parseResource(String resource) {
			List<AggregatorPost> result = new ArrayList<AggregatorPost>();
			IlCentroSingleArticlePageParserResponse parserResponse = parser.getPostsFromResource(resource).getWebsiteParserResponseObject();
			result.addAll(parserResponse.getWebsiteParserResponseObject());
			return result;
		}
}