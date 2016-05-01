package decorator;

import java.io.File;

/**
 * Eine abstrakte Klasse, die als Grundelement fuer das Dekorieren verwendet wird.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public abstract class MyFile {
	public String path = "";
	public String fileType = "";
	
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public abstract File decorate();
}