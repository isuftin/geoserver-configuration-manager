package gov.usgs.cida.manager.geoserver.model;

import it.geosolutions.geoserver.rest.encoder.GSResourceEncoder;
import java.net.URI;
import java.nio.charset.Charset;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author isuftin
 */
public class Shapefile implements VerifyingGSModel {

	// The layer name that the shapefile will have
	private String name;
	// the name of the workspace to use
	private String workspace;
	// The name of the datastore to put shapefile in. Will be created if it
	// does not exist
	private String storeName;
	// Where to get the file from
	// Use file:// to upload from the file system
	// Use http:// or https:// to grab the file from a remote system
	private URI uri;
	// the SRS for this shapefile. It must be an EPSG code.
	private String srs;
	// Only set if projectionPolicy is set.
	// The nativeCRS for this shapefile. This should be an EPSG code.
	private String nativeCRS;
	/**
	 * @see it.geosolutions.geoserver.rest.encoder.GSResourceEncoder.ProjectionPolicy
	 */
	private GSResourceEncoder.ProjectionPolicy policy = GSResourceEncoder.ProjectionPolicy.NONE;
	// The default style to set
	private String defaultStyle;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public URI getUri() {
		return uri;
	}

	public void setUri(URI uri) {
		this.uri = uri;
	}

	public String getSrs() {
		return srs;
	}

	public void setSrs(String srs) {
		this.srs = srs;
	}

	public String getNativeCRS() {
		return nativeCRS;
	}

	public void setNativeCRS(String nativeCRS) {
		this.nativeCRS = nativeCRS;
	}

	public GSResourceEncoder.ProjectionPolicy getPolicy() {
		return policy;
	}

	public void setPolicy(GSResourceEncoder.ProjectionPolicy policy) {
		this.policy = policy;
	}

	public String getDefaultStyle() {
		return defaultStyle;
	}

	public void setDefaultStyle(String defaultStyle) {
		this.defaultStyle = defaultStyle;
	}

	@Override
	public void verify() throws ModelVerifyException {
		if (StringUtils.isBlank(this.name)) {
			throw new ModelVerifyException("Shapefile name is required");
		}
		
		if (this.uri == null) {
			throw new ModelVerifyException("Shapefile URI is required");
		}
		
		if (StringUtils.isBlank(this.srs)) {
			throw new ModelVerifyException("Shapefile SRS is required");
		}
		
		if (!this.policy.equals(GSResourceEncoder.ProjectionPolicy.NONE) && 
				(StringUtils.isBlank(nativeCRS))) {
			throw new ModelVerifyException("Shapefile native CRS is required if reprojection policy is not set to NONE");
		}
	}
}
