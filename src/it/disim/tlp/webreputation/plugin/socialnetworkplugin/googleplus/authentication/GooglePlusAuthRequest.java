package it.disim.tlp.webreputation.plugin.socialnetworkplugin.googleplus.authentication;

public class GooglePlusAuthRequest {
	final String API_KEY;

	public GooglePlusAuthRequest(String aPI_KEY) {
		API_KEY = aPI_KEY;
	}

	public GooglePlusAuthRequest() {
		this.API_KEY = "AIzaSyCbSGI_Y9SkISC03K8IgjUeLheb1oaGtI4";
	}

	public String getAPI_KEY() {
		return API_KEY;
	}
	
}
