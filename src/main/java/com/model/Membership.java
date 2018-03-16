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
@Table(name="Membership")
public class Membership {

	@Id
	@Column(name="id_membership")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_membership;
	
	@Column
	private boolean owner;
	
	@Column
	private boolean accepted;
	
	@Column
	private int draw_user;
	
	@Column
	private int user;
	
	@Column
	private int gift;
	
	@Column
	private int group;

	public int getMembershipId() {
		return id_membership;
	}

	public void setUserId(int id) {
		this.id_membership = id;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public int getDrawUser() {
		return draw_user;
	}
	
	public void setDrawUser(int draw_user) {
		this.draw_user = draw_user;
	}
	public int getUser() {
		return user;
	}
	
	public void setUser(int user) {
		this.user = user;
	}
	public int getGift() {
		return gift;
	}
	
	public void setGift(int gift) {
		this.gift = gift;
	}
	public int getGroup() {
		return group;
	}
	
	public void setGroup(int group) {
		this.group = group;
	}

	
	@Override
	public String toString(){
		return "id="+id_membership+", owner="+owner+", accepted="+accepted+", draw_user="+draw_user+", user="+user+", gift="+gift+", group="+group;
	}
}