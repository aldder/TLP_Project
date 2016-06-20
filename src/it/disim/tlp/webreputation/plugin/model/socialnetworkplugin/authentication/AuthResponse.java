package it.disim.tlp.webreputation.plugin.model.socialnetworkplugin.authentication;

public class AuthResponse<AUTHRESPONSE_TYPE> {
	
	private AUTHRESPONSE_TYPE authResponse;

	public AuthResponse(AUTHRESPONSE_TYPE authResponse) {
		super();
		this.authResponse = authResponse;
	}

	public AUTHRESPONSE_TYPE getAuthResponse() {
		return authResponse;
	}
	
	
	
}
