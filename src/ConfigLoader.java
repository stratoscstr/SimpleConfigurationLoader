

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;


public class ConfigLoader {
	private Logger logger;
	/*
	 * This API is a settings configuration loader based on: <variable> =
	 * <assignment>
	 * 
	 * Simple, right?
	 */
	private ArrayList<String> vars;

	private File configFile;

	public ConfigLoader(File configFile, Logger logger) {
		this.configFile = configFile;
		this.logger = logger;
		vars = new ArrayList<String>();
	}

	public void load() throws FileNotFoundException {
		if (!configFile.exists()) {
			throw new FileNotFoundException();
		}

		Scanner scanner = null;
		try {
			scanner = new Scanner(configFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			vars.add(line);
		}
	}

	public String getVariable(String name) {
		for (String var : vars) {
			if (var.startsWith(name)) {
				String replace1 = var.replaceFirst(" =", "=");
				String replace2 = replace1.replaceFirst("= ", "=");
				String[] split = replace2.split("=", 2);
				return split[1];
			}
		}
		return null;
	}

	/*
	 * A hashmap is passed through with variables and their default value. If no
	 * value is set, it is left blank
	 */

	public void createConfigFile(ArrayList<String> variables) {
		if (this.configFile.exists()) {
			this.configFile.delete();
			try {
				this.configFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		PrintWriter writer;
		try {
			writer = new PrintWriter(this.configFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
		for (String var : variables) {
			writer.println(var);
		}
		writer.flush();
		writer.close();
	}

}
