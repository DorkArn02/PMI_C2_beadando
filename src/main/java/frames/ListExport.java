package frames;

import static utils.LanguageUtils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import utils.ExportUtils;
import utils.XmlHandler;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.TransformerException;

import objects.Patient;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * This is a JFrame where you can export the patients' information.
 */
public class ListExport extends JFrame {

	private final JPanel panel;

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public ListExport(XmlHandler xmlHandler2) throws IOException {
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
		model.addColumn(LAST_NAME_LABEL);
		model.addColumn(FIRST_NAME_LABEL);
		model.addColumn(TAJ_NUMBER_LABEL);

		JTable table = new JTable();
		table.setFont(new Font("Segoe UI", Font.BOLD, 15));
		table.setDefaultEditor(Object.class, null);

		table.getActionMap().put("copy", new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				String cellValue = table.getModel().getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString();
				StringSelection stringSelection = new StringSelection(cellValue);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, stringSelection);
				JOptionPane.showMessageDialog(panel, "A kiválaszott cella tartalma a vágólapra lett másolva.");
			}
		});

		table.setModel(model);
		table.setRowSelectionAllowed(true);
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
		lblPciensLista.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblPciensLista.setBounds(10, 24, 114, 20);
		panel.add(lblPciensLista);

		// Export to txt button
		JButton btnNewButton = new JButton(PATIENT_EXPORT_BTN);
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.addActionListener(e -> {
			if(!xmlHandler2.Contains_Any_Patient()) {
				  JOptionPane.showMessageDialog(panel, NO_PATIENT_LIST, "Hiba", JOptionPane.ERROR_MESSAGE);
			}else {
				ExportUtils exportUtils = new ExportUtils(xmlHandler2);

				try {
					exportUtils.exportPatientPersonalData();
				} catch (TransformerException ex) {
					ex.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(194, 401, 175, 54);
		panel.add(btnNewButton);
	}
}
