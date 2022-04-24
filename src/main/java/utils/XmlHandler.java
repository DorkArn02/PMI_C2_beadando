package utils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import objects.Diagnosis;
import objects.Patient;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;

public class XmlHandler {
    private Document doc;
    private final Logger logger = Logger.getLogger(XmlHandler.class.getName());
    
    /**
     * @param res_path Path to the patients.xml
     */
    public XmlHandler(String res_path) throws ParserConfigurationException, SAXException, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        try {
        	this.doc = db.parse(res_path);
        }catch(IOException e) {
        	this.doc = db.newDocument();
        	this.doc.appendChild(doc.createElement("patients"));
        	try {
				Update_Xml_File();
			} catch (TransformerException e1) {
				e1.printStackTrace();
			}
        }
    }

    /**
     * Returns true if the specified patient exists in the database.
     * @param TajNumber TAJ number of patient
     * @return boolean
     */
    public boolean Contains_Patient(String TajNumber){
    	NodeList nodeList = doc.getElementsByTagName("patient");
        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);
            if(node.getAttributes().getNamedItem("tajNumber") != null)
            	if(node.getAttributes().getNamedItem("tajNumber").getTextContent().equals(TajNumber))
            		return true;
        }
        return false;
    }

    /**
     * Adds a new patient to the database if it doesn't exist.
     * @param p Patient object
     */
    public void Add_Patient(Patient p) throws TransformerException {
        if (!Contains_Patient(p.getTajNumber())) {
            Element firstNameE = doc.createElement("firstName");
            Element lastNameE = doc.createElement("lastName");
            Element bornDateE = doc.createElement("bornDate");
            Element zipCodeE = doc.createElement("zipCode");
            Element cityE = doc.createElement("city");
            Element streetE = doc.createElement("street");
            Element houseNumberE = doc.createElement("houseNumber");
            Element motherNameE = doc.createElement("motherName");
            Element fatherNameE = doc.createElement("fatherName");
            Element bornPlaceE = doc.createElement("bornPlace");
            Element phoneNumberE = doc.createElement("phoneNumber");
            Element genderE = doc.createElement("gender");
            Element bloodGroupE = doc.createElement("bloodGroup");

            Element medicalHistoryE = doc.createElement("medicalHistory");

            firstNameE.setTextContent(p.getFirstName());
            lastNameE.setTextContent(p.getLastName());
            bornDateE.setTextContent(p.getBornDate());
            zipCodeE.setTextContent(p.getZipCode());
            cityE.setTextContent(p.getCity());
            streetE.setTextContent(p.getStreet());
            houseNumberE.setTextContent(p.getHouseNumber());
            motherNameE.setTextContent(p.getMotherName());
            fatherNameE.setTextContent(p.getFatherName());
            bornPlaceE.setTextContent(p.getBornPlace());
            phoneNumberE.setTextContent(p.getPhoneNumber());
            genderE.setTextContent(p.getGender());
            bloodGroupE.setTextContent(p.getBloodGroup());
            
            Element patientE = doc.createElement("patient");
            patientE.appendChild(firstNameE);
            patientE.appendChild(lastNameE);
            patientE.appendChild(bornDateE);
            patientE.appendChild(zipCodeE);
            patientE.appendChild(cityE);
            patientE.appendChild(streetE);
            patientE.appendChild(houseNumberE);
            patientE.appendChild(motherNameE);
            patientE.appendChild(fatherNameE);
            patientE.appendChild(bornPlaceE);
            patientE.appendChild(phoneNumberE);
            patientE.appendChild(genderE);
            patientE.appendChild(bloodGroupE);
            
            patientE.appendChild(medicalHistoryE);
            patientE.setAttribute("tajNumber", p.getTajNumber());

            doc.getDocumentElement().appendChild(patientE);

            Update_Xml_File();
            
            logger.info("Patient added to XML file with ID: " + p.getTajNumber());
        }else{
            logger.warning("Patient with ID:  " + p.getTajNumber() + " already exists in the database.");
        }
    }

    /**
     * Deletes a specified patient from the database if it exists.
     * @param tajNumber TAJ number of patient
     */
    public void Delete_Patient(String tajNumber) throws TransformerException {
        if (Contains_Patient(tajNumber)) {
            NodeList nodeList = doc.getElementsByTagName("patient");
            for(int i = 0; i < nodeList.getLength(); i++){
                Node node = nodeList.item(i);
                if(node.getAttributes().getNamedItem("tajNumber").getTextContent().equals(tajNumber)){
                    doc.getDocumentElement().removeChild(node);
                }
            }
            Update_Xml_File();
            logger.info("Patient with ID: " + tajNumber + " deleted from the database.");
        }else{
            logger.warning("Patient with ID: " + tajNumber + " does not exist.");
        }
    }

    /**
     * Modifies the data of a specified patient.
     * @param TajNumber TAJ number of patient
     * @param Detail Name of field which you want to edit
     * @param NewValue This value will be written into the xml file
     */
    public void Edit_Patient_Detail(String TajNumber, String Detail, String NewValue) throws TransformerException {
        if(Contains_Patient(TajNumber)){
            NodeList nodeList = doc.getElementsByTagName("patient");
            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(TajNumber)){
                        NodeList childNodes = n.getChildNodes();
                        for(int j = 0; j < childNodes.getLength(); j++){
                            Node nn = childNodes.item(j);
                            if(nn.getNodeType() == Node.ELEMENT_NODE){
                                if(nn.getNodeName().equals(Detail)){
                                    nn.setTextContent(NewValue);
                                    logger.info("Patient's " +  Detail + "  with ID: " + TajNumber + " has been successfully changed to " + NewValue);
                                }
                            }
                        }
                    }
                }
            }

            Update_Xml_File();
        }else{
            logger.warning("Patient with ID: " + TajNumber + " does not exist.");
        }
    }

    /**
     * Returns a patient object if it exists in the database.
     * @param TajNumber TAJ number of patient
     * @return Patient
     */
    public Patient Get_Patient(String TajNumber) throws TransformerException {
        Patient patient = new Patient();
        if(Contains_Patient(TajNumber)){
            NodeList nodeList = doc.getElementsByTagName("patient"); 
            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(TajNumber)){
                    	patient.setTajNumber(n.getAttributes().getNamedItem("tajNumber").getTextContent());
                        NodeList childNodes = n.getChildNodes();

                        for(int j = 0; j < childNodes.getLength(); j++){
                        		if(childNodes.item(j).getNodeName().equals("firstName"))
                        			patient.setFirstName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("lastName"))
                        			patient.setLastName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("bornDate"))
                        			patient.setBornDate(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("zipCode"))
                        			patient.setZipCode(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("city"))
                        			patient.setCity(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("street"))
                        			patient.setStreet(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("houseNumber"))
                        			patient.setHouseNumber(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("motherName"))
                        			patient.setMotherName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("fatherName"))
                        			patient.setFatherName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("bornPlace"))
                        			patient.setBornPlace(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("phoneNumber"))
                        			patient.setPhoneNumber(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("gender"))
                        			patient.setGender(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("bloodGroup"))
                        			patient.setBloodGroup(childNodes.item(j).getTextContent());
                        }
                        logger.info("Patient with ID: " + TajNumber + " retrieved from the database successfully.");
                    }
                }
            }
        }else{
            logger.warning("Patient with ID: " + TajNumber + " does not exist.");
        }
        
        return patient;
    }

    /**
     * Returns an ArrayList with patients
     * @return ArrayList<Patient>
     */
    public ArrayList<Patient> Get_All_Patients() throws TransformerException {          	
    	ArrayList<Patient> temPatients = new ArrayList<>();
    	NodeList nodeList = doc.getElementsByTagName("patient"); 
            for(int i = 0; i < nodeList.getLength(); i++){
                Patient patient = new Patient();
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    	patient.setTajNumber(n.getAttributes().getNamedItem("tajNumber").getTextContent());
                        NodeList childNodes = n.getChildNodes();
                        for(int j = 0; j < childNodes.getLength(); j++){
                        		if(childNodes.item(j).getNodeName().equals("firstName"))
                        			patient.setFirstName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("lastName"))
                        			patient.setLastName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("bornDate"))
                        			patient.setBornDate(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("zipCode"))
                        			patient.setZipCode(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("city"))
                        			patient.setCity(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("street"))
                        			patient.setStreet(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("houseNumber"))
                        			patient.setHouseNumber(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("motherName"))
                        			patient.setMotherName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("fatherName"))
                        			patient.setFatherName(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("bornPlace"))
                        			patient.setBornPlace(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("phoneNumber"))
                        			patient.setPhoneNumber(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("gender"))
                        			patient.setGender(childNodes.item(j).getTextContent());
                        		if(childNodes.item(j).getNodeName().equals("bloodGroup"))
                        			patient.setBloodGroup(childNodes.item(j).getTextContent());
                        }
                   }
                temPatients.add(patient);
            }

            logger.info("Data of all patients have been retrieved from the database.");

        return temPatients;
    }

    /**
     * Updates the XML file
     */
    public void Update_Xml_File() throws TransformerException {
        doc.getDocumentElement().normalize();

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File("src/main/resources/patients.xsl")));
        transformer.setOutputProperty(
                "{https://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

        DOMSource domSource = new DOMSource(doc);
        StreamResult streamResult = new StreamResult(new File("./patients.xml"));

        transformer.transform(domSource, streamResult);
        
        logger.info("XML file updated!");
    }

    /**
     * Registers a new diagnosis to a specified patient's medical history.
     * @param tajNumber TAJ number of patient
     * @param diagnosis Name of diagnosis
     * @param date Date of diagnosis
     * @param expertOpinion Expert's opinion
     */
    public void Add_Diagnosis_To_Patient_Medical_History(String tajNumber, String diagnosis, String date, String expertOpinion) throws TransformerException {
        if(!Contains_Patient(tajNumber)){
            logger.warning("Patient with ID: " + tajNumber + " does not exist.");
        }else if(Contains_Diagnosis_In_Specific_Date(tajNumber, date)) {
            logger.warning("Diagnosis with date: " + date + " already exists.");
        }else{

            Element diag = doc.createElement("diagnosis");
            Element name = doc.createElement("name");
            Element expOp = doc.createElement("expertOpinion");
            diag.setAttribute("date", date);
            name.setTextContent(diagnosis);
            expOp.setTextContent(expertOpinion);

            diag.appendChild(name);
            diag.appendChild(expOp);

            NodeList nodeList = doc.getElementsByTagName("patient");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(tajNumber)){

                        NodeList childNodes = n.getChildNodes();

                        for(int j = 0; j <childNodes.getLength(); j++){
                            Node nn = childNodes.item(j);
                            if(nn.getNodeType() == Node.ELEMENT_NODE){
                                if(nn.getNodeName().equals("medicalHistory")){
                                    nn.appendChild(diag);
                                }
                            }
                        }

                    }
                }
            }
            logger.info("Registered a new diagnosis to the patient with ID: " + tajNumber);
            Update_Xml_File();
        }
    }

    /**
     * Returns true if the patient's medical history contains a diagnosis with unique date.
     * @param TajNumber TAJ number of patient
     * @param date Date of diagnosis
     * @return boolean
     */
    public boolean Contains_Diagnosis_In_Specific_Date(String TajNumber, String date){
        if(!Contains_Patient(TajNumber)){
            logger.warning("Patient with ID: " + TajNumber + " does not exist.");
        }else{
            NodeList nodeList = doc.getElementsByTagName("patient");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(TajNumber)){
                        NodeList childNodes = n.getChildNodes();
                        for(int j = 0; j <childNodes.getLength(); j++){
                            Node nn = childNodes.item(j);
                            if(nn.getNodeType() == Node.ELEMENT_NODE){
                                if(nn.getNodeName().equals("medicalHistory")){
                                    NodeList diagnoses = nn.getChildNodes();
                                    for(int z = 0; z < diagnoses.getLength(); z++){
                                        Node nnn = diagnoses.item(z);
                                        if(nnn.getNodeType() == Node.ELEMENT_NODE){
                                            if(nnn.getAttributes().getNamedItem("date").getTextContent().equals(date))
                                                return true;
                                        }

                                    }


                                }
                            }
                        }

                    }
                }
            }
        }
        return false;
    }

    /**
     * Edits the specified patient's diagnosis.
     * @param tajNumber TAJ number of patient
     * @param date Date of diagnosis
     * @param field Name of field which you want to edit
     * @param value This value will be written into the xml file
     */
    public void Edit_Patient_Diagnosis(String tajNumber, String date, String field, String value) throws TransformerException {
        if(!Contains_Patient(tajNumber)){
            logger.warning("Patient with ID: " + tajNumber + " does not exist.");
        }else if(!Contains_Diagnosis_In_Specific_Date(tajNumber, date)){
            logger.warning("No diagnosis has been made on such a date, " + date);
        }else{
            NodeList nodeList = doc.getElementsByTagName("patient");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    // PÁCIENS KERESÉSE
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(tajNumber)){
                        NodeList childNodes = n.getChildNodes();
                        for(int j = 0; j < childNodes.getLength(); j++){
                            Node nn = childNodes.item(j);
                            if(nn.getNodeType() == Node.ELEMENT_NODE){
                                // PÁCIENS KÓRTÖRTÉNETE
                                if(nn.getNodeName().equals("medicalHistory")){
                                    NodeList diagnoses = nn.getChildNodes();
                                    for(int z = 0; z < diagnoses.getLength(); z++){
                                        Node nnn = diagnoses.item(z);
                                        if(nnn.getNodeType() == Node.ELEMENT_NODE){
                                            // ADOTT DÁTUMHOZ TARTOZÓ DIAGNÓZIS
                                            if(nnn.getAttributes().getNamedItem("date").getTextContent().equals(date)){
                                                if(nnn.getChildNodes().item(0).getNodeName().equals(field)){
                                                    nnn.getChildNodes().item(0).setTextContent(value);
                                                }
                                                else if(nnn.getChildNodes().item(1).getNodeName().equals(field)){
                                                    nnn.getChildNodes().item(1).setTextContent(value);
                                                }
                                            }
                                        }

                                    }


                                }
                            }
                        }

                    }
                }
            }

            Update_Xml_File();

        }
    }

    /**
     * Deletes a specified patient from database.
     * @param TajNumber TAJ number of patient
     * @param date Date of diagnosis
     */
    public void Delete_Patient_Diagnosis(String TajNumber, String date) throws TransformerException {
        if(!Contains_Patient(TajNumber)){
            logger.warning("Patient with ID: " + TajNumber + " does not exist.");
        }else if(!Contains_Diagnosis_In_Specific_Date(TajNumber, date)){
             logger.warning("No diagnosis has been made on such a date, " + date);
        }else{
            NodeList nodeList = doc.getElementsByTagName("patient");

            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    // PÁCIENS KERESÉSE
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(TajNumber)){
                        NodeList childNodes = n.getChildNodes();
                        for(int j = 0; j < childNodes.getLength(); j++){
                            Node nn = childNodes.item(j);
                            if(nn.getNodeType() == Node.ELEMENT_NODE){
                                // PÁCIENS KÓRTÖRTÉNETE
                                if(nn.getNodeName().equals("medicalHistory")){
                                    NodeList diagnoses = nn.getChildNodes();
                                    for(int z = 0; z < diagnoses.getLength(); z++){
                                        Node nnn = diagnoses.item(z);
                                        if(nnn.getNodeType() == Node.ELEMENT_NODE){
                                            if(nnn.getAttributes().getNamedItem("date").getTextContent().equals(date)){
                                                nn.removeChild(nnn);
                                                logger.info("Diagnosis with date: " + date + " has been deleted from the patient's medical history.");
                                            }
                                        }

                                    }


                                }
                            }
                        }

                    }
                }
            }

            Update_Xml_File();
        }
    }

    /**
     * Returns true if the patient's medical history is not empty.
     * @param TajNumber TAJ number of patient
     * @return boolean
     */
    public boolean Contains_Any_Diagnosis(String TajNumber){
        if(!Contains_Patient(TajNumber)){
            logger.warning("Patient with ID: " + TajNumber + " does not exist.");
        }else{
            NodeList nodeList = doc.getElementsByTagName("patient");
            for(int i = 0; i < nodeList.getLength(); i++){
                Node n = nodeList.item(i);
                if(n.getNodeType() == Node.ELEMENT_NODE){
                    if(n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(TajNumber)){
                        NodeList childNodes = n.getChildNodes();
                        for(int j = 0; j <childNodes.getLength(); j++){
                            Node nn = childNodes.item(j);
                            if(nn.getNodeType() == Node.ELEMENT_NODE){
                                if(nn.getNodeName().equals("medicalHistory")){
                                    NodeList diagnoses = nn.getChildNodes();
                                    for(int z = 0; z < diagnoses.getLength(); z++){
                                        Node nnn = diagnoses.item(z);
                                        if(nnn.getNodeType() == Node.ELEMENT_NODE){
                                                return false;
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return true;
    }

    /**
     * Returns true if there is any patient in the database.
     * @return boolean
     */
    public boolean Contains_Any_Patient(){
    	return doc.getElementsByTagName("patient").getLength() != 0;
    }

    /**
     *
     * @param tajNumber TAJ number of patient
     * @return ArrayList<Diagnosis>
     */
    public ArrayList<Diagnosis> List_Medical_History_Of_Patient(String tajNumber){
        ArrayList<Diagnosis> tempArrayList = new ArrayList<>();
        if(!Contains_Patient(tajNumber)){
            logger.warning("Patient with ID: " + tajNumber + " does not exist.");
        }else if(Contains_Any_Diagnosis(tajNumber)){
            logger.warning("Patient with ID: " + tajNumber + " doesn't have any diagnosis.");
        }else{
                NodeList nodeList = doc.getElementsByTagName("patient");
                for(int i = 0; i < nodeList.getLength(); i++) {
                    Node n = nodeList.item(i);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        if (n.getAttributes().getNamedItem("tajNumber").getTextContent().equals(tajNumber)) {
                            NodeList childNodes = n.getChildNodes();
                            for (int j = 0; j < childNodes.getLength(); j++) {
                                Node nn = childNodes.item(j);
                                if (nn.getNodeType() == Node.ELEMENT_NODE) {
                                    if (nn.getNodeName().equals("medicalHistory")) {
                                        NodeList diagnoses = nn.getChildNodes();
                                        for (int z = 0; z < diagnoses.getLength(); z++) {
                                            Node nnn = diagnoses.item(z);
                                            if (nnn.getNodeType() == Node.ELEMENT_NODE) {
                                            	Diagnosis diagnosis = new Diagnosis();
                                            	
                                            	diagnosis.setDate(nnn.getAttributes().getNamedItem("date").getTextContent());
                                            	
                                            	NodeList nnnn = nnn.getChildNodes();
                                            	
                                            	for(int zz = 0; zz < nnnn.getLength(); zz++) {
                                            		if(nnnn.item(zz).getNodeType() == Node.ELEMENT_NODE) {
                                            			if(nnnn.item(zz).getNodeName().equals("name")) {
                                            				diagnosis.setName(nnnn.item(zz).getTextContent());
                                            			}
                                            			if(nnnn.item(zz).getNodeName().equals("expertOpinion")) {
                                            				diagnosis.setExpertOpinion(nnnn.item(zz).getTextContent());
                                            			}
                                            		}
                                            	} 	
                                            	tempArrayList.add(diagnosis);
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
        }
        return tempArrayList;
    }
}
