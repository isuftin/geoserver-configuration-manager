package gov.usgs.cida.manager.geoserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isuftin
 */
public class GeoserverConfig {

	private List<GeoserverWorkspace> workspaces;
	private boolean wipeDefaultWorkspaces = false;

	public List<GeoserverWorkspace> getWorkspaces() {
		return new ArrayList<>(workspaces);
	}

	public void setWorkspaces(List<GeoserverWorkspace> workspaces) {
		this.workspaces = new ArrayList<>(workspaces);
	}

	public boolean isWipeDefaultWorkspaces() {
		return wipeDefaultWorkspaces;
	}

	public void setWipeDefaultWorkspaces(boolean wipeDefaultWorkspaces) {
		this.wipeDefaultWorkspaces = wipeDefaultWorkspaces;
	}

}
