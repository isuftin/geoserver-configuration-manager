package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.Datastore;
import gov.usgs.cida.manager.geoserver.model.Workspace;
import it.geosolutions.geoserver.rest.GeoServerRESTManager;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import it.geosolutions.geoserver.rest.encoder.datastore.GSAbstractDatastoreEncoder;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

/**
 *
 * @author isuftin
 */
class GeoserverInteractions {

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
		Workspace workspace = new Workspace();
		workspace.setName(name);
		workspace.setUri(uri);
		return createWorkspace(workspace);
		
	}
	
	public boolean createWorkspace(Workspace workspace) {
		if (workspace.getUri() == null) {
			return mgr.getPublisher().createWorkspace(workspace.getName());
		} else {
			return mgr.getPublisher().createWorkspace(workspace.getName(), workspace.getUri());
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
	
	public boolean createDataStore(String workspaceName, String datastoreName) {
		Datastore datastore = new Datastore();
		return true; //TODO - Do stuff here
	}
	
	public boolean createDataStore(Datastore datastore) {
		
		mgr.getStoreManager().create(datastore.getWorkspaceName(), null);
		return true; //TODO - Do stuff here
	}
}
