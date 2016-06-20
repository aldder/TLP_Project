package it.disim.tlp.webreputation.exceptions.pluginexceptions;

public class QuotaExceededException extends Exception {

	private static final long serialVersionUID = 1L;
	private String error;
	
	@SuppressWarnings("unused")
	private QuotaExceededException(){
		/* constructor hiding that avoid
		 * throwing that exception without
		 * an error. 
		 */
	}
	
	public QuotaExceededException(String e){
		this.error = e;
	}
	
	public String getError(){
		return error;
	}
	
}
