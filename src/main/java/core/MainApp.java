package core;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;
import frames.Dashboard;
import utils.XmlHandler;

/**
 * @version 1.0
 * @author Dorkó Arnold
 */
public class MainApp {
	
    private final Logger logger = Logger.getLogger(MainApp.class.getName());
	private XmlHandler xmlHandler2;
    
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.initXmlFiles();
		mainApp.initDashboard();
	}

	private void initDashboard() {		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			
			Dashboard d = new Dashboard(xmlHandler2);
			d.setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
			d.setVisible(true);	
			
			logger.info("Dashboard loaded successfully.");
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException | IOException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
	
	private void initXmlFiles() {
		try {
			xmlHandler2 = new XmlHandler("./patients.xml");
			logger.info("Xml files loaded successfully.");
		} catch (ParserConfigurationException | SAXException  | TransformerException e) {
			logger.log(Level.SEVERE, e.getMessage());
		}
	}
}
