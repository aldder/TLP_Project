package it.disim.tlp.webreputation.plugin.model.aggregatorplugin;

import it.disim.tlp.webreputation.exceptions.pluginexceptions.QuotaExceededException;

import java.util.Date;
import java.util.List;

public interface AggregatorPlugin {
	
	public List<AggregatorPost> getPosts(String resource, String type, Date from) throws QuotaExceededException;
	public List<AggregatorPost> getPosts(String resource, String type, Date from, Date to) throws QuotaExceededException;
	//public String getDateFromPlugin();
}
