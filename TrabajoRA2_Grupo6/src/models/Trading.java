package models;

import java.text.SimpleDateFormat;
import java.util.Date;


import services.TradingService;

public class Trading {
	private int id, id_product, id_provider, amount;
	private Date date; 
	private String type;
	public Trading(int id_product, int id_provider, int amount, String type) {
		super();
		this.id = TradingService.getNextId();
		this.id_product = id_product;
		this.id_provider = id_provider;
		this.amount = amount;
		this.date = new Date();
		this.type = type;
	}
	
	public Trading(int id, int id_product, int id_provider, int amount, String date,  String type) {
		super();
		this.id = id;
		this.id_product = id_product;
		this.id_provider = id_provider;
		this.amount = amount;
		try {
			this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		this.type = type;
	}
	
	public Trading() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getId_product() {
		return id_product;
	}
	public void setId_product(int id_product) {
		this.id_product = id_product;
	}
	public int getId_provider() {
		return id_provider;
	}
	public void setId_provider(int id_provider) {
		this.id_provider = id_provider;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "Transaction [id=" + id + ", id_product=" + id_product + ", id_provider=" + id_provider + ", amount="
				+ amount + ", date=" + date + ", type=" + type + "]";
	}
	
	
}
