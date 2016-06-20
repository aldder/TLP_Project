package it.disim.tlp.webreputation.plugin.model.websiteplugin.websiteparser;

public interface WebsiteParser<RESPONSE_OBJECT_TYPE> {
	
	public WebsiteParserResponse<RESPONSE_OBJECT_TYPE> getPostsFromResource(String resource);
	
}
