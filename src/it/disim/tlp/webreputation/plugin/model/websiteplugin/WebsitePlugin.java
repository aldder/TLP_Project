package it.disim.tlp.webreputation.plugin.model.websiteplugin;

import java.util.List;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPlugin;
import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPost;

public interface WebsitePlugin extends AggregatorPlugin {
	
	public List<AggregatorPost> parseResource(String resource);
	
}
