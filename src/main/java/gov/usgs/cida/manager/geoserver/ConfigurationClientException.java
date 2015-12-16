package gov.usgs.cida.manager.geoserver;

/**
 *
 * @author isuftin
 */
public class ConfigurationClientException extends Exception {

	/**
	 * Creates a new instance of <code>ConfigurationClientException</code>
	 * without detail message.
	 */
	public ConfigurationClientException() {
	}

	/**
	 * Constructs an instance of <code>ConfigurationClientException</code> with
	 * the specified detail message.
	 *
	 * @param msg the detail message.
	 */
	public ConfigurationClientException(String msg) {
		super(msg);
	}
}
