package gov.usgs.cida.manager.geoserver;

import it.geosolutions.geoserver.rest.manager.GeoServerRESTStoreManager;
import java.net.URL;

/**
 *
 * @author isuftin
 */
public class GeoserverStoreManager extends GeoServerRESTStoreManager{
	
	public GeoserverStoreManager(URL restURL, String username, String password) throws IllegalArgumentException {
		super(restURL, username, password);
	}
	
}
