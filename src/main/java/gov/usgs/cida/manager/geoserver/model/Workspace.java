package gov.usgs.cida.manager.geoserver.model;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Represents a GeoServer workspace
 *
 * @see
 * <a href="http://docs.geoserver.org/stable/en/user/rest/api/workspaces.html#workspaces">http://docs.geoserver.org/stable/en/user/rest/api/workspaces.html#workspaces</a>
 * @author isuftin
 */
public class Workspace implements VerifyingGSModel {

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
		return new ArrayList<>(datastores);
	}

	public void setDatastores(List<Datastore> datastores) {
		this.datastores = new ArrayList<>(datastores);
		this.datastores.stream().forEach(
				(datastore) -> datastore.setWorkspaceName(this.name)
		);
	}

	@Override
	public void verify() throws ModelVerifyException {
		if (StringUtils.isBlank(this.name)) {
			throw new ModelVerifyException("Workspace name is required");
		}
		if (this.datastores != null) {
			for (Datastore store : this.datastores) {
				store.verify();
			}
		}
	}

	@Override
	public String toString() {

		ToStringBuilder builder = new ToStringBuilder(this)
				.append("name", this.name);
		
		if (this.uri != null) {
			builder.append("uri", this.uri.toASCIIString());
		}
		
		return builder.toString();
	}

}
