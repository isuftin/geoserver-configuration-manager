package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.Datastore;
import gov.usgs.cida.manager.geoserver.model.Workspace;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;
import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import org.apache.commons.httpclient.NameValuePair;

/**
 *
 * @author isuftin
 */
class GeoserverInteractions {

	protected static final String DEFAULT_USER = "admin";
	protected static final String DEFAULT_PASS = "geoserver";
	private static GeoserverPublisher publisher;
	private static GeoserverReader reader;
	private static GeoserverStoreManager storeManager;

	protected GeoserverInteractions(URL geoserverEndpoint) throws MalformedURLException {
		this(geoserverEndpoint, DEFAULT_USER, DEFAULT_PASS);
	}

	protected GeoserverInteractions(URL geoserverEndpoint, String user, String pass) throws MalformedURLException {
		publisher = new GeoserverPublisher(geoserverEndpoint.toString(), user, pass);
		reader = new GeoserverReader(geoserverEndpoint.toString(), user, pass);
		storeManager = new GeoserverStoreManager(geoserverEndpoint, user, pass);
	}

	public boolean isAvailable() {
		return reader.existGeoserver();
	}

	public boolean existsDatastore(String workspace, String datastore, boolean ignoreCase) {
		return reader.existsDatastore(workspace, datastore, ignoreCase);
	}

	public boolean createWorkspace(String name, URI uri) {
		Workspace workspace = new Workspace();
		workspace.setName(name);
		workspace.setUri(uri);
		return createWorkspace(workspace);

	}

	public boolean createWorkspace(Workspace workspace) {
		return publisher.createWorkspace(workspace);
	}

	/**
	 * @see
	 * it.geosolutions.geoserver.rest.GeoServerRESTPublisher#removeWorkspace(java.lang.String,
	 * boolean)
	 * @param name
	 * @param recurse
	 * @return
	 */
	public boolean removeWorkspace(String name, boolean recurse) {
		return publisher.removeWorkspace(name, recurse);
	}

	/**
	 * Check if a workspace exists on the server
	 *
	 * @param workspace
	 * @param ignoreCase
	 * @return
	 */
	public boolean existsWorkspace(String workspace, boolean ignoreCase) {
		return reader.existsWorkspace(workspace, ignoreCase);
	}

	public boolean createDataStore(Datastore datastore) {
		return storeManager.create(datastore.getWorkspaceName(), datastore.getEncoder());
	}

	/**
	 *
	 * @see
	 * it.geosolutions.geoserver.rest.GeoServerRESTPublisher#removeDatastore(java.lang.String,
	 * java.lang.String, boolean)
	 * @param datastore
	 * @return
	 */
	public boolean removeDataStore(Datastore datastore, boolean recursive) {
		return publisher.removeDatastore(datastore.getWorkspaceName(), datastore.getName(), recursive);
	}

	/**
	 * @see
	 * it.geosolutions.geoserver.rest.GeoServerRESTPublisher#publishShp(java.lang.String,
	 * java.lang.String, org.apache.commons.httpclient.NameValuePair[],
	 * java.lang.String,
	 * it.geosolutions.geoserver.rest.GeoServerRESTPublisher.UploadMethod,
	 * java.net.URI, java.lang.String, java.lang.String,
	 * it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy,
	 * java.lang.String)
	 * @param workspace
	 * @param storeName
	 * @param storeParams
	 * @param datasetName
	 * @param method
	 * @param shapefile
	 * @param srs
	 * @param nativeCRS
	 * @param policy
	 * @param defaultStyle
	 * @return
	 * @throws IOException
	 */
	public boolean publishShapefile(String workspace, String storeName, NameValuePair[] storeParams,
			String datasetName, GeoServerRESTPublisher.UploadMethod method, URI shapefile, String srs, String nativeCRS,
			GSResourceEncoder.ProjectionPolicy policy, String defaultStyle) throws IOException {
		return publisher.publishShp(workspace, storeName, storeParams, datasetName,
				method, shapefile, srs, nativeCRS, policy, defaultStyle);
	}
}
