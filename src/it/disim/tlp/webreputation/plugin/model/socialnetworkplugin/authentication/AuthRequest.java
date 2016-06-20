package it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication;

public class AuthRequest<AUTHREQUEST_TYPE> {
	
	private AUTHREQUEST_TYPE authRequest;
	
	public AuthRequest(AUTHREQUEST_TYPE authRequest){
		this.authRequest = authRequest;
	}
	
	public AUTHREQUEST_TYPE getAuthRequest() {
		return authRequest;
	}
	
	
		
}
