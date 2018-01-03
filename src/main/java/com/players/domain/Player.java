package com.players.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "player.all", query = "SELECT p FROM Player p"),
    @NamedQuery(name = "player.byName", query = "SELECT p FROM Player p WHERE p.name = :name") })
public class Player {
	
	public long id;
	public int age;
	public String name;
	public double marketValue;
	public NationalTeam nationalTeam;
	private List<Contract> contracts = new ArrayList<Contract>();
	
	public Player(){
		
	}
	
	public Player(int age, String name, double marketValue, NationalTeam nationalTeam) {
		super();
		this.age = age;
		this.name = name;
		this.marketValue = marketValue;
		this.nationalTeam = nationalTeam;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(double marketValue) {
		this.marketValue = marketValue;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public NationalTeam getNationalTeam() {
		return nationalTeam;
	}

	public void setNationalTeam(NationalTeam nationalTeam) {
		this.nationalTeam = nationalTeam;
	}
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Contract> getContracts() {
		return contracts;
	}

	public void setContracts(List<Contract> contracts) {
		this.contracts = contracts;
	}

}
