package com.gso.dogreview.model;

public class Comment {

	private String id;
	private String dogId;
	private String userAvatar;
	private String description;
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the userAvatar
	 */
	public String getUserAvatar() {
		return userAvatar;
	}
	/**
	 * @param userAvatar the userAvatar to set
	 */
	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}
	/**
	 * @return the dogId
	 */
	public String getDogId() {
		return dogId;
	}
	/**
	 * @param dogId the dogId to set
	 */
	public void setDogId(String dogId) {
		this.dogId = dogId;
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
}
