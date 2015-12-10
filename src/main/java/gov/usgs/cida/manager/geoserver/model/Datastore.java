package gov.usgs.cida.manager.geoserver.model;

import java.net.URL;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author isuftin
 */
public class Datastore {
	public static final String[] ALLOWED_TYPES = new String[] {
		"shapefile",
		"shapefileDirectory",
		"postgis",
		"oracle",
		"arcsde"
	};
	private String name;
	private String type;
	private String description;
	private boolean enabled = false;
	private URL url;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Datastore name may not be empty");
		}
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (StringUtils.isBlank(type) || Arrays.asList(ALLOWED_TYPES).indexOf(type.trim().toLowerCase()) == -1) {
			throw new IllegalArgumentException(type + " is not an allowed datastore type");
		}
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}
	
}
