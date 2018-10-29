package beans;

public class RegistrationBean {
	private int registrationId;
	private String name;

	private String password;
	@Override
	public String toString() {
		return "RegistrationBeam [registrationId=" + registrationId + ", name=" + name + ", password=" + password + ", country=" + country + ", email=" + email + "]";
	}
	public int getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	private String country;
	private String email;
	
}
