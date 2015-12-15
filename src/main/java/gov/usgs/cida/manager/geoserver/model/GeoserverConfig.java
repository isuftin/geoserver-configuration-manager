package gov.usgs.cida.manager.geoserver.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author isuftin
 */
public class GeoserverConfig implements VerifyingGSModel {

	private String username;
	private String password;
	private List<Workspace> workspaces = null;
	private boolean wipeDefaultWorkspaces = false;
	private boolean stopOnError = true;

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

	@Override
	public void verify() throws ModelVerifyException {
		if (StringUtils.isBlank(this.username)) {
			throw new ModelVerifyException("Geoserver Config username is required");
		}

		if (StringUtils.isBlank(this.password)) {
			throw new ModelVerifyException("Geoserver Config password is required");
		}

		if (this.workspaces != null) {
			for (Workspace workspace : this.workspaces) {
				workspace.verify();
			}
		}
	}

}
