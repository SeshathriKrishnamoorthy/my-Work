package beans;

public class UserActionProduct {
private String registrationId;
private String productId;
private Integer isLiked=0;
private Integer isDisliked=0;
public String getRegistrationId() {
	return registrationId;
}
public void setRegistrationId(String registrationId) {
	this.registrationId = registrationId;
}
public String getProductId() {
	return productId;
}
public void setProductId(String productId) {
	this.productId = productId;
}
public Integer getIsLiked() {
	return isLiked;
}
public void setIsLiked(Integer isLiked) {
	this.isLiked = isLiked;
}
public Integer getIsDisliked() {
	return isDisliked;
}
public void setIsDisliked(Integer isDisliked) {
	this.isDisliked = isDisliked;
}

}
