package it.disim.tlp.webreputation.exceptions.pluginexceptions;

public class ResourceURLFormatException extends Exception {

	private static final long serialVersionUID = 8111992378369403312L;
	private String error;
	
	@SuppressWarnings("unused")
	private ResourceURLFormatException(){
		/* constructor hiding that avoid
		 * throwing that exception without
		 * an error. 
		 */
	}
	
	public ResourceURLFormatException(String error){
		this.error = error;
	}
	
	public String getError(){
		return error;
	}
	
}

