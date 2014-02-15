package com.iia.data.Classes;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * Attributes
	 */
	private int id;
	private String libelle;
	private double coordLat;
	private double coordLong;
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
	public double getCoordLat() {
		return coordLat;
	}
	/**
	 * @param coord_lAT the coord_lAT to set
	 */
	public void setCoordLat(double coordLat) {
		this.coordLat = coordLat;
	}
	
	/**
	 * @return the coord_Long
	 */
	public double getCoordLong() {
		return coordLong;
	}
	/**
	 * @param coord_Long the coord_Long to set
	 */
	public void setCoordLong(double coordLong) {
		this.coordLong = coordLong;
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
	public Item(String libelle, double coordLat, double coordLong, User user) {
		super();
		this.libelle = libelle;
		this.coordLat = coordLat;
		this.coordLong = coordLong;
		this.user = user;
	}
	
}
