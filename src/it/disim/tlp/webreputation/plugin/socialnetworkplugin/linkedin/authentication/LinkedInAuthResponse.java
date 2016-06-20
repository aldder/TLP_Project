package it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin.authentication;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

public class LinkedInAuthResponse {
	
	private OAuthService linkedInService;
	private Token linkedInOAuthToken;
	
	/* LinkedInAuthResponse Constructor */
	public LinkedInAuthResponse(OAuthService linkedInService, Token linkedInOAuthToken){
		this.linkedInService = linkedInService;
		this.linkedInOAuthToken = linkedInOAuthToken;
	}
	/* Get LinkedInService */
	public OAuthService getLinkedInService() {
		return linkedInService;
	}
	/* Get LinkedInService OAuthToken */
	public Token getLinkedInOAuthToken() {
		return linkedInOAuthToken;
	}
	
	
	
}
