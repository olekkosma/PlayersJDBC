package com.players.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "contract.all", query = "SELECT c FROM Contract c")
public class Contract {
	
	public Long id;
	public Player player;
	public double salary;
	public String title;
	public String companyName;
	
	public Contract() {
		super();
		
	}
	
	public Contract(Player player, double salary, String title, String companyName) {
		super();
		this.player = player;
		this.salary = salary;
		this.title = title;
		this.companyName = companyName;
	}

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	@Column(unique = true)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
