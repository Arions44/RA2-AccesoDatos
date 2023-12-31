package models;

import services.ProviderServices;

public class Provider {
	
	private int id, active;
	private String name,description,address,phone;
	
	public Provider(String name, String description, String address, String phone) {
		super();
		this.id = ProviderServices.getNextId();
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.active = 1;
	}
	public Provider(int id, String name, String description, String address, String phone, int active) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.address = address;
		this.phone = phone;
		this.active = active;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "Provider [id=" + id + ", name=" + name + ", description=" + description + ", address=" + address
				+ ", phone=" + phone + ", active=" + active + "]";
	}
	
}
