package frames;

import static utils.LanguageUtils.*;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.xml.transform.TransformerException;

import objects.Patient;
import utils.ValidationUtils;
import utils.XmlHandler;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

/**
 * This is a JFrame where you can delete the selected patient
 */
public class DeletePatient extends JFrame {
	private JTextField tajNumber;

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public DeletePatient(XmlHandler xmlHandler2) throws IOException {
		setTitle(DELETE_PATIENT_TITLE);
		setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
	    setBounds(100, 100, 354, 164);
	    setResizable(false);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, DELETE_PATIENT_TITLE, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(21, 11, 307, 109);
		getContentPane().add(panel);
		panel.setLayout(null);

		// Delete patient button
		JButton deleteButton = new JButton(DELETE_LABEL);
		deleteButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		deleteButton.addActionListener(e -> {
			if(ValidationUtils.isValidTajNumber(tajNumber.getText())) {
				if(!xmlHandler2.Contains_Patient(tajNumber.getText())) {
					JOptionPane.showMessageDialog(panel, PATIENT_NOT_EXISTS , "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}
				else {
					try {
						Patient patient = xmlHandler2.Get_Patient(tajNumber.getText());
						int confirmed = JOptionPane.showConfirmDialog(null,
								CONFIRM_DELETE_PATIENT + " ("
								+ patient.getLastName() + " " + patient.getFirstName() + ")", CONFIRM_LABEL,
								JOptionPane.YES_NO_OPTION);

							if (confirmed == JOptionPane.YES_OPTION) {
								xmlHandler2.Delete_Patient(tajNumber.getText());
								clearInputFields();
								JOptionPane.showMessageDialog(panel, patient.getLastName() + " " + patient.getFirstName() + " nevű páciens sikeresen törölve az adatbázisból!");
							}
					} catch (TransformerException e1) {
						e1.printStackTrace();
					}
				}

			}else {
				JOptionPane.showMessageDialog(panel, ERROR_WRONG_TAJ_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
			}
		});
		deleteButton.setBounds(169, 59, 118, 38);
		panel.add(deleteButton);
		
		JLabel label_tajNumber = new JLabel(TAJ_NUMBER_LABEL);
		label_tajNumber.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_tajNumber.setBounds(10, 23, 99, 19);
		panel.add(label_tajNumber);
		
		tajNumber = new JTextField();
		tajNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tajNumber.setColumns(10);
		tajNumber.setBounds(118, 20, 169, 30);
		panel.add(tajNumber);

		// Window close confirm dialog
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        CONFIRM_EXIT_DATA, EXIT_LABEL,
			        JOptionPane.YES_NO_OPTION);
			    if (confirmed == JOptionPane.YES_OPTION) {
			      dispose();
			      clearInputFields();
			    }else {
			    	setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			    }
			  }
			});
		
	}
	
	private void clearInputFields() {
		tajNumber.setText("");
	}

	
}
