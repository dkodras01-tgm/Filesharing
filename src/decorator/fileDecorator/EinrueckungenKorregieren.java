package decorator.fileDecorator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseException;
import com.github.javaparser.ast.CompilationUnit;

import decorator.MyFile;

/**
 * Eine Klasse, die einen Dekorierer darstellt.
 * In diesem Fall werden die Einrueckungen eines Java-Files korregiert.
 * 
 * @author Dominik Kodras
 * @version 22.04.2016
 */
public class EinrueckungenKorregieren extends Decorator {
	MyFile file;
	
	public EinrueckungenKorregieren(MyFile file) {
		this.file = file;
	}
	
	@Override
	public String getFileType() {
		return file.getFileType() + ", Einrueckungen korregiert";
	}

	@Override
	public File decorate() {
		CompilationUnit cu = null;
		try {			
			cu = JavaParser.parse(file.decorate());
		} catch (FileNotFoundException e) {
			System.out.println("File wurde nicht gefunden!");
			//e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("Fehler beim korregieren der Einrueckungen!");
			//e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Fehler beim Lesen des Files!");
			//e.printStackTrace();
		} finally {
			
		}
//		System.out.println(cu.toString());
		File f = new File("tmpfile.java");
		try (FileOutputStream fop = new FileOutputStream(f)) {
			byte[] contentInBytes = cu.toString().getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		} catch (IOException e) {
			System.out.println("Fehler beim Schreiben ins File!");
			//e.printStackTrace();
		}
		return f;
	}
}