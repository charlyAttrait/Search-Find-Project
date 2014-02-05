package com.iia.data.CLASS;

public class Item {

	/**
	 * Attributes
	 */
	private int id;
	private String libelle;
	private int coord_Alt;
	private int coord_Long;
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
	 * @return the coord_Alt
	 */
	public int getCoord_Alt() {
		return coord_Alt;
	}
	/**
	 * @param coord_Alt the coord_Alt to set
	 */
	public void setCoord_Alt(int coord_Alt) {
		this.coord_Alt = coord_Alt;
	}
	
	/**
	 * @return the coord_Long
	 */
	public int getCoord_Long() {
		return coord_Long;
	}
	/**
	 * @param coord_Long the coord_Long to set
	 */
	public void setCoord_Long(int coord_Long) {
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
	private Item() {
		super();
	}
	
	/**
	 * @param libelle
	 * @param coord_Alt
	 * @param coord_Long
	 * @param user
	 */
	public Item(String libelle, int coord_Alt, int coord_Long, User user) {
		super();
		this.libelle = libelle;
		this.coord_Alt = coord_Alt;
		this.coord_Long = coord_Long;
		this.user = user;
	}
	
}
