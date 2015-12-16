package gov.usgs.cida.manager.geoserver.model;

import java.net.URL;
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
	private List<Workspace> workspaces;
	private boolean stopOnError = true;
	private URL endpoint;

	public List<Workspace> getWorkspaces() {
		if (workspaces == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(workspaces);
	}

	public void setWorkspaces(List<Workspace> workspaces) {
		this.workspaces = new ArrayList<>(workspaces);
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

	public void setEndpoint(URL endpoint) {
		this.endpoint = endpoint;
	}

	public boolean isStopOnError() {
		return stopOnError;
	}

	public void setStopOnError(boolean stopOnError) {
		this.stopOnError = stopOnError;
	}

	@Override
	public void verify() throws ModelVerifyException {
		if (StringUtils.isBlank(this.username)) {
			throw new ModelVerifyException("Geoserver Config username is required");
		}

		if (StringUtils.isBlank(this.password)) {
			throw new ModelVerifyException("Geoserver Config password is required");
		}
		
		if (this.endpoint == null || 
				(!this.endpoint.getProtocol().equals("http") && !this.endpoint.getProtocol().equals("https"))) {
			throw new ModelVerifyException("Geoserver endpoint is required to be an HTTP or HTTPS endpoint");
		}

		if (this.workspaces != null) {
			for (Workspace workspace : this.workspaces) {
				workspace.verify();
			}
		}
	}

	/**
	 * @return the endpoint
	 */
	public URL getEndpoint() {
		return endpoint;
	}

}
