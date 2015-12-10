package gov.usgs.cida.manager.geoserver;

import gov.usgs.cida.manager.geoserver.model.GeoserverConfig;
import gov.usgs.cida.manager.yaml.GeoserverDescriptionYamlParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

/**
 *
 * @author isuftin
 */
public class ConfigurationClientBuilder {

	private static CmdLineParser parser;
	private final PrintStream outputStream;

	public ConfigurationClientBuilder() {
		outputStream = System.out;
	}

	public ConfigurationClientBuilder(PrintStream outputStream) {
		this.outputStream = outputStream;
	}

	@Option(name = "-h",
			aliases = {"--help"},
			usage = "Print this documentation",
			help = true,
			required = false,
			hidden = false)
	private boolean help;

	private File configFile;

	protected ConfigurationClient buildClient(String[] args) throws CmdLineException, IOException {
		ConfigurationClient result = null;
		parser = new CmdLineParser(this, ParserProperties.defaults());

		try {
			parser.parseArgument(args);
			if (help) {
				printUsage(this.outputStream);
			} else {
				GeoserverConfig config = new GeoserverDescriptionYamlParser(configFile).parse();
				result = new ConfigurationClient(config);
			}
		} catch (CmdLineException | IOException ex) {
			// User may not have passed in any arguments
			boolean missingArgmentList = "no argument is allowed:".equals(ex.getMessage().trim().toLowerCase());

			if (help || missingArgmentList) {
				printUsage(this.outputStream);
			} else {
				throw ex;
			}
		}
		return result;
	}

	protected void printUsage(PrintStream stream) {
		stream.println("java Client [options...] arguments...");
		stream.println("-------------------------------------");
		parser.printUsage(stream);
	}

	@Option(name = "-f",
			aliases = {"--file"},
			usage = "Configuration YAML file location",
			metaVar = "File",
			required = true,
			hidden = false)
	protected void setFile(File file) {
		this.configFile = file;
	}
}
