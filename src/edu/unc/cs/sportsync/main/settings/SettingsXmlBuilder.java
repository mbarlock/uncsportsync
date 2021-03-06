package edu.unc.cs.sportsync.main.settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.sound.sampled.Mixer;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import edu.unc.cs.sportsync.main.ui.error.ErrorUtil;
/*************************************************************************************
 * 
 * Author(s) - Michael Barlock
 * 		   created: September 25, 2012
 * 	 last modified: October 30, 2012
 * 
 * Function - Creating the settings.xml file, and updating it with the most recent
 *          settings.
 * 
 * 
 *************************************************************************************/
public class SettingsXmlBuilder {

	Document doc;
	Element root;

	public SettingsXmlBuilder() {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			doc = docBuilder.newDocument();
			root = doc.createElement("settings");
			doc.appendChild(root);
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
			ErrorUtil.openStackTraceDialog("A Fatal Error has occured and the application will need to shut down", e);
			System.exit(1);
		}
	}

	public void addDelayTime(int delayTime) {
		Element delay = doc.createElement("maxDelay");
		delay.appendChild(doc.createTextNode(Integer.toString(delayTime)));
		root.appendChild(delay);
	}

	public void addInputMixer(Mixer.Info input) {
		Element mixer = doc.createElement("inputMixer");
		Element description = doc.createElement("description");
		Element name = doc.createElement("name");
		Element vendor = doc.createElement("vendor");
		Element version = doc.createElement("version");

		description.appendChild(doc.createTextNode(input.getDescription()));
		name.appendChild(doc.createTextNode(input.getName()));
		vendor.appendChild(doc.createTextNode(input.getVendor()));
		version.appendChild(doc.createTextNode(input.getVersion()));

		mixer.appendChild(name);
		mixer.appendChild(description);
		mixer.appendChild(vendor);
		mixer.appendChild(version);

		root.appendChild(mixer);
	}

	public void addOutputMixer(Mixer.Info output) {
		Element mixer = doc.createElement("outputMixer");
		Element description = doc.createElement("description");
		Element name = doc.createElement("name");
		Element vendor = doc.createElement("vendor");
		Element version = doc.createElement("version");

		description.appendChild(doc.createTextNode(output.getDescription()));
		name.appendChild(doc.createTextNode(output.getName()));
		vendor.appendChild(doc.createTextNode(output.getVendor()));
		version.appendChild(doc.createTextNode(output.getVersion()));

		mixer.appendChild(name);
		mixer.appendChild(description);
		mixer.appendChild(vendor);
		mixer.appendChild(version);

		root.appendChild(mixer);
	}

	public void addVolume(int vol) {
		Element volume = doc.createElement("volume");
		volume.appendChild(doc.createTextNode(Integer.toString(vol)));
		root.appendChild(volume);
	}

	public void saveTo(String path) {
		try {
			OutputFormat format = new OutputFormat(doc);
			format.setIndenting(true);
			XMLSerializer serializer = new XMLSerializer(new FileOutputStream(new File(path)), format);
			serializer.serialize(doc);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			ErrorUtil.openStackTraceDialog("A Fatal Error has occured and the application will need to shut down", e);
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
			ErrorUtil.openStackTraceDialog("A Fatal Error has occured and the application will need to shut down", e);
			System.exit(1);
		}

	}
}
