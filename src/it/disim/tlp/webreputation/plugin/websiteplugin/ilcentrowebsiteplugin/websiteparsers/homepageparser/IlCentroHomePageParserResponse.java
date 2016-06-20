package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.homepageparser;

import java.util.List;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser.WebsiteParserResponse;

public class IlCentroHomePageParserResponse extends WebsiteParserResponse<List<AggregatorPost>>{
	
	public IlCentroHomePageParserResponse(List<AggregatorPost> parserResponse) {
		super(parserResponse);
	}

}
