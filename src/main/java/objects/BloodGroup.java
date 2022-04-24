package objects;

/**
 * Blood group enum
 */
public enum BloodGroup {

	O_NEG("0-"),
	O_POS("0+"),
	A_NEG("A-"),
	A_POS("A+"),
	B_NEG("B-"),
	B_POS("B+"),
	AB_NEG("AB-"),
	AB_POS("AB+");
	
	private final String bloodGroup;
	
	BloodGroup(String bg){
		this.bloodGroup = bg;
	}
	
	public String getBloodGroup() {
		return bloodGroup;
	}
	
}
