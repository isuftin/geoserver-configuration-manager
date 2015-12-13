package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.Workspace;
import it.geosolutions.geoserver.rest.GeoServerRESTPublisher;

/**
 *
 * @author isuftin
 */
public class GeoserverPublisher extends GeoServerRESTPublisher {

	protected GeoserverPublisher(String restURL, String username, String password) {
		super(restURL, username, password);
	}

	protected boolean createWorkspace(Workspace workspace) {
		if (workspace.getUri() == null) {
			return super.createWorkspace(workspace.getName());
		} else {
			return super.createWorkspace(workspace.getName(), workspace.getUri());
		}
	}
}
