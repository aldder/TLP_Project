package it.disim.tlp.webreputation.plugin.socialnetworkplugin.linkedin.authentication;

public class LinkedInAuthRequest {
	
	/* LinkedIn Application API Key */
	public final String apiKey;
	/* LinkedIn Application Secret Key*/
	public final String secretKey;
	/* LinkedIn oAuth User Token */
	public final String oAuthUserToken;
	/* LinkedIn oAuth User Secret Code */
	public final String oAuthUserSecret;
	
	/* Custom User Authentication Request Constructor */
	public LinkedInAuthRequest(String apiKey, String secretKey, String oAuthUserToken, String oAuthUserSecret){
		this.apiKey = apiKey;
		this.secretKey = secretKey;
		this.oAuthUserToken = oAuthUserToken;
		this.oAuthUserSecret = oAuthUserSecret;
	}
	
	/* Default User Authentication Request Constructor */
	public LinkedInAuthRequest(){
		this.apiKey = "7726wxcv6jcfqn";
		this.secretKey = "EzM9wMfMq7mUC9Jx";
		this.oAuthUserToken = "6ee3f203-1379-4b74-a2a7-dca7ba8d1224";
		this.oAuthUserSecret = "8d2bcb65-721f-47fd-be55-b12dcb7c0bae";
	}
	/* Get User API Key */
	public String getApiKey() {
		return apiKey;
	}
	/* Get User Secret Key */
	public String getSecretKey() {
		return secretKey;
	}
	/* Get OAuth User Token */
	public String getoAuthUserToken() {
		return oAuthUserToken;
	}
	/* Get OAuth User Secret Code */
	public String getoAuthUserSecret() {
		return oAuthUserSecret;
	}

}
