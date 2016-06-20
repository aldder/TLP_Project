package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websitepages;

import java.util.ArrayList;
import java.util.List;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.IlCentroWebsitePluginController;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.homepageparser.IlCentroHomePageParser;
import it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.homepageparser.IlCentroHomePageParserResponse;

public class IlCentroHomePage extends IlCentroWebsitePluginController {
	
	IlCentroHomePageParser parser = new IlCentroHomePageParser();
	
	@Override
	public List<AggregatorPost> parseResource(String resource) {
		List<AggregatorPost> result = new ArrayList<AggregatorPost>();
		IlCentroHomePageParserResponse parserResponse = parser.getPostsFromResource(resource).getWebsiteParserResponseObject();
		result.addAll(parserResponse.getWebsiteParserResponseObject());
		return result;
	}

}
