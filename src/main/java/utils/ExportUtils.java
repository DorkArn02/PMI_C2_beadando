package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import objects.Patient;

import javax.swing.*;
import javax.xml.transform.TransformerException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ExportUtils {

    private final XmlHandler xmlHandler2;

    public ExportUtils(XmlHandler xmlHandler2){
        this.xmlHandler2 = xmlHandler2;
    }

    /**
     * Exports the person data of patients'
     */
    public void exportPatientPersonalData() throws TransformerException {
        if(!xmlHandler2.Contains_Any_Patient()){
            JOptionPane.showMessageDialog(null, "Jelenleg az adatbázis üres.", "Export", JOptionPane.ERROR_MESSAGE);
        }else{
            ArrayList<Patient> patients = xmlHandler2.Get_All_Patients();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            try (PrintWriter file = new PrintWriter(new FileWriter("./patients_export.json"))) {
                gson.toJson(patients, file);
                JOptionPane.showMessageDialog(null, LanguageUtils.EXPORT_SUCCESS, "Információ", JOptionPane.INFORMATION_MESSAGE);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
