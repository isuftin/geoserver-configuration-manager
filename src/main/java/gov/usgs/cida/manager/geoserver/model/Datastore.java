package gov.usgs.cida.manager.geoserver.model;

import it.geosolutions.geoserver.rest.encoder.GSAbstractStoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSAbstractDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSArcSDEDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSOracleNGDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author isuftin
 */
public class Datastore {

	public enum ALLOWED_TYPES {
		SHAPEFILE("shapefile", GSShapefileDatastoreEncoder.class),
		SHAPEFILE_DIRECTORY("shapefileDirectory", GSShapefileDatastoreEncoder.class),
		POSTGIS("postgis", GSPostGISDatastoreEncoder.class),
		ORACLE("oracle", GSOracleNGDatastoreEncoder.class),
		ARCSDE("arcsde", GSArcSDEDatastoreEncoder.class);

		private final String type;
		private Class<? extends GSAbstractDatastoreEncoder> encoder;
		private final static Map<String, ALLOWED_TYPES> map = new HashMap<>(ALLOWED_TYPES.values().length, 1.0f);
		
		static {
			for (ALLOWED_TYPES type : ALLOWED_TYPES.values()) {
				map.put(type.toString(), type);
			}
		}

		ALLOWED_TYPES(String type, Class<? extends GSAbstractDatastoreEncoder> encoder) {
			this.type = type;
			this.encoder = encoder;
		}
		
		public static ALLOWED_TYPES of(String type) {
			return map.get(type);
		}

		@Override
		public String toString() {
			return this.type;
		}

		private Class<? extends GSAbstractDatastoreEncoder> getEncoder() {
			return this.encoder;
		}
	}

	private Class<? extends GSAbstractDatastoreEncoder> encoder;
	private String workspaceName;
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
		if (StringUtils.isBlank(type) || Arrays.asList(ALLOWED_TYPES.values()).indexOf(type.trim().toLowerCase()) == -1) {
			throw new IllegalArgumentException(type + " is not an allowed datastore type");
		}

		this.encoder = ALLOWED_TYPES.of(type).getEncoder();

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

	void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getWorkspaceName() {
		return this.workspaceName;
	}

	public Class<? extends GSAbstractStoreEncoder> getEncoder() {
		return encoder;
	}

	public void setEncoder(Class<? extends GSAbstractDatastoreEncoder> encoder) {
		this.encoder = encoder;
	}

}
