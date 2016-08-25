package com.target.exception;



/**
 * @author jyothiswaroopsingareddigari
 *
 */
public class AppException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public AppException(){
		
    }
	/**
	 * @param message
	 */
	public AppException(String message){

        super(message);
    }
	/**
	 * @param cause
	 */
	public AppException(Throwable cause){
        super(cause);
    }
	/**
	 * @param message
	 * @param cause
	 */
	public AppException(String message, Throwable cause){
        super(message, cause);
    }

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public AppException(String message, Throwable cause,boolean enableSuppression, boolean writableStackTrace){
        super();
   }

}
