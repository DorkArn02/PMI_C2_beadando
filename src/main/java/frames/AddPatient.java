package frames;

import static utils.LanguageUtils.*;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
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
import java.text.SimpleDateFormat;
import java.awt.event.ActionEvent;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;

import com.github.javafaker.Faker;
import com.toedter.calendar.JDateChooser;

import objects.BloodGroup;
import objects.Patient;
import utils.ValidationUtils;
import utils.XmlHandler;
import javax.swing.JComboBox;

/**
 * This is a JFrame where you can add new patient to the database
 */
public class AddPatient extends JFrame {
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
	private final JRadioButton genderFemale;
	private final JDateChooser bornDate;

	/**
	 *
	 * @param xmlHandler2 Getting the Xml Handler Class from MainApp
	 */
	public AddPatient(XmlHandler xmlHandler2) throws IOException {
		setTitle(ADD_PATIENT_TITLE);
		setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/favicon-32x32.png"))));
	    ButtonGroup btn = new ButtonGroup();
		
		setBounds(100, 100, 580, 481);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setResizable(false);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, ADD_PATIENT_TITLE, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(21, 11, 532, 420);
		contentPane.add(panel);
		panel.setLayout(null);

		JComboBox<String> comboBox = new JComboBox<>();
		comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		comboBox.setBounds(382, 198, 120, 30);

		for(BloodGroup bloodGroup : BloodGroup.values()) {
			comboBox.addItem(bloodGroup.getBloodGroup());
		}
		
		panel.add(comboBox);
		
		// Add patient button
		JButton addPatient = new JButton(ADD_PATIENT_BTN);
		addPatient.setFont(new Font("Segoe UI", Font.BOLD, 15));
		addPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!ValidationUtils.isPatientInputValid(lastName, firstName, tajNumber, zipCode, city, street, houseNumber, phoneNumber, motherName, fatherName, bornPlace)) {
					JOptionPane.showMessageDialog(contentPane, ERROR_EMPTY_FIELDS, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}else if(!ValidationUtils.isJDateChooserValid(bornDate)) {
					JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_DATE_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}else if(!ValidationUtils.isValidTajNumber(tajNumber.getText())) {
					JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_TAJ_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
				}
				else{
					if(!ValidationUtils.isValidZipCode(zipCode.getText())){
						JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_ZIP_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
					}else if(!ValidationUtils.isValidHouseNumber(houseNumber.getText())){
						JOptionPane.showMessageDialog(contentPane, ERROR_WRONG_HOUSE_NUMBER_FORMAT, "Adatbevitel", JOptionPane.ERROR_MESSAGE);
					}else{
						Patient patient = new Patient();
						patient.setLastName(lastName.getText());
						patient.setFirstName(firstName.getText());
						patient.setStreet(street.getText());
						patient.setMotherName(motherName.getText());
						patient.setFatherName(fatherName.getText());

						if(genderFemale.isSelected()) {
							patient.setGender("nő");
						}else {
							patient.setGender("férfi");
						}

						patient.setHouseNumber(houseNumber.getText());
						patient.setZipCode(zipCode.getText());

						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

						patient.setBornDate(simpleDateFormat.format(bornDate.getDate()));
						patient.setBornPlace(bornPlace.getText());
						patient.setTajNumber(tajNumber.getText());
						patient.setPhoneNumber(phoneNumber.getText());
						patient.setCity(city.getText());
						patient.setBloodGroup(Objects.requireNonNull(comboBox.getSelectedItem()).toString());
												
						if(!xmlHandler2.Contains_Patient(tajNumber.getText())) {
							try {
								xmlHandler2.Add_Patient(patient);
								JOptionPane.showMessageDialog(contentPane, PATIENT_ADDED_SUCCESSFULLY, "Adatbevitel", JOptionPane.INFORMATION_MESSAGE);
								clearInputFields();
							} catch (TransformerException e1) {
								e1.printStackTrace();
							}
						}else {
							JOptionPane.showMessageDialog(panel, CONTAINS_PATIENT, "Adatbevitel",  JOptionPane.ERROR_MESSAGE);
						}
					}
				}
			}
		});
		addPatient.setBounds(388, 319, 134, 50);
		panel.add(addPatient);
		
		lastName = new JTextField();
		lastName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		lastName.setBounds(141, 27, 120, 30);
		panel.add(lastName);
		lastName.setColumns(10);
		
		JLabel label_lastName = new JLabel(LAST_NAME_LABEL);
		label_lastName.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_lastName.setBounds(10, 29, 120, 20);
		panel.add(label_lastName);
		
		JLabel label_firstName = new JLabel(FIRST_NAME_LABEL);
		label_firstName.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_firstName.setBounds(272, 27, 120, 20);
		panel.add(label_firstName);
		
		firstName = new JTextField();
		firstName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		firstName.setColumns(10);
		firstName.setBounds(382, 24, 120, 30);
		panel.add(firstName);
		
		JLabel label_bornDate = new JLabel(BORN_DATE_LABEL);
		label_bornDate.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_bornDate.setBounds(10, 68, 120, 20);
		panel.add(label_bornDate);
		
		JLabel label_tajNumber = new JLabel(TAJ_NUMBER_LABEL);
		label_tajNumber.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_tajNumber.setBounds(10, 114, 120, 20);
		panel.add(label_tajNumber);
		
		tajNumber = new JTextField();
		tajNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		tajNumber.setColumns(10);
		tajNumber.setBounds(141, 104, 120, 30);
		panel.add(tajNumber);
		
		JLabel label_zipCode = new JLabel(ZIP_CODE_LABEL);
		label_zipCode.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_zipCode.setBounds(10, 155, 120, 20);
		panel.add(label_zipCode);
		
		zipCode = new JTextField();
		zipCode.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		zipCode.setColumns(10);
		zipCode.setBounds(141, 145, 120, 30);
		panel.add(zipCode);
		
		JLabel label_city = new JLabel(CITY_LABEL);
		label_city.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_city.setBounds(272, 114, 120, 20);
		panel.add(label_city);
		
		city = new JTextField();
		city.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		city.setColumns(10);
		city.setBounds(382, 110, 120, 30);
		panel.add(city);
		
		JLabel lblUtca = new JLabel(STREET_LABEL);
		lblUtca.setFont(new Font("Segoe UI", Font.BOLD, 15));
		lblUtca.setBounds(10, 196, 120, 20);
		panel.add(lblUtca);
		
		street = new JTextField();
		street.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		street.setColumns(10);
		street.setBounds(141, 186, 120, 30);
		panel.add(street);
		
		JLabel label_gender = new JLabel(GENDER_LABEL);
		label_gender.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_gender.setBounds(10, 348, 84, 21);
		panel.add(label_gender);

		JRadioButton genderMale = new JRadioButton("Férfi");
		genderMale.setFont(new Font("Segoe UI", Font.BOLD, 15));
		genderMale.setBounds(74, 346, 109, 23);
		
		genderMale.setSelected(true);
		
		genderFemale = new JRadioButton("Nő");
		genderFemale.setFont(new Font("Segoe UI", Font.BOLD, 15));
		genderFemale.setBounds(185, 346, 109, 23);
		
		btn.add(genderMale);
		btn.add(genderFemale);
		panel.add(genderMale);
		panel.add(genderFemale);
		
		JLabel label_houseNumber = new JLabel(HOUSE_NUMBER_LABEL);
		label_houseNumber.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_houseNumber.setBounds(272, 155, 120, 20);
		panel.add(label_houseNumber);
		
		houseNumber = new JTextField();
		houseNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		houseNumber.setColumns(10);
		houseNumber.setBounds(382, 152, 120, 30);
		panel.add(houseNumber);
		
		JLabel label_phoneNumber = new JLabel(PHONE_NUMBER_LABEL);
		label_phoneNumber.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_phoneNumber.setBounds(10, 237, 120, 20);
		panel.add(label_phoneNumber);
		
		phoneNumber = new JTextField();
		phoneNumber.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		phoneNumber.setColumns(10);
		phoneNumber.setBounds(141, 227, 120, 30);
		panel.add(phoneNumber);
		
		JLabel label_motherName = new JLabel(MOTHER_NAME_LABEL);
		label_motherName.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_motherName.setBounds(10, 278, 120, 20);
		panel.add(label_motherName);
		
		motherName = new JTextField();
		motherName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		motherName.setColumns(10);
		motherName.setBounds(141, 268, 120, 30);
		panel.add(motherName);
		
		JLabel label_fatherName = new JLabel(FATHER_NAME_LABEL);
		label_fatherName.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_fatherName.setBounds(10, 317, 120, 20);
		panel.add(label_fatherName);
		
		fatherName = new JTextField();
		fatherName.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		fatherName.setColumns(10);
		fatherName.setBounds(141, 309, 120, 30);
		panel.add(fatherName);
		
		JLabel label_bornPlace = new JLabel(BORN_PLACE_LABEL);
		label_bornPlace.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_bornPlace.setBounds(272, 68, 120, 20);
		panel.add(label_bornPlace);
		
		bornPlace = new JTextField();
		bornPlace.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		bornPlace.setColumns(10);
		bornPlace.setBounds(382, 65, 120, 30);
		panel.add(bornPlace);
		
		bornDate = new JDateChooser();
		bornDate.setDateFormatString("yyyy/MM/dd");
		bornDate.setBounds(141, 68, 120, 30);
		panel.add(bornDate);

		// Generate random data button
		JButton btnNewButton = new JButton(RANDOM_PERSON_DETAILS);
		btnNewButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
		btnNewButton.addActionListener(e -> {
			Faker f = new Faker(new Locale("hu_HU"));			
			firstName.setText(f.name().firstName());
			lastName.setText(f.name().lastName());
			street.setText(f.address().streetName());
			houseNumber.setText(f.address().streetAddressNumber());
			zipCode.setText(f.address().zipCode());
			city.setText(f.address().city());
			motherName.setText(f.name().fullName());
			fatherName.setText(f.name().fullName());
			bornPlace.setText(f.address().city());
			phoneNumber.setText(f.phoneNumber().cellPhone());
			bornDate.setDate(f.date().birthday());
			tajNumber.setText(f.number().digits(9));

			Random random = new Random();

			if(random.nextBoolean())
			{
				genderMale.setSelected(true);
			}else{
				genderFemale.setSelected(true);
			}

			JOptionPane.showMessageDialog(this, RANDOM_DATA_SUCCESS);

		});
		btnNewButton.setBounds(272, 380, 250, 29);
		panel.add(btnNewButton);
		

		
		JLabel label_BloodGroup = new JLabel("Vércsoport");
		label_BloodGroup.setFont(new Font("Segoe UI", Font.BOLD, 15));
		label_BloodGroup.setBounds(272, 202, 120, 20);
		panel.add(label_BloodGroup);

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
		fatherName.setText("");
		motherName.setText("");
		phoneNumber.setText("");
		tajNumber.setText("");
		zipCode.setText("");
		street.setText("");
		houseNumber.setText("");
		city.setText("");
	}
}
