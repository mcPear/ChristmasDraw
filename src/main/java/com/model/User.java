package com.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 */
@Entity
@Table(name="User")
public class User {

	@Id
	@Column(name="id_user")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_user;
	
	@Column
	private String login;
	
	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	@Lob
	private String about;
	
	@Column
	private int children;

	public int getUserId() {
		return id_user;
	}

	public void setUserId(int id) {
		this.id_user = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	@Override
	public String toString(){
		return "id="+id_user+", login="+login+", name="+name+", surname="+surname+", about="+about+", children="+children;
	}
}