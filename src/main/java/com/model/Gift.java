package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 */
@Entity
@Table(name="Gift")
public class Gift {

	@Id
	@Column(name="id_gift")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_gift;
	
	@Column
	private String name;
	
	@Column
	private int value;

	public int getGiftId() {
		return id_gift;
	}

	public void setGiftId(int id) {
		this.id_gift = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public String toString(){
		return "id="+id_gift+", name="+name+", value="+value;
	}
}