package utils;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;

public class ValidationUtils {
	/**
	 * Returns true if the TAJ number is valid.
	 * @param tajNumber Patient's TAJ number
	 * @return boolean
	 */
	public static boolean isValidTajNumber(String tajNumber) {
		if(tajNumber.length() != 9)
			return false;
		try {
			Integer.parseInt(tajNumber);
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the zip code is valid
	 * @param zipCode Patient's zip code
	 * @return boolean
	 */
	public static boolean isValidZipCode(String zipCode){
		if(zipCode.length() != 4){
			return false;
		}
		try {
			Integer.parseInt(zipCode);
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the house number is valid
	 * @param houseNumber Patient's house number
	 * @return boolean
	 */
	public static boolean isValidHouseNumber(String houseNumber){
		try {
			Integer.parseInt(houseNumber);
		}catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if the jDateChooser component is valid
	 * @param jDateChooser Date chooser component
	 * @return boolean
	 */
	public static boolean isJDateChooserValid(JDateChooser jDateChooser){
		return jDateChooser.getDate() != null;
	}

	/**
	 * Returns true if all input fields are filled.
	 * @param lastName Patient's last name
	 * @param firstName Patient's first name
	 * @param tajNumber Patient's TAJ number
	 * @param zipCode Patient's zip code
	 * @param city Patient's home city
	 * @param street Patient's street
	 * @param houseNumber Patient's house number
	 * @param phoneNumber Patient's phone number
	 * @param motherName Patient's mother name
	 * @param fatherName Patient's father name
	 * @param bornPlace Patient's born place
	 * @return boolean
	 */
	public static boolean isPatientInputValid(JTextField lastName, JTextField firstName, JTextField tajNumber, JTextField zipCode, JTextField city,
											  JTextField street, JTextField houseNumber, JTextField phoneNumber, JTextField motherName,
											  JTextField fatherName, JTextField bornPlace) {
		String vezeteknevString = lastName.getText();
		String keresztnevString = firstName.getText();
		String taj_szamString = tajNumber.getText();
		String iranyito_szamString = zipCode.getText();
		String telepuleString = city.getText();
		String utcaString = street.getText();
		String hazszamString = houseNumber.getText();
		String telefonszamString = phoneNumber.getText();
		String anyja_neveString = motherName.getText();
		String apja_neveString = fatherName.getText();
		String szuletesi_helyString = bornPlace.getText();

		return vezeteknevString.length() != 0 && keresztnevString.length() != 0
				&& taj_szamString.length() != 0
				&& iranyito_szamString.length() != 0
				&& telepuleString.length() != 0
				&& utcaString.length() != 0
				&& hazszamString.length() != 0
				&& telefonszamString.length() != 0
				&& anyja_neveString.length() != 0
				&& apja_neveString.length() != 0
				&& szuletesi_helyString.length() != 0;
	}

}
