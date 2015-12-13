package gov.usgs.cida.manager.geoserver;

import it.geosolutions.geoserver.rest.GeoServerRESTReader;
import it.geosolutions.geoserver.rest.decoder.RESTDataStoreList;
import java.net.MalformedURLException;

/**
 *
 * @author isuftin
 */
public class GeoserverReader extends GeoServerRESTReader {

	public GeoserverReader(String gsUrl, String username, String password) throws MalformedURLException {
		super(gsUrl, username, password);
	}

	boolean existsWorkspace(String workspace, boolean ignoreCase) {
		for (String workspaceName : super.getWorkspaceNames()) {
			if (ignoreCase && workspaceName.equalsIgnoreCase(workspace)) {
				return true;
			} else if (!ignoreCase && workspaceName.equals(workspace)) {
				return true;
			}
		}
		return false;
	}

	boolean existsDatastore(String workspace, String datastore, boolean ignoreCase) {
		RESTDataStoreList datastores = super.getDatastores(workspace);
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
