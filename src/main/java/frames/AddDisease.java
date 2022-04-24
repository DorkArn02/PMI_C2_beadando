package frames;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import com.github.javafaker.Faker;

import utils.ValidationUtils;
import utils.XmlHandler;

import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Objects;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerException;

import static utils.LanguageUtils.*;
import com.github.lgooddatepicker.components.DateTimePicker;

import objects.Diagnosis;
import objects.Patient;
import javax.swing.JTextPane;

/**
 * This is a JFrame where you can add a diagnosis to the specified patient
 */
public class AddDisease extends JFrame {
	private final JPanel contentPane;
	private final JTextField tajNumber;
	private final JTextField diagnosis;
	private final JTextField tajNumber2;
	private final JTextArea expertOpinion;
	private final DateTimePicker diagnosis_date;
	private final JComboBox<String> comboBox;
	private final JTextPane medicalHistoryPanel;
	
	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public AddDisease(XmlHandler xmlHandler2) throws IOException {
		setTitle(ADD_DISEASE_TITLE);
		setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 985, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, ADD_DISEASE_TITLE, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(20, 25, 426, 455);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel label_tajNumber = new JLabel(TAJ_NUMBER_LABEL);
		label_tajNumber.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_tajNumber.setBounds(10, 38, 120, 20);
		panel.add(label_tajNumber);
		
		JLabel label_diagnosis = new JLabel(DIAGNOSIS_LABEL);
		label_diagnosis.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_diagnosis.setBounds(10, 79, 120, 20);
		panel.add(label_diagnosis);
		
		JLabel label_expertOpinion = new JLabel(EXPERT_OPINION_LABEL);
		label_expertOpinion.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_expertOpinion.setBounds(10, 141, 160, 20);
		panel.add(label_expertOpinion);
		
		JLabel label_date = new JLabel(DIAGNOSIS_DATE_LABEL);
		label_date.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_date.setBounds(10, 110, 130, 20);
		panel.add(label_date);
		
		tajNumber = new JTextField();
		tajNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tajNumber.setColumns(10);
		tajNumber.setBounds(145, 34, 247, 30);

		// When you type in the TAJ number, and it exists then this popup opens
		tajNumber.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        if(xmlHandler2.Contains_Patient(tajNumber.getText())){
		        	try {
		        		Patient p = xmlHandler2.Get_Patient(tajNumber.getText());
		        		JOptionPane.showMessageDialog(panel, "TAJ szám találat: " + p.getLastName() + " " + p.getFirstName(), "Találat", JOptionPane.INFORMATION_MESSAGE);
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
		        }
		    }

		    @Override
		    public void removeUpdate(DocumentEvent e) {
		    }

		    @Override
		    public void changedUpdate(DocumentEvent e) {
		    }
		});
	
		panel.add(tajNumber);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 172, 382, 225);
		panel.add(scrollPane);
		
		expertOpinion = new JTextArea();
		scrollPane.setViewportView(expertOpinion);
		expertOpinion.setColumns(2);
		expertOpinion.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		
		diagnosis_date = new DateTimePicker();
		diagnosis_date.setBorder(null);
		diagnosis_date.getTimePicker().getComponentTimeTextField().setFont(new Font("Segoe UI", Font.PLAIN, 15));
		diagnosis_date.getDatePicker().getComponentDateTextField().setFont(new Font("Segoe UI", Font.PLAIN, 15));
		diagnosis_date.setBounds(145, 110, 271, 30);
		panel.add(diagnosis_date);

		JButton addDiagnosis = new JButton(ADD_DIAGNOSIS_BTN);

		// Add diagnosis button
		addDiagnosis.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {				
				String dat = diagnosis_date.getDatePicker().getDateStringOrEmptyString();
				String time = diagnosis_date.getTimePicker().getTimeStringOrEmptyString();
								
				String res = dat + " " + time;
				
				if(tajNumber.getText().length() == 0
						|| expertOpinion.getText().length() == 0
						|| dat.length() == 0
						|| time.length() == 0) {
					JOptionPane.showMessageDialog(contentPane, ERROR_EMPTY_FIELDS, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}else {
					if(!xmlHandler2.Contains_Patient(tajNumber.getText())) {
						JOptionPane.showMessageDialog(contentPane, PATIENT_NOT_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
					}else if(xmlHandler2.Contains_Diagnosis_In_Specific_Date(tajNumber.getText(), res)){
						JOptionPane.showMessageDialog(contentPane, DIAGNOSIS_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
					}else {	
						
						if(ValidationUtils.isValidTajNumber(tajNumber.getText())){
							try {
								xmlHandler2.Add_Diagnosis_To_Patient_Medical_History(tajNumber.getText(), diagnosis.getText(), res, expertOpinion.getText());
								JOptionPane.showMessageDialog(contentPane, "Sikeresen rögzítette a(z) " + diagnosis.getText() + " nevű diagnózist a páciens kórtörténetéhez!", "Adatbevitel", JOptionPane.INFORMATION_MESSAGE);
								clearInputFields();
							} catch (TransformerException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		addDiagnosis.setFont(new Font("Segoe UI", Font.BOLD, 15));
		addDiagnosis.setBounds(284, 408, 108, 36);
		panel.add(addDiagnosis);

		diagnosis = new JTextField();
		diagnosis.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		diagnosis.setColumns(10);
		diagnosis.setBounds(145, 75, 247, 30);
		panel.add(diagnosis);

		JButton btnNewButton = new JButton(RANDOM_DISEASE_NAME);
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.addActionListener(e -> {
			Faker f = new Faker(new Locale("hu_HU"));

			diagnosis.setText(f.medical().diseaseName());
			
			expertOpinion.setText(f.lorem().paragraph(2));

		});
		btnNewButton.setBounds(10, 408, 247, 36);
		panel.add(btnNewButton);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, DIAGNOSIS_DELETE_LABEL, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(456, 25, 485,455);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label_tajNumber2 = new JLabel(TAJ_NUMBER_LABEL);
		label_tajNumber2.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_tajNumber2.setBounds(10, 26, 120, 20);
		panel_1.add(label_tajNumber2);
		
		tajNumber2 = new JTextField();
		tajNumber2.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tajNumber2.setColumns(10);
		tajNumber2.setBounds(110, 22, 247, 30);
		
		// When you type in the TAJ number, and it exists then this popup opens
		tajNumber2.getDocument().addDocumentListener(new DocumentListener() {
		    @Override
		    public void insertUpdate(DocumentEvent e) {
		        if(xmlHandler2.Contains_Patient(tajNumber2.getText())){
		        	try {
		        		Patient p = xmlHandler2.Get_Patient(tajNumber2.getText());
		        		JOptionPane.showMessageDialog(panel_1, "TAJ szám találat: " + p.getLastName() + " " + p.getFirstName(), "Találat", JOptionPane.INFORMATION_MESSAGE);
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
		        }
		    }

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		
		panel_1.add(tajNumber2);
		
		JLabel label_medicalHistory = new JLabel(MEDICAL_HISTORY_2);
		label_medicalHistory.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_medicalHistory.setBounds(10, 89, 97, 20);
		panel_1.add(label_medicalHistory);
		
		DefaultTableModel model = new DefaultTableModel();
		
		model.addColumn("Diagnózis");
		model.addColumn("Rögzítés dátuma");
		model.addColumn("Szakértői vélemény");
		
		
		comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		comboBox.setBounds(140, 410, 171, 34);
		
		panel_1.add(comboBox);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 120, 465, 279);
		panel_1.add(scrollPane_1);
		
		medicalHistoryPanel = new JTextPane();
		medicalHistoryPanel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		medicalHistoryPanel.setEditable(false);
		medicalHistoryPanel.setContentType("text/html");
		scrollPane_1.setViewportView(medicalHistoryPanel);
	
		
		// Search patient button
		JButton searchPatient = new JButton(SEARCH_PATIENT_BTN);
		searchPatient.addActionListener(e -> {
			if(tajNumber2.getText().length() != 9) {
				JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_TAJ_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}
			else if(!xmlHandler2.Contains_Patient(tajNumber2.getText())){
				JOptionPane.showMessageDialog(contentPane, PATIENT_NOT_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}else if(xmlHandler2.Contains_Any_Diagnosis(tajNumber2.getText())){
				JOptionPane.showMessageDialog(contentPane, DIAGNOSIS_NOT_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}else {

				ArrayList<Diagnosis> arrayList = xmlHandler2.List_Medical_History_Of_Patient(tajNumber2.getText());

				Patient p;
				try {
					p = xmlHandler2.Get_Patient(tajNumber2.getText());
					
					String fi = "============================"
							+ "<h1> Kórtörténet jelentés </h1>" 
 							+ "<p><b>Név</b>: " + p.getLastName() + " " + p.getFirstName() + "</p>" +
							"<p><b>Születési dátum</b>: " + p.getBornDate() + "</p>" +
							"============================";
					
					for(Diagnosis diagnosis : arrayList) {
						fi += "<div style='margin-bottom: 10px;'><b>Szakértői vélemény</b> ( " + diagnosis.getDate() + "):" 
					+ "<p color='red'>Diagnózis: " + diagnosis.getName() + "</p>" 
					+ "<p><b>Leírat:</b>" + diagnosis.getExpertOpinion() + "</p> <hr size='2'> </div>";
					}
					
					
					medicalHistoryPanel.setText(fi);
					
				} catch (TransformerException e1) {
					e1.printStackTrace();
				}

				if(comboBox.getItemCount() != 0) {
					comboBox.removeAllItems();
					for(Diagnosis diagnosis : arrayList) {
						comboBox.addItem(diagnosis.getDate());
					}
				}else {
					for(Diagnosis diagnosis : arrayList) {
						comboBox.addItem(diagnosis.getDate());
					}
				}
			}
		});
		searchPatient.setFont(new Font("Segoe UI", Font.BOLD, 15));
		searchPatient.setBounds(268, 63, 89, 30);
		panel_1.add(searchPatient);

		// Delete patient button
		JButton btnNewButton_1 = new JButton(DELETE_LABEL);
		btnNewButton_1.addActionListener(e -> {

			if(comboBox.getItemCount() == 0) {
				JOptionPane.showMessageDialog(contentPane, ERROR_NO_SEARCH, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}
			else {
				String combo = Objects.requireNonNull(comboBox.getSelectedItem()).toString();

				if(xmlHandler2.Contains_Diagnosis_In_Specific_Date(tajNumber2.getText(), combo)){
					try {
						xmlHandler2.Delete_Patient_Diagnosis(tajNumber2.getText(), combo);
						ArrayList<Diagnosis> arrayList = xmlHandler2.List_Medical_History_Of_Patient(tajNumber2.getText());

						Patient p;
				
							p = xmlHandler2.Get_Patient(tajNumber2.getText());
							
							String fi = "============================"
									+ "<h1> Kórtörténet jelentés </h1>" 
		 							+ "<p><b>Név</b>: " + p.getLastName() + " " + p.getFirstName() + "</p>" +
									"<p><b>Születési dátum</b>: " + p.getBornDate() + "</p>" +
									"============================";
							
							for(Diagnosis diagnosis : arrayList) {
								fi += "<div style='margin-bottom: 10px;'><b>Szakértői vélemény</b> ( " + diagnosis.getDate() + "):" 
							+ "<p color='red'>Diagnózis: " + diagnosis.getName() + "</p>" 
							+ "<p><b>Leírat:</b>" + diagnosis.getExpertOpinion() + "</p> <hr size='2'> </div>";
							}
							
							
							medicalHistoryPanel.setText(fi);
		
						comboBox.removeItem(combo);
						JOptionPane.showMessageDialog(contentPane, DIAGNOSIS_DELETED , "Adatbevitel", JOptionPane.INFORMATION_MESSAGE);
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton_1.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton_1.setBounds(349, 410, 126, 35);
		panel_1.add(btnNewButton_1);

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
		diagnosis.setText("");
		expertOpinion.setText("");
		diagnosis_date.setDateTimePermissive(null);
		tajNumber2.setText("");
		comboBox.removeAllItems();
		medicalHistoryPanel.setText("");
	}
}
