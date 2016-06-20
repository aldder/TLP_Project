package it.disim.tlp.webreputation.plugin.websiteplugin.ilcentrowebsiteplugin.websiteparsers.singlearticlepageparser;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;
import it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser.WebsiteParserResponse;

import java.util.List;

public class IlCentroSingleArticlePageParserResponse extends WebsiteParserResponse<List<AggregatorPost>>{

	public IlCentroSingleArticlePageParserResponse(List<AggregatorPost> parserResponse) {
		super(parserResponse);
	}
	
}
