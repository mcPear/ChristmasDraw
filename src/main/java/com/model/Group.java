package com.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity bean with JPA annotations
 * Hibernate provides JPA implementation
 */
@Entity
@Table(name="Group")
public class Group {

	@Id
	@Column(name="id_group")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id_group;
	
	@Column(name = "draw_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date draw_date;
	
	@Column
	private boolean is_drawn;
	
	@Column
	private boolean count_children;
	
	@Column
	private int gift_value;

	public int getGroupId() {
		return id_group;
	}

	public void setGroupId(int id) {
		this.id_group = id;
	}
	
	public Date getDrawDate() {
		return draw_date;
	}
	
	public void setDrawDate(Date date) {
		this.draw_date = date;
	}
	
	public boolean getIsDrawn() {
		return is_drawn;
	}

	public void setIsDrawn(boolean is_drawn) {
		this.is_drawn = is_drawn;
	}
	
	public boolean getCountChildren() {
		return is_drawn;
	}
	
	public void setCountChildren(boolean count_children) {
		this.count_children = count_children;
	}
	
	public int getGiftValue() {
		return gift_value;
	}
	
	public void setGiftValue(int gift_value) {
		this.gift_value = gift_value;
	}
	
	
	@Override
	public String toString(){
		return "id="+id_group+", draw_date="+draw_date+", is_drawn="+is_drawn+", count_children="+count_children+", gift_value="+gift_value;
	}
}