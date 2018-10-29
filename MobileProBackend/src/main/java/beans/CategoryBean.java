package beans;

public class CategoryBean {
public Integer categoryId;
public String categoryName;
public String registrationId;
public Integer index;


public String getRegistrationId() {
	return registrationId;
}
public void setRegistrationId(String registrationId) {
	this.registrationId = registrationId;
}
public Integer getCategoryId() {
	return categoryId;
}
public Integer getIndex() {
	return index;
}
public void setIndex(Integer index) {
	this.index = index;
}
public void setCategoryId(Integer categoryId) {
	this.categoryId = categoryId;
}
public String getCategoryName() {
	return categoryName;
}
public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
}
}
