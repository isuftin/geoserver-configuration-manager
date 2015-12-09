package gov.usgs.cida.manager.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStore;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import java.net.MalformedURLException;
import java.net.URL;

/**
 *
 * @author isuftin
 */
public class GeoserverInteractions {

	private static final String DEFAULT_USER = "admin";
	private static final String DEFAULT_PASS = "geoserver";
	private static GeoServerRESTManager mgr;
	private static GeoServerRESTReader reader;
	private static GeoServerRESTPublisher publisher;

	protected GeoserverInteractions(URL geoserverEndpoint) throws MalformedURLException {
		this(geoserverEndpoint, DEFAULT_USER, DEFAULT_PASS);

	}

	protected GeoserverInteractions(URL geoserverEndpoint, String user, String pass) throws MalformedURLException {
		mgr = new GeoServerRESTManager(geoserverEndpoint, user, pass);
		reader = mgr.getReader();
		publisher = mgr.getPublisher();
	}

	public boolean isAvailable() {
		return reader.existGeoserver();
	}

	public boolean existsDatastore(String workspace, String datastore, boolean ignoreCase) {
		RESTDataStoreList datastores = reader.getDatastores(workspace);
		for (String storeName : datastores.getNames()) {
			if (ignoreCase && storeName.equalsIgnoreCase(datastore)) {
				return true;
			} else if (!ignoreCase && storeName.equals(datastore)) {
				return true;
			}
		}
		return false;
	}
}
