package objects;

public class Patient {
	private String firstName;
	private String lastName;
	private String bornDate;
	private String tajNumber;
	private String zipCode;
	private String city;
	private String street;
	private String houseNumber;
	private String motherName;
	private String fatherName;
	private String bornPlace;
	private String phoneNumber;
	private String gender;
	private String bloodGroup;
	
	public Patient() {
		
	}
	
	/**
	 * Patient object
	 * @param firstName First name of patient
	 * @param lastName Last name of patient
	 * @param bornDate Born date of patient
	 * @param tajNumber Primary key in xml file, <patient tajNumber="xxxxxxxxx"></patient>
	 * @param zipCode Zip code of patient
	 * @param city City of patient
	 * @param street Street of patient
	 * @param houseNumber House number of patient
	 * @param motherName Patient's mother
	 * @param fatherName Patient's father
	 * @param bornPlace Born place of patient
	 * @param phoneNumber Phone number of patient
	 * @param gender Gender of patient
	 * @param bloodGroup Blood group of patient
	 */
	public Patient(String firstName, String lastName, String bornDate, String tajNumber, String zipCode, String city,
			String street, String houseNumber, String motherName, String fatherName,
			String bornPlace, String phoneNumber, String gender, String bloodGroup) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.bornDate = bornDate;
		this.tajNumber = tajNumber;
		this.zipCode = zipCode;
		this.city = city;
		this.street = street;
		this.houseNumber = houseNumber;
		this.motherName = motherName;
		this.fatherName = fatherName;
		this.bornPlace = bornPlace;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.bloodGroup = bloodGroup;
	}

	// Getters
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getBornDate() {
		return bornDate;
	}

	public void setBornDate(String bornDate) {
		this.bornDate = bornDate;
	}

	public String getTajNumber() {
		return tajNumber;
	}

	public void setTajNumber(String tajNumber) {
		this.tajNumber = tajNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getBornPlace() {
		return bornPlace;
	}

	public void setBornPlace(String bornPlace) {
		this.bornPlace = bornPlace;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getBloodGroup() {
		return bloodGroup;
	}
	
	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}
	

	@Override
	public String toString() {
		String firstName = "Keresztnév: " + getFirstName();
		String lastName = "Vezetéknév: " + getLastName();
		String bornDate = "Születési dátum: " + getBornDate();
		String tajNumber = "TAJ szám: " + getTajNumber();
		String zipCode = "Irányítószám: " + getZipCode();
		String city = "Település: " + getCity();
		String street = "Utca: " + getStreet();
		String houseNumber = "Házszám: " + getHouseNumber();
		String motherName = "Anyja neve: " + getMotherName();
		String fatherName = "Apja neve: " + getFatherName();
		String bornPlace = "Születési hely: " + getBornPlace();
		String phoneNumber = "Telefonszám: " + getPhoneNumber();
		String gender = "Neme: " + getGender();
		String bloodGroupS = "Vércsoportja: " + bloodGroup;
		
		return lastName + '\n' + firstName + '\n' +
				bornDate + '\n' + tajNumber + '\n' + zipCode
				+ '\n'   + city + '\n' + street + '\n' + houseNumber
				+ '\n'   + motherName + '\n' + fatherName
				+ '\n'   + bornPlace + '\n' + phoneNumber + '\n' + gender + "\n" + bloodGroupS;
	}
}
