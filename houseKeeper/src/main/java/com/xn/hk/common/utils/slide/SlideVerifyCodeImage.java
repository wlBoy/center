package com.xn.hk.common.utils.slide;

import java.util.UUID;

/**
 * 
 * @ClassName: SlideVerifyCodeEntity
 * @Package: com.xn.hk.common.utils.slide
 * @Description: 滑块验证码实体类
 * @Author: wanlei
 * @Date: 2020年11月20日 上午10:46:46
 */
public class SlideVerifyCodeImage {
	/**
	 * 唯一标识
	 */
	private String id;
	/**
	 * 背景图片base64码
	 */
	private String backImgBase64;
	/**
	 * 拼图图片base64码
	 */
	private String markImgBase64;
	/**
	 * 图左上角的x坐标位置
	 */
	private int xLocation;
	/**
	 * 图左上角的y坐标位置
	 */
	private int yLocation;

	public SlideVerifyCodeImage() {
		super();
	}

	public SlideVerifyCodeImage(String backImgBase64, String markImgBase64, int xLocation, int yLocation) {
		super();
		this.id = UUID.randomUUID().toString();
		this.backImgBase64 = backImgBase64;
		this.markImgBase64 = markImgBase64;
		this.xLocation = xLocation;
		this.yLocation = yLocation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBackImgBase64() {
		return backImgBase64;
	}

	public void setBackImgBase64(String backImgBase64) {
		this.backImgBase64 = backImgBase64;
	}

	public String getMarkImgBase64() {
		return markImgBase64;
	}

	public void setMarkImgBase64(String markImgBase64) {
		this.markImgBase64 = markImgBase64;
	}

	public int getxLocation() {
		return xLocation;
	}

	public void setxLocation(int xLocation) {
		this.xLocation = xLocation;
	}

	public int getyLocation() {
		return yLocation;
	}

	public void setyLocation(int yLocation) {
		this.yLocation = yLocation;
	}

	@Override
	public String toString() {
		return "SlideVerifyCodeImage [id=" + id + ", backImgBase64=" + backImgBase64 + ", markImgBase64="
				+ markImgBase64 + ", xLocation=" + xLocation + ", yLocation=" + yLocation + "]";
	}

}
