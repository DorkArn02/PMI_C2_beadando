package objects;

public class Diagnosis {
	private String name;
	private String date;
	private String expertOpinion;
	
	public Diagnosis() {
		
	}
	
	/**
	 * Diagnosis object
	 * @param name Name of diagnosis
	 * @param date Primary key in xml file, <diagnosis date="yyyy-MM-dd hh:mm"></diagnosis>
	 * @param expertOpinion Expert's opinion
	 */
	public Diagnosis(String name, String date, String expertOpinion) {
		this.name = name;
		this.date = date;
		this.expertOpinion = expertOpinion;
	}

	// Getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getExpertOpinion() {
		return expertOpinion;
	}

	public void setExpertOpinion(String expertOpinion) {
		this.expertOpinion = expertOpinion;
	}
}
