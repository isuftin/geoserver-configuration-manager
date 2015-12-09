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
		this(geoserverEndpoint, DEFAULT_USER, DEFAULT_PASS);
		
	}
	protected Interactions(URL geoserverEndpoint, String user, String pass) {
		reader = new GeoServerRESTReader(geoserverEndpoint, user, pass);
		publisher = new GeoServerRESTPublisher(geoserverEndpoint.toString(), user, pass);
	}
	
	public boolean isAvailable() {
		return reader.existGeoserver();
	}
}
