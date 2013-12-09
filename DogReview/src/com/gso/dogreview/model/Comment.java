package com.gso.dogreview.model;

import java.io.Serializable;

public class Comment implements Serializable{

	private String id;
	private String dogId;
	private String avatar;
	private String comment;

	
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
	/**
	 * @return the dogAvatar
	 */
	/**
	 * @return the avatar
	 */
	public String getAvatar() {
		return avatar;
	}
	/**
	 * @param avatar the avatar to set
	 */
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
