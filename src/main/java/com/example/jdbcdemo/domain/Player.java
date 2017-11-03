package com.example.jdbcdemo.domain;

public class Player {
	
	public long id;
	public int age;
	public String name;
	public double marketValue;
	
	
	public Player(){
		
	}
	
	public Player(int age, String name, double marketValue){
		this.age=age;
		this.name=name;
		this.marketValue=marketValue;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	
	

}
