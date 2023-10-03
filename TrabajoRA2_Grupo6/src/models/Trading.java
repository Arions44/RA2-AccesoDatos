package models;

import java.time.LocalDate;

import services.TradingService;

public class Trading {
	private int id, id_product, id_provider, amount;
	private LocalDate date; 
	private String type;
	public Trading(int id_product, int id_provider, int amount, LocalDate date, String type) {
		super();
		this.id = TradingService.getNextId();
		this.id_product = id_product;
		this.id_provider = id_provider;
		this.amount = amount;
		this.date = date;
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
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
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
