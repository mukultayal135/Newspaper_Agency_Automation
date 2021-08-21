package hawkerhistory;

import java.util.Date;

public class userbean1 {
Date doj;
String name;
String address;
String mobile;
String area;
public userbean1()
{
	
}
public userbean1(Date doj, String name, String address, String mobile,String area) {
	super();
	this.doj = doj;
	this.name = name;
	this.address = address;
	this.mobile = mobile;
	this.area = area;
}
public Date getDoj() {
	return doj;
}
public void setDoj(Date doj) {
	this.doj = doj;
}
public String getName() {
	return name;
}
public String getArea() {
	return area;
}
public void setName(String name) {
	this.name = name;
}
public void setArea(String area) {
	this.area = area;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
public String getMobile() {
	return mobile;
}
public void setMobile(String mobile) {
	this.mobile = mobile;
}
}
