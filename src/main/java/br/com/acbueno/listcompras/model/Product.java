package br.com.acbueno.listcompras.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_product")
	private int id;
	@Column(name = "product_name")
	private String productName;
	@Column(name = "qtd")
	private int qtd;
	@Column(name = "date_buy")
	private Date dateBuy;
	@ManyToOne
	@JoinColumn(name = "id_list")
	private ListShop listShop;

	public Product() {

	}

	public Product(String productName, int qtd, Date dateBuy, ListShop listShop) { 
		this.productName = productName;
		this.qtd = qtd;
		this.dateBuy = dateBuy;
		this.listShop = listShop;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getQtd() {
		return qtd;
	}

	public void setQtd(int qtd) {
		this.qtd = qtd;
	}

	public Date getDateBuy() {
		return dateBuy;
	}

	public void setDateBuy(Date dateBuy) {
		this.dateBuy = dateBuy;
	}

	public ListShop getListShop() {
		return listShop;
	}

	public void setListShop(ListShop listShop) {
		this.listShop = listShop;
	}

}
