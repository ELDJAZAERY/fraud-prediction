package application;


import data.Profile;
import javafx.beans.property.SimpleStringProperty;

public class LineTableProfile {

    private final SimpleStringProperty id;
    private final SimpleStringProperty limitBAL;
    private final SimpleStringProperty Ballance;
    private final SimpleStringProperty age;
    private final SimpleStringProperty sex;
    private final SimpleStringProperty mariage;
    private final SimpleStringProperty education;

	public LineTableProfile(Profile profile) {
		this.id = new SimpleStringProperty(""+profile.getId());
		this.limitBAL = new SimpleStringProperty(""+profile.getLimitBAL());
		this.Ballance = new SimpleStringProperty(""+profile.getBallance());
		this.age = new SimpleStringProperty(""+profile.getAge());
		this.sex = new SimpleStringProperty(""+profile.getSex());
		this.mariage = new SimpleStringProperty(""+profile.getMariage());
		this.education = new SimpleStringProperty(""+profile.getEducation());
	}

	public String getId() {
		return id.get();
	}

	public String getLimitBAL() {
		return limitBAL.get()+" $";
	}

	public String getBallance() {
		return Ballance.get()+" $";
	}

	public String getAge() {
		return age.get();
	}

	public String getSex() {
		return sex.get();
	}

	public String getMariage() {
		return mariage.get();
	}

	public String getEducation() {
		return education.get();
	}	
	
}
