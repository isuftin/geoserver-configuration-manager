package gov.usgs.cida.manager.geoserver.model;

import java.util.List;

/**
 *
 * @author isuftin
 */
public class GeoserverConfig {

	private List<GeoserverWorkspace> workspaces;	

	public List<GeoserverWorkspace> getWorkspaces() {
		return workspaces;
	}

	public void setWorkspaces(List<GeoserverWorkspace> workspaces) {
		this.workspaces = workspaces;
	}

}
