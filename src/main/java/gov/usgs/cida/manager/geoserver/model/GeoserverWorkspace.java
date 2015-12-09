package gov.usgs.cida.manager.geoserver.model;

import java.net.URI;

/**
 * Represents a Geoserver workspace
 *
 * @see <a href="http://docs.geoserver.org/stable/en/user/rest/api/workspaces.html#workspaces">http://docs.geoserver.org/stable/en/user/rest/api/workspaces.html#workspaces</a>
 * @author isuftin
 */
public class GeoserverWorkspace {

	private String name;
	private URI uri;

	public GeoserverWorkspace() {
		
	}
	
	public GeoserverWorkspace(String name) {
		this(name, null);
	}

	public GeoserverWorkspace(String name, URI uri) {
		this.name = name;
		this.uri = uri;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

}
