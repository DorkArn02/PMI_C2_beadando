package frames;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerException;

import objects.Diagnosis;
import objects.Patient;
import utils.ValidationUtils;
import utils.XmlHandler2;

import static utils.LanguageUtils.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 * This is a JFrame where you can view the medical history of a specified patient.
 */
public class ViewMedicalHistory extends JFrame {

	private final JPanel contentPane;
	private final JTextField tajNumber;
	private final JTextArea patient_Adatok;

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public ViewMedicalHistory(XmlHandler2 xmlHandler2) throws IOException {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 773, 487);
		setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle(VIEW_MEDICAL_HISTORY_TITLE);
		setResizable(false);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, VIEW_MEDICAL_HISTORY_TITLE, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(24, 22, 380, 415);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_tajNumber = new JLabel(TAJ_NUMBER_LABEL);
		label_tajNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_tajNumber.setBounds(10, 35, 120, 20);
		panel.add(label_tajNumber);
		
		tajNumber = new JTextField();
		tajNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tajNumber.setColumns(10);
		tajNumber.setBounds(87, 37, 180, 25);
		panel.add(tajNumber);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, PATIENT_DATA_LABEL, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(414, 22, 333, 415);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		patient_Adatok = new JTextArea();
		patient_Adatok.setEditable(false);
		patient_Adatok.setFont(new Font("Arial", Font.PLAIN, 20));
		patient_Adatok.setBounds(10, 30, 313, 374);
		panel_1.add(patient_Adatok);
		
		JScrollPane kortortenet = new JScrollPane();
		kortortenet.setBounds(10, 145, 349, 259);
		panel.add(kortortenet);

		JTable table = new JTable();
		kortortenet.setViewportView(table);
		
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("Diagnózis");
		model.addColumn("Rögzítés dátuma");
		model.addColumn("Szakértői vélemény");
		table.setDefaultEditor(Object.class, null);
		table.setModel(model);

		// Search patient button
		JButton addDiagnosis = new JButton(SEARCH_PATIENT_BTN);
		addDiagnosis.addActionListener(e -> {
			if(!ValidationUtils.isValidTajNumber(tajNumber.getText())) {
				JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_TAJ_FORMAT , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}
			else if(!xmlHandler2.Contains_Patient(tajNumber.getText())) {
				JOptionPane.showMessageDialog(contentPane, PATIENT_NOT_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}else if(xmlHandler2.Contains_Any_Diagnosis(tajNumber.getText())) {
				JOptionPane.showMessageDialog(contentPane, DIAGNOSIS_NOT_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				try {
					Patient p = xmlHandler2.Get_Patient(tajNumber.getText());

					patient_Adatok.setText(p.toString());

				} catch (TransformerException e1) {
					e1.printStackTrace();
				}
			}else {
				ArrayList<Diagnosis> arrayList = xmlHandler2.List_Medical_History_Of_Patient(tajNumber.getText());
				if(model.getRowCount() != 0) {
					model.setRowCount(0);
					for(Diagnosis d : arrayList) {
						model.addRow(new Object[] {
							d.getName(),
							d.getDate(),
							d.getExpertOpinion()
						});
					}
				}else {
					for(Diagnosis d : arrayList) {
						model.addRow(new Object[] {
							d.getName(),
							d.getDate(),
							d.getExpertOpinion()
						});
					}
				}
				try {
					Patient p = xmlHandler2.Get_Patient(tajNumber.getText());

					patient_Adatok.setText(p.toString());

				} catch (TransformerException e1) {
					e1.printStackTrace();
				}
			}
		});
		addDiagnosis.setFont(new Font("Tahoma", Font.BOLD, 13));
		addDiagnosis.setBounds(177, 73, 89, 38);
		panel.add(addDiagnosis);
		
		JLabel label_medicalHistory = new JLabel(MEDICAL_HISTORY_2);
		label_medicalHistory.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_medicalHistory.setBounds(10, 120, 114, 20);
		panel.add(label_medicalHistory);

		// Window close confirm dialog
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        CONFIRM_EXIT_DATA, EXIT_LABEL,
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) {
			    	clearInputFields();
			    	model.setRowCount(0);
				    dispose();
			    }else {
			    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    }
			  }
			});
	}

	private void clearInputFields() {
		tajNumber.setText("");
		patient_Adatok.setText("");
	}
}