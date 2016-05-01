package kodras;

import org.apache.commons.cli2.*;
import org.apache.commons.cli2.builder.*;
import org.apache.commons.cli2.commandline.Parser;
import org.apache.commons.cli2.util.HelpFormatter;

/**
 * Eine Klasse, die für das verwalten der Kommandozeilenparameter zuständig ist.
 * 
 * @author Dominik Kodras
 * @version 21.01.2016
 */
public class MyCLI {
	
	/**
	 * Spezifikation ob Server oder Client.
	 * Kurzform: -sc
	 */
	private char sc = 'a';
	
	/**
	 * Die IP des Servers.
	 * Kurzform: -h
	 */
	private String ip = "not_set";
	
	/**
	 * Der Port des Servers.
	 * Kurzform: -p
	 */
	private String port = "1010";
	
	/**
	 * Der Pfad des Files.
	 * Kurzform: -d
	 */
	private String pfad = "not_set";
	
	/**
	 * Flag fuer Einrueckungen korregieren.
	 * Kurzform: -ek
	 */
	private boolean ek = false;
	
	/**
	 * Flag fuer Author loeschen.
	 * Kurzform: -ae
	 */
	private boolean ae = false;
	
	/**
	 * Flag fuer Package loeschen.
	 * Kurzform: -pe
	 */
	private boolean pe = false;
	
	/**
	 * Ueberprueft die uebergebenen Optionen und Argumente
	 * @param args Optionen und Argumente die uebergeben werden
	 */
	@SuppressWarnings("unchecked")
	public MyCLI(String[] args) {
		DefaultOptionBuilder obuilder = new DefaultOptionBuilder();
		ArgumentBuilder abuilder = new ArgumentBuilder();
		GroupBuilder gbuilder = new GroupBuilder();
		
		Option sc = obuilder.withShortName("sc").withRequired(true)
				.withArgument(abuilder.withName("Server/Client (s/c). Standard: -\tVERPFLICHTEND")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option ip = obuilder.withShortName("h").withRequired(false)
				.withArgument(abuilder.withName("IP(nur f\u00fcr Client zum Angeben). Standard: -\tOPTIONAL")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option port = obuilder.withShortName("p").withRequired(true)
				.withArgument(abuilder.withName("Port. Standard: 1010\tVERPFLICHTEND")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option pfad = obuilder.withShortName("d").withRequired(true)
				.withArgument(abuilder.withName("Pfad. Standard: -\tVERPFLICHTEND")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option ek = obuilder.withShortName("ek").withRequired(false)
				.withArgument(abuilder.withName("Option Einrueckungen korregieren. Standard: false\tOPTIONAL")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option ae = obuilder.withShortName("ae").withRequired(false)
				.withArgument(abuilder.withName("Option Author entfernen. Standard: false\tOPTIONAL")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Option pe = obuilder.withShortName("pe").withRequired(false)
				.withArgument(abuilder.withName("Option Package entfernen. Standard: false\tOPTIONAL")
						.withMinimum(1).withMaximum(1).create()).create();
		
		Group options = gbuilder.withName("options").withOption(ip).withOption(port).withOption(pfad)
				.withOption(sc).withOption(ek).withOption(ae).withOption(pe).create();
		
		Parser parser = new Parser();
		parser.setGroup(options);
		
		HelpFormatter hf = new HelpFormatter();
		hf.setShellCommand("FileSharing");
		hf.setGroup(options);
		hf.getFullUsageSettings().add(DisplaySetting.DISPLAY_GROUP_NAME);
		hf.getFullUsageSettings().add(DisplaySetting.DISPLAY_GROUP_ARGUMENT);
		hf.getFullUsageSettings().remove(DisplaySetting.DISPLAY_GROUP_EXPANDED);
		hf.getDisplaySettings().remove(DisplaySetting.DISPLAY_GROUP_ARGUMENT);
		hf.getLineUsageSettings().add(DisplaySetting.DISPLAY_PROPERTY_OPTION);
		hf.getLineUsageSettings().add(DisplaySetting.DISPLAY_PARENT_ARGUMENT);
		hf.getLineUsageSettings().add(DisplaySetting.DISPLAY_ARGUMENT_BRACKETED);
		//http://commons.apache.org/sandbox/commons-cli2/examples/ant.html
		
		
		/*
		 * Hier werden die Optionen und Argumente aus der args-Variable ausgelesen, und mit entsprechenden
		 * Meldungen und Exceptions verwaltet.
		 */
		try {
			CommandLine cl = parser.parse(args);
			if(cl.hasOption(sc)) {
				try {
					this.sc = ((String) cl.getValue(sc)).charAt(0);
				} catch(Exception e) {
//					this.sc = 'a';
					System.out.println("Fehler beim Einlesen: Bitte geben Sie s oder c fuer die Option -sc ein!");
					hf.print();
					System.exit(1);
				}
			}
			
			if(cl.hasOption(ip)) {
				try {
					this.ip = (String) cl.getValue(ip);
				} catch(Exception e) {
					this.ip = "not_set";
					System.out.println("Fehler beim Einlesen der IP!");
				}
			}
			
			if(cl.hasOption(port)) {
				try {
					this.port = (String) cl.getValue(port);
				} catch(Exception e) {
					this.port = "1010";
				}
			}
			
			if(cl.hasOption(pfad)) {
				try {
					this.pfad = (String) cl.getValue(pfad);
				} catch(Exception e) {
					this.pfad = "not_set";
				}
			}
			
			if(cl.hasOption(ek)) {
				try {
					this.ek = Boolean.valueOf((String) cl.getValue(ek));
				} catch(Exception e) {
					this.ek = false;
				}
			}
			
			if(cl.hasOption(ae)) {
				try {
					this.ae = Boolean.valueOf((String) cl.getValue(ae));
				} catch(Exception e) {
					this.ae = false;
				}
			}
			
			if(cl.hasOption(pe)) {
				try {
					this.pe = Boolean.valueOf((String) cl.getValue(pe));
				} catch(Exception e) {
					this.pe = false;
				}
			}
			
		} catch(OptionException e) {
			//wenn Verarbeiten der Optionen und Argmente fehlschlaegt, wird Hilfe/Beschreibung ausgegeben und Programm beendet
			//System.out.println(this.felder);
			hf.print();
			System.exit(1);
		}
	}
	
	/**
	 * Gibt die IP zurueck
	 * @return die IP
	 */
	public String getIP() {
		return ip;
	}

	/**
	 * Gibt den Port zurueck
	 * @return das Passwort
	 */
	public String getPort() {
		return port;
	}
	
	/**
	 * Gibt den Port zurueck
	 * @return den Port
	 */
	public String getPfad() {
		return pfad;
	}
	
	/**
	 * Gibt zurueck ob Server oder Client
	 * @return ob Server oder CLient (s/c)
	 */
	public char getSC() {
		return sc;
	}
	
	/**
	 * Gibt die Flag Einrueckungen Korregieren zurueck
	 * @return flag Einrueckungen korregieren
	 */
	public boolean getEK() {
		return ek;
	}
	
	/**
	 * Gibt die Flag Author entfernen zurueck
	 * @return flag Author entfernen
	 */
	public boolean getAE() {
		return ae;
	}
	
	/**
	 * Gibt die Flag Package entfernen zurueck
	 * @return flag Package entfernen
	 */
	public boolean getPE() {
		return pe;
	}
}