package gov.usgs.cida.manager.geoserver.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author isuftin
 */
public class GeoserverConfig {
	private String username;
	private String password;
	private List<Workspace> workspaces;
	private boolean wipeDefaultWorkspaces = false;

	public List<Workspace> getWorkspaces() {
		return new ArrayList<>(workspaces);
	}

	public void setWorkspaces(List<Workspace> workspaces) {
		this.workspaces = new ArrayList<>(workspaces);
	}

	public boolean isWipeDefaultWorkspaces() {
		return wipeDefaultWorkspaces;
	}

	public void setWipeDefaultWorkspaces(boolean wipeDefaultWorkspaces) {
		this.wipeDefaultWorkspaces = wipeDefaultWorkspaces;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
