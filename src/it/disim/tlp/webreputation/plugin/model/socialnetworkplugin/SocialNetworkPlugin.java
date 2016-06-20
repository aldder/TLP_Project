package it.disim.tlp.webreputation.plugin.model.socialnetworkplugin;

import it.disim.tlp.webreputation.plugin.model.aggregatorplugin.AggregatorPlugin;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication.AuthRequest;
import it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication.AuthResponse;

public interface SocialNetworkPlugin<AUTHREQUEST_TYPE, AUTHRESPONSE_TYPE> extends AggregatorPlugin{

	public AuthResponse<AUTHRESPONSE_TYPE> authentication( AuthRequest<AUTHREQUEST_TYPE> request);
	
	
}
