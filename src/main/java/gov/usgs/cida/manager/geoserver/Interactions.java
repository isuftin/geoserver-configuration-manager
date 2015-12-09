package gov.usgs.cida.manager.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import java.net.URL;

/**
 *
 * @author isuftin
 */
public class Interactions {

	private static final String DEFAULT_USER = "admin";
	private static final String DEFAULT_PASS = "geoserver";
	private static GeoServerRESTReader reader;
	private static GeoServerRESTPublisher publisher;

	protected Interactions(URL geoserverEndpoint) {
		this(geoserverEndpoint, DEFAULT_PASS, DEFAULT_USER);
		
	}
	protected Interactions(URL geoserverEndpoint, String user, String pass) {
		reader = new GeoServerRESTReader(geoserverEndpoint, DEFAULT_PASS, DEFAULT_USER);
		publisher = new GeoServerRESTPublisher(geoserverEndpoint.toString(), DEFAULT_PASS, DEFAULT_USER);
	}
	
	public boolean isAvailable() {
		return reader.existGeoserver();
	}
}
