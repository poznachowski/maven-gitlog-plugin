package com.github.danielflower.mavenplugins.gitlog.renderers;

import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Scanner;

public abstract class FileRenderer implements ChangeLogRenderer {

	protected Writer writer;
	protected final Log log;

	public FileRenderer(Log log, File targetFolder, String filename) throws IOException {
		this.log = log;
		File file = new File(targetFolder, filename);
		log.debug("Creating git changelog at " + file.getAbsolutePath());
		writer = new FileWriter(file);
	}

	public void close() {
		if (writer != null) {
			try {
				writer.flush();
			} catch (IOException e) {
				log.error("Could not flush file to disk", e);
			}
			try {
				writer.close();
			} catch (IOException e) {
				// ignore
			}
		}
	}
	
	protected String convertStreamToString(InputStream is) {
		return new Scanner(is, "UTF-8").useDelimiter("\\A").next();
	}
}
