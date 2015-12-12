package gov.usgs.cida.manager.geoserver.model;

import java.net.URI;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * Represents a Geoserver workspace
 *
 * @see <a href="http://docs.geoserver.org/stable/en/user/rest/api/workspaces.html#workspaces">http://docs.geoserver.org/stable/en/user/rest/api/workspaces.html#workspaces</a>
 * @author isuftin
 */
public class Workspace {

	private String name;
	private URI uri;
	private List<Datastore> datastores;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Workspace name may not be empty");
		}
		this.name = name;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public List<Datastore> getDatastores() {
		return datastores;
	}

	public void setDatastores(List<Datastore> datastores) {
		for (Datastore datastore : datastores) {
			datastore.setWorkspaceName(this.name);
		}
		this.datastores = datastores;
	}

}