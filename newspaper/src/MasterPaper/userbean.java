package MasterPaper;

public class userbean {
String paper;
Float price;
public String getPaper() {
	return paper;
}
public void setPaper(String paper) {
	this.paper = paper;
}
public Float getPrice() {
	return price;
}
public void setPrice(Float price) {
	this.price = price;
}
public userbean(String paper, Float price) {
	super();
	this.paper = paper;
	this.price = price;
}
}
