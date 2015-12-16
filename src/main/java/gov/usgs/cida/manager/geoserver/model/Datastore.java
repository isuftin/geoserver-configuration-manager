package gov.usgs.cida.manager.geoserver.model;

import it.geosolutions.geoserver.rest.encoder.GSAbstractStoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSAbstractDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSArcSDEDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSDirectoryOfShapefilesDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSOracleNGDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder;
import it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 *
 * @author isuftin
 */
public class Datastore implements VerifyingGSModel {

	private Class<? extends GSAbstractDatastoreEncoder> encoder;
	private TYPE datastoreType;
	private String workspaceName;
	private String name;
	private String typeName;
	private String description = "";
	private boolean enabled = false;
	private URL url;
	private String databaseName; // Oracle
	private String serverName; // ArcSDE
	private String userName; // ArcSDE
	private List<Shapefile> shapefiles = null;

	public Datastore() {
	}

	/**
	 * Setup for PostGIS stores
	 *
	 * @param workspaceName
	 * @see
	 * it.geosolutions.geoserver.rest.encoder.datastore.GSPostGISDatastoreEncoder
	 * @param name
	 */
	public Datastore(String workspaceName, String name) {
		if (StringUtils.isBlank(workspaceName)) {
			throw new IllegalArgumentException("Workspace name is required");
		}
		this.workspaceName = workspaceName;
		this.name = name;
		this.setDatastoreType(TYPE.POSTGIS);
	}

	/**
	 * Setup for Shapefile and Shapefile Directory stores
	 *
	 * @param workspaceName
	 * @see
	 * it.geosolutions.geoserver.rest.encoder.datastore.GSShapefileDatastoreEncoder
	 * @see
	 * it.geosolutions.geoserver.rest.encoder.datastore.GSDirectoryOfShapefilesDatastoreEncoder
	 * @param name
	 * @param url
	 */
	public Datastore(String workspaceName, String name, URL url) {
		if (StringUtils.isBlank(workspaceName)) {
			throw new IllegalArgumentException("Workspace name is required");
		}

		// Setting the datastore type for shapefile and shapefile directory encoders is not
		// possible because both types take two arguments (string and URL) so more 
		// info will be needed to solidify the type here
		this.workspaceName = workspaceName;
		this.name = name;
		this.url = url;
	}

	/**
	 * Setup for ArcSDE stores
	 *
	 * @param workspaceName
	 * @see
	 * it.geosolutions.geoserver.rest.encoder.datastore.GSArcSDEDatastoreEncoder
	 * @param name
	 * @param serverName
	 * @param userName
	 */
	public Datastore(String workspaceName, String name, String serverName, String userName) {
		if (StringUtils.isBlank(workspaceName)) {
			throw new IllegalArgumentException("Workspace name is required");
		}
		this.workspaceName = workspaceName;
		this.name = name;
		this.serverName = serverName;
		this.userName = userName;
		this.setDatastoreType(TYPE.ARCSDE);
	}

	/**
	 * Setup for Oracle datastores
	 *
	 * @param workspaceName
	 * @see
	 * it.geosolutions.geoserver.rest.encoder.datastore.GSOracleNGDatastoreEncoder
	 * @param name
	 * @param databaseName
	 */
	public Datastore(String workspaceName, String name, String databaseName) {
		if (StringUtils.isBlank(workspaceName)) {
			throw new IllegalArgumentException("Workspace name is required");
		}
		this.workspaceName = workspaceName;
		this.name = name;
		this.databaseName = databaseName;
		this.setDatastoreType(TYPE.ORACLE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Datastore name may not be empty");
		}
		this.name = name;
	}

	public String getTypeName() {
		return typeName;
	}

	/**
	 * Sets the datastore type name. The type name is a specific string that
	 * describes the datastore type. This will also set the formalized datastore
	 * type
	 *
	 * @see TYPE
	 * @param typeName
	 */
	public void setTypeName(String typeName) {
		TYPE typeEnum = TYPE.of(typeName);
		if (typeEnum == null) {
			throw new IllegalArgumentException(typeName + " is not an allowed datastore type");
		}
		if (this.getDatastoreType() == null) {
			this.setDatastoreType(typeEnum);
		}
		this.setEncoder(typeEnum.getEncoder());
		this.typeName = typeName;
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

	public void setWorkspaceName(String workspaceName) {
		this.workspaceName = workspaceName;
	}

	public String getWorkspaceName() {
		return this.workspaceName;
	}

	public GSAbstractStoreEncoder getEncoder() {
		switch (getDatastoreType()) {
			case SHAPEFILE:
				return new GSShapefileDatastoreEncoder(getName(), getUrl());
			case SHAPEFILE_DIRECTORY:
				return new GSDirectoryOfShapefilesDatastoreEncoder(getName(), getUrl());
			case ARCSDE:
				return new GSArcSDEDatastoreEncoder(getName(), getServerName(), getUserName());
			case ORACLE:
				return new GSOracleNGDatastoreEncoder(getName(), getDatabaseName());
			case POSTGIS:
				return new GSPostGISDatastoreEncoder(getName());
			default:
				throw new IllegalArgumentException("Encoder type not found");
		}

	}

	public void setEncoder(Class<? extends GSAbstractDatastoreEncoder> encoder) {
		this.encoder = encoder;
	}

	/**
	 * @return the datastoreType
	 */
	public TYPE getDatastoreType() {
		return datastoreType;
	}

	/**
	 * @param datastoreType the datastoreType to set
	 */
	public final void setDatastoreType(TYPE datastoreType) {
		this.datastoreType = datastoreType;
		if (StringUtils.isBlank(this.getTypeName())) {
			this.setTypeName(this.datastoreType.toString());
		}
	}

	/**
	 * Used for Oracle datastore type
	 *
	 * @return the databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * Used for Oracle datastore type
	 *
	 * @param databaseName the databaseName to set
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	/**
	 * Used for ArcSDE type
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Used for ArcSDE type
	 *
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * Used for ArcSDE type
	 *
	 * @return the serverName
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * Used for ArcSDE type
	 *
	 * @param serverName the serverName to set
	 */
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public List<Shapefile> getShapefiles() {
		if (shapefiles == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(shapefiles);
	}

	public void setShapefiles(List<Shapefile> shapefiles) throws ModelVerifyException {
		for (Shapefile shpFile : shapefiles) {
			shpFile.verify();
			shpFile.setWorkspace(this.workspaceName);
			shpFile.setStoreName(this.name);
		}
		this.shapefiles = new ArrayList<>(shapefiles);
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this)
				.append("name", this.name)
				.append("workspace", this.workspaceName)
				.append("type", this.datastoreType.toString())
				.append("description", this.description)
				.append("enabled", this.enabled);

		return builder.toString();
	}

	@Override
	public void verify() throws ModelVerifyException {
		if (StringUtils.isBlank(this.name)) {
			throw new ModelVerifyException("Store name is required");
		}

		if (StringUtils.isBlank(this.typeName)) {
			throw new ModelVerifyException("Store typeName is required");
		}

		if (this.datastoreType == null) {
			throw new ModelVerifyException("Store type is required");
		}

		if (this.datastoreType.equals(TYPE.ARCSDE) && StringUtils.isBlank(this.serverName)) {
			throw new ModelVerifyException("Store type ArcSDE requires a server name");
		}

		if (this.datastoreType.equals(TYPE.ARCSDE) && StringUtils.isBlank(this.userName)) {
			throw new ModelVerifyException("Store type ArcSDE requires a user name");
		}

		if (this.datastoreType.equals(TYPE.ORACLE) && StringUtils.isBlank(this.databaseName)) {
			throw new ModelVerifyException("Store type Oracle requires a database name");
		}

		if (this.shapefiles != null) {
			for (Shapefile shpFile : this.shapefiles) {
				shpFile.verify();
			}
		}
	}

	/**
	 * A formalized Geoserver Datastore type
	 */
	public enum TYPE {
		SHAPEFILE("shapefile", GSShapefileDatastoreEncoder.class),
		SHAPEFILE_DIRECTORY("shapefileDirectory", GSDirectoryOfShapefilesDatastoreEncoder.class),
		POSTGIS("postgis", GSPostGISDatastoreEncoder.class),
		ORACLE("oracle", GSOracleNGDatastoreEncoder.class),
		ARCSDE("arcsde", GSArcSDEDatastoreEncoder.class);

		private final String type;
		private Class<? extends GSAbstractDatastoreEncoder> encoder;
		private final static Map<String, TYPE> map = new HashMap<>(TYPE.values().length, 1.0f);

		static {
			for (TYPE type : TYPE.values()) {
				map.put(type.toString(), type);
			}
		}

		TYPE(String type, Class<? extends GSAbstractDatastoreEncoder> encoder) {
			this.type = type;
			this.encoder = encoder;
		}

		public static TYPE of(String type) {
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

}
