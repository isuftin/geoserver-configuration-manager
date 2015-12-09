package gov.usgs.cida.manager.yaml;

import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

/**
 *
 * @author isuftin
 */
public class GeoserverDescriptionYamlParser {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GeoserverDescriptionYamlParser.class);

	private File yamlFile;

	public GeoserverDescriptionYamlParser(File yamlFile) {
		this.yamlFile = yamlFile;
	}

	public GeoserverConfig parse() throws IOException {
		if (!yamlFile.exists()) {
			throw new IOException(String.format("%s does not exist", yamlFile.getAbsolutePath()));
		}

		if (!yamlFile.isFile()) {
			throw new IOException(String.format("%s is not a file", yamlFile.getAbsolutePath()));
		}

		if (!yamlFile.canRead()) {
			throw new IOException(String.format("%s cannot be read", yamlFile.getAbsolutePath()));
		}
		Yaml yaml = new Yaml(new Constructor(GeoserverConfig.class));
		return (GeoserverConfig) yaml.load(new FileReader(yamlFile));

	}
}
