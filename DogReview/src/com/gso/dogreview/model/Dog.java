package com.gso.dogreview.model;

import java.io.Serializable;

public class Dog implements Serializable{

	private String id;
	private String name;
	private String description;
	private String avatar;
	private boolean isFavourite;
	private boolean isRead;

	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}

	/**
	 * @param avatar
	 *            the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the isFavourite
	 */
	public boolean isFavourite() {
		return isFavourite;
	}

	/**
	 * @param isFavourite the isFavourite to set
	 */
	public void setFavourite(boolean isFavourite) {
		this.isFavourite = isFavourite;
	}

	/**
	 * @return the isRead
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

}
