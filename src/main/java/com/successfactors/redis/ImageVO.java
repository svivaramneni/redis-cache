package com.successfactors.redis;

import java.io.Serializable;

public class ImageVO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String imageName;

	private byte[] imageContent;

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String image) {
		this.imageName = image;
	}

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}

	@Override
	public String toString() {
		return "Image [" + getImageName() + ", " + getImageContent() + "]";
	}

}
