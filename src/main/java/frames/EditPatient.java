package frames;

import static utils.LanguageUtils.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.xml.transform.TransformerException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.Objects;

import com.toedter.calendar.JDateChooser;
import objects.Patient;
import utils.ValidationUtils;
import utils.XmlHandler2;

/**
 * This is a JFrame where you can edit the patients' information
 */
public class EditPatient extends JFrame {
	private final JPanel contentPane;
	private final JTextField lastName;
	private final JTextField firstName;
	private final JTextField tajNumber;
	private final JTextField zipCode;
	private final JTextField city;
	private final JTextField street;
	private final JTextField houseNumber;
	private final JTextField phoneNumber;
	private final JTextField motherName;
	private final JTextField fatherName;
	private final JTextField bornPlace;
	private final JRadioButton genderMale;
	private final JRadioButton genderFemale;
	private final JDateChooser bornDate;

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public EditPatient(XmlHandler2 xmlHandler2) throws IOException {
		setTitle(EDIT_PATIENT_TITLE);
		
		ButtonGroup btn = new ButtonGroup();
		setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
		setBounds(100, 100, 580, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, EDIT_PATIENT_TITLE, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(21, 11, 532, 389);
		contentPane.add(panel);
		panel.setLayout(null);

		// Edit patient button
		JButton addPatient = new JButton(EDIT_PATIENT_BTN);
		addPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if(!ValidationUtils.isPatientInputValid(lastName, firstName, tajNumber, zipCode, city, street, houseNumber, phoneNumber, motherName, fatherName, bornPlace)) {
					JOptionPane.showMessageDialog(contentPane, ERROR_EMPTY_FIELDS, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}else if(!ValidationUtils.isJDateChooserValid(bornDate)) {
					JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_DATE_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}else if(!ValidationUtils.isValidTajNumber(tajNumber.getText())) {
					JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_TAJ_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}
				else {
					if (!ValidationUtils.isValidZipCode(zipCode.getText())) {
						JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_ZIP_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
					} else if (!ValidationUtils.isValidHouseNumber(houseNumber.getText())) {
						JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_HOUSE_NUMBER_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
					} else {
						if (!xmlHandler2.Contains_Patient(tajNumber.getText())) {
							JOptionPane.showMessageDialog(contentPane, PATIENT_NOT_EXISTS, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
						} else {
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

							String d = simpleDateFormat.format(bornDate.getDate());

							try {
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "firstName", firstName.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "lastName", lastName.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "bornDate", d);
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "zipCode", zipCode.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "city", city.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "street", street.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "houseNumber", houseNumber.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "motherName", motherName.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "fatherName", fatherName.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "bornPlace", bornPlace.getText());
								xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "phoneNumber", phoneNumber.getText());
								if (genderMale.isSelected())
									xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "gender", "férfi");
								else {
									xmlHandler2.Edit_Patient_Detail(tajNumber.getText(), "gender", "nő");
								}
								clearInputFields();
								JOptionPane.showMessageDialog(contentPane, PATIENT_EDIT_SUCESS, "Adatbevitel", JOptionPane.INFORMATION_MESSAGE);

							} catch (TransformerException e1) {
								e1.printStackTrace();
							}
						}
					}
				}
			}
		});
		addPatient.setBounds(400, 292, 122, 40);
		panel.add(addPatient);
		
		lastName = new JTextField();
		lastName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lastName.setBounds(141, 27, 120, 25);
		panel.add(lastName);
		lastName.setColumns(10);
		
		JLabel label_lastName = new JLabel(LAST_NAME_LABEL);
		label_lastName.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_lastName.setBounds(10, 29, 120, 20);
		panel.add(label_lastName);
		
		JLabel label_firstName = new JLabel(FIRST_NAME_LABEL);
		label_firstName.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_firstName.setBounds(272, 27, 120, 20);
		panel.add(label_firstName);
		
		firstName = new JTextField();
		firstName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		firstName.setColumns(10);
		firstName.setBounds(382, 24, 120, 25);
		panel.add(firstName);
		
		JLabel label_bornDate = new JLabel(BORN_DATE_LABEL);
		label_bornDate.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_bornDate.setBounds(10, 60, 120, 20);
		panel.add(label_bornDate);
		
		JLabel label_tajNumber = new JLabel(TAJ_NUMBER_LABEL);
		label_tajNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_tajNumber.setBounds(10, 91, 120, 20);
		panel.add(label_tajNumber);
		
		tajNumber = new JTextField();
		tajNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		tajNumber.setColumns(10);
		tajNumber.setBounds(141, 89, 120, 25);

		// When you type in the TAJ number, and it exists then this popup opens
		tajNumber.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				if(xmlHandler2.Contains_Patient(tajNumber.getText())){
					try {
						Patient p = xmlHandler2.Get_Patient(tajNumber.getText());
						firstName.setText(p.getFirstName());
						lastName.setText(p.getLastName());
						bornPlace.setText(p.getBornPlace());
						motherName.setText(p.getMotherName());
						fatherName.setText(p.getFatherName());
						street.setText(p.getStreet());
						houseNumber.setText(p.getHouseNumber());
						genderMale.setEnabled(p.getGender().equals("férfi"));
						genderFemale.setEnabled(p.getGender().equals("nő"));
						zipCode.setText(p.getZipCode());
						phoneNumber.setText(p.getPhoneNumber());
						city.setText(p.getCity());
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
						bornDate.setDate(formatter.parse(p.getBornDate()));
						JOptionPane.showMessageDialog(panel, "TAJ szám találat: " + p.getLastName() + " " + p.getFirstName(), "Találat", JOptionPane.INFORMATION_MESSAGE);

					} catch (TransformerException | ParseException e1) {
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
		
		JLabel label_zipCode = new JLabel(ZIP_CODE_LABEL);
		label_zipCode.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_zipCode.setBounds(10, 123, 120, 20);
		panel.add(label_zipCode);
		
		zipCode = new JTextField();
		zipCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		zipCode.setColumns(10);
		zipCode.setBounds(142, 121, 120, 25);
		panel.add(zipCode);
		
		JLabel label_city = new JLabel(CITY_LABEL);
		label_city.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_city.setBounds(272, 113, 120, 20);
		panel.add(label_city);
		
		city = new JTextField();
		city.setFont(new Font("Tahoma", Font.PLAIN, 15));
		city.setColumns(10);
		city.setBounds(382, 110, 120, 25);
		panel.add(city);
		
		JLabel lblUtca = new JLabel(STREET_LABEL);
		lblUtca.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblUtca.setBounds(10, 154, 120, 20);
		panel.add(lblUtca);
		
		street = new JTextField();
		street.setFont(new Font("Tahoma", Font.PLAIN, 15));
		street.setColumns(10);
		street.setBounds(142, 152, 120, 25);
		panel.add(street);
		
		JLabel label_gender = new JLabel(GENDER_LABEL);
		label_gender.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_gender.setBounds(10, 320, 84, 21);
		panel.add(label_gender);
		
		genderMale = new JRadioButton("Férfi");
		genderMale.setFont(new Font("Tahoma", Font.BOLD, 15));
		genderMale.setBounds(75, 319, 109, 23);
		
		genderMale.setSelected(true);

		genderFemale = new JRadioButton("Nő");
		genderFemale.setFont(new Font("Tahoma", Font.BOLD, 15));
		genderFemale.setBounds(186, 319, 109, 23);
		
		btn.add(genderMale);
		btn.add(genderFemale);
		panel.add(genderMale);
		panel.add(genderFemale);
		
		JLabel label_houseNumber = new JLabel(HOUSE_NUMBER_LABEL);
		label_houseNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_houseNumber.setBounds(272, 169, 120, 20);
		panel.add(label_houseNumber);
		
		houseNumber = new JTextField();
		houseNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		houseNumber.setColumns(10);
		houseNumber.setBounds(382, 163, 120, 25);
		panel.add(houseNumber);
		
		JLabel label_phoneNumber = new JLabel(PHONE_NUMBER_LABEL);
		label_phoneNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_phoneNumber.setBounds(10, 185, 120, 20);
		panel.add(label_phoneNumber);
		
		phoneNumber = new JTextField();
		phoneNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
		phoneNumber.setColumns(10);
		phoneNumber.setBounds(141, 183, 120, 25);
		panel.add(phoneNumber);
		
		JLabel label_motherName = new JLabel(MOTHER_NAME_LABEL);
		label_motherName.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_motherName.setBounds(10, 216, 120, 20);
		panel.add(label_motherName);
		
		motherName = new JTextField();
		motherName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		motherName.setColumns(10);
		motherName.setBounds(141, 214, 120, 25);
		panel.add(motherName);
		
		JLabel label_fatherName = new JLabel(FATHER_NAME_LABEL);
		label_fatherName.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_fatherName.setBounds(10, 247, 120, 20);
		panel.add(label_fatherName);
		
		fatherName = new JTextField();
		fatherName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fatherName.setColumns(10);
		fatherName.setBounds(141, 247, 120, 25);
		panel.add(fatherName);
		
		JLabel label_bornPlace = new JLabel(BORN_PLACE_LABEL);
		label_bornPlace.setFont(new Font("Tahoma", Font.BOLD, 15));
		label_bornPlace.setBounds(272, 58, 120, 20);
		panel.add(label_bornPlace);
		
		bornPlace = new JTextField();
		bornPlace.setFont(new Font("Tahoma", Font.PLAIN, 15));
		bornPlace.setColumns(10);
		bornPlace.setBounds(382, 58, 120, 25);
		panel.add(bornPlace);
		
		bornDate = new JDateChooser();
		bornDate.setDateFormatString("yyyy/MM/dd");
		bornDate.setBounds(141, 60, 120, 25);
		panel.add(bornDate);

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
		firstName.setText("");
		lastName.setText("");
		bornDate.setDate(null);
		bornPlace.setText("");
		tajNumber.setText("");
		zipCode.setText("");
		city.setText("");
		houseNumber.setText("");
		phoneNumber.setText("");
		motherName.setText("");
		fatherName.setText("");
		street.setText("");
	}

}
