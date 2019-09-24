
public class Student {

	private int sId;

	private String sName;

	private long sContact;

	public Student(int sId, String sName, long sContact) {
		this.sId = sId;
		this.sName = sName;
		this.sContact = sContact;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public long getsContact() {
		return sContact;
	}

	public void setsContact(long sContact) {
		this.sContact = sContact;
	}

}
