package pt.tecnico.phonebook.dto;

public class ContactDto implements Comparable<ContactDto> {

	private String name;
	private int phoneNumber;
	private String email;
	private String personName;

	public ContactDto() { // No nulls
		this.name = "";
		this.phoneNumber = -1;
		this.email = "";
		this.personName = "";
	}

	public ContactDto(String name, int phoneNumber) {
		this();
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public ContactDto(String name, int phoneNumber, String email) {
		this(name, phoneNumber);
		this.email = email;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getPersonName() {
		return personName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setPhoneNumber(int phone) {
		this.phoneNumber = phone;
	}

	public int getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

	@Override
	public int compareTo(ContactDto other) {
		return getName().compareTo(other.getName());
	}
}
