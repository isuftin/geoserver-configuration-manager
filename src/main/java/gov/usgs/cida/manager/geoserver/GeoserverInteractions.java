package gov.usgs.cida.manager.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 *
 * @author isuftin
 */
public class GeoserverInteractions {

	protected static final String DEFAULT_USER = "admin";
	protected static final String DEFAULT_PASS = "geoserver";
	private static GeoServerRESTManager mgr;

	protected GeoserverInteractions(URL geoserverEndpoint) throws MalformedURLException {
		this(geoserverEndpoint, DEFAULT_USER, DEFAULT_PASS);

	}

	protected GeoserverInteractions(URL geoserverEndpoint, String user, String pass) throws MalformedURLException {
		mgr = new GeoServerRESTManager(geoserverEndpoint, user, pass);
	}

	public boolean isAvailable() {
		return mgr.getReader().existGeoserver();
	}

	public boolean existsDatastore(String workspace, String datastore, boolean ignoreCase) {
		RESTDataStoreList datastores = mgr.getReader().getDatastores(workspace);
		for (String storeName : datastores.getNames()) {
			if (ignoreCase && storeName.equalsIgnoreCase(datastore)) {
				return true;
			} else if (!ignoreCase && storeName.equals(datastore)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean createWorkspace(String name, URI uri) {
		if (uri == null) {
			return mgr.getPublisher().createWorkspace(name);
		} else {
			return mgr.getPublisher().createWorkspace(name, uri);
		}
	}
	
	public boolean existsWorkspace(String workspace, boolean ignoreCase) {
		for (String workspaceName : mgr.getReader().getWorkspaceNames()) {
			if (ignoreCase && workspaceName.equalsIgnoreCase(workspace)) {
				return true;
			} else if (!ignoreCase && workspaceName.equals(workspace)) {
				return true;
			}
		}
		return false;
	}
}
