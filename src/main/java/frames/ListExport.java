package frames;

import static utils.LanguageUtils.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import utils.XmlHandler2;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerException;

import objects.Patient;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;

/**
 * This is a JFrame where you can export the patients' information.
 */
public class ListExport extends JFrame {

	private final JPanel panel;

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public ListExport(XmlHandler2 xmlHandler2) throws IOException {
		setTitle(LIST_EXPORT);
		setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 410, 551);
		setResizable(false);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new TitledBorder(null, LIST_EXPORT, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 11, 380, 480);
		contentPane.add(panel);

		JScrollPane kortortenet = new JScrollPane();
		kortortenet.setBounds(10, 55, 359, 335);
		panel.add(kortortenet);
		
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Vezetéknév");
		model.addColumn("Keresztnév");
		model.addColumn("TAJ szám");

		JTable table = new JTable();
		table.setDefaultEditor(Object.class, null);
		table.setModel(model);
		kortortenet.setViewportView(table);

		// ComponentListener always fires when the user opens the window
		addComponentListener(new ComponentAdapter(){
			@Override
			public void componentShown(ComponentEvent e) {
				try {
					if(xmlHandler2.Contains_Any_Patient()) {
						ArrayList<Patient> patients = xmlHandler2.Get_All_Patients();

						model.setRowCount(0);

						for(Patient p : patients) {
							model.addRow(new Object[] {
									p.getLastName(),
									p.getFirstName(),
									p.getTajNumber()
							});
						}
						JOptionPane.showMessageDialog(panel, PATIENT_LIST_LOADED, "Információ", JOptionPane.INFORMATION_MESSAGE);
					}else {
						JOptionPane.showMessageDialog(panel, NO_PATIENT_LIST, "Hiba", JOptionPane.ERROR_MESSAGE);
					}
				} catch (TransformerException e1) {
					e1.printStackTrace();
				}
			}
		});

		JLabel lblPciensLista = new JLabel(PATIENT_LIST);
		lblPciensLista.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblPciensLista.setBounds(10, 24, 114, 20);
		panel.add(lblPciensLista);

		// Export to txt button
		JButton btnNewButton = new JButton(PATIENT_EXPORT_BTN);
		btnNewButton.addActionListener(e -> {
			if(!xmlHandler2.Contains_Any_Patient()) {
				  JOptionPane.showMessageDialog(panel, NO_PATIENT_LIST, "Hiba", JOptionPane.ERROR_MESSAGE);
			}else {
				try(PrintWriter pw = new PrintWriter(new FileWriter("./patients_export.txt"))){
					ArrayList<Patient> patients = xmlHandler2.Get_All_Patients();

					for(Patient patient : patients) {
						pw.println(patient.getTajNumber());
						pw.println(patient.getFirstName());
						pw.println(patient.getLastName());
						pw.println(patient.getBornDate());
						pw.println(patient.getZipCode());
						pw.println(patient.getCity());
						pw.println(patient.getStreet());
						pw.println(patient.getHouseNumber());
						pw.println(patient.getMotherName());
						pw.println(patient.getFatherName());
						pw.println(patient.getBornPlace());
						pw.println(patient.getPhoneNumber());
						pw.println(patient.getGender());
						pw.println();
					}

					  JOptionPane.showMessageDialog(panel, EXPORT_SUCCESS, "Információ", JOptionPane.INFORMATION_MESSAGE);

				}catch (IOException | TransformerException e2) {
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(233, 401, 136, 38);
		panel.add(btnNewButton);
	}
}
