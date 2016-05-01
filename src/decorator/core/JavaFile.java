package decorator.core;

import java.io.File;
import java.io.FileNotFoundException;

import decorator.MyFile;

/**
 * Eine Klasse, die ein Coreelement fuers Dekorieren darstellt.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public class JavaFile extends MyFile {
	
	public JavaFile(String path) {
		this.fileType = "Java File";
		this.path = path;
	}
	
	@Override
	public File decorate() {
		File f = new File(path);
		return f;
	}
}