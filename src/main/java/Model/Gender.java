package Model;

public class Gender {

	private int gender_id;
	private String gender_name;
	private int gender_status;

	public Gender(int gender_id) {
		this.gender_id = gender_id;
	}

	public Gender(int gender_id, String gender_name, int gender_status) {
		this.gender_id = gender_id;
		this.gender_name = gender_name;
		this.gender_status = gender_status;
	}

	public int getGender_id() {
		return gender_id;
	}

	public String getGender_name() {
		return gender_name;
	}

	public void setGender_name(String gender_name) {
		this.gender_name = gender_name;
	}

	public int getGender_status() {
		return gender_status;
	}

	public void setGender_status(int gender_status) {
		this.gender_status = gender_status;
	}
}
