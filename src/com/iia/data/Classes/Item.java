package com.iia.data.Classes;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * Attributes
	 */
	private int id;
	private String libelle;
	private double coord_Lat;
	private double coord_Long;
	private User user;
	
	/**
	 * Accessors
	 */
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	/**
	 * @return the coord_Lat
	 */
	public double getCoord_Lat() {
		return coord_Lat;
	}
	/**
	 * @param coord_lAT the coord_lAT to set
	 */
	public void setCoord_Lat(double coord_Lat) {
		this.coord_Lat = coord_Lat;
	}
	
	/**
	 * @return the coord_Long
	 */
	public double getCoord_Long() {
		return coord_Long;
	}
	/**
	 * @param coord_Long the coord_Long to set
	 */
	public void setCoord_Long(double coord_Long) {
		this.coord_Long = coord_Long;
	}
	
	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Constructors
	 */
	public Item() {
		super();
	}
	
	/**
	 * @param libelle
	 * @param coord_Lat
	 * @param coord_Long
	 * @param user
	 */
	public Item(String libelle, double coord_Lat, double coord_Long, User user) {
		super();
		this.libelle = libelle;
		this.coord_Lat = coord_Lat;
		this.coord_Long = coord_Long;
		this.user = user;
	}
	
}
