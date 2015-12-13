package gov.usgs.cida.manager.geoserver.model;

/**
 *
 * @author isuftin
 */
public class ModelVerifyException extends Exception {

	/**
	 * Creates a new instance of <code>ModelVerifyException</code> without
	 * detail message.
	 */
	public ModelVerifyException() {
	}

	/**
	 * Constructs an instance of <code>ModelVerifyException</code> with the
	 * specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ModelVerifyException(String msg) {
		super(msg);
	}
}
