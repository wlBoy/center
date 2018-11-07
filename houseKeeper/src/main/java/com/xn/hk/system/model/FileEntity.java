package com.xn.hk.system.model;

import java.io.Serializable;

import com.xn.hk.common.constant.EnabledEnum;
import com.xn.hk.common.constant.FileType;
import com.xn.hk.common.utils.string.StringUtil;

/**
 * 
 * @ClassName: File
 * @Package: com.xn.hk.system.model
 * @Description: 文件管理的实体类
 * @Author: wanlei
 * @Date: 2018年10月19日 上午10:00:54
 */
public class FileEntity implements Serializable {
	private static final long serialVersionUID = 2202750306259747430L;
	/**
	 * 文件ID
	 */
	private String fileId;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 文件类型ID
	 */
	private Integer fileType;
	/**
	 * 文件类型名称
	 */
	private String fileTypeName;
	/**
	 * 文件存储位置
	 */
	private String filePath;
	/**
	 * 上传人
	 */
	private Integer uploadBy;
	/**
	 * 上传人名称
	 */
	private String uploadByName;
	/**
	 * 上传时间
	 */
	private String uploadTime;
	/**
	 * 更新时间
	 */
	private String updateTime;
	/**
	 * 上传日期
	 */
	private Integer curday;
	/**
	 * 备注信息
	 */
	private String remark;
	/**
	 * 是否有效：0：未删状态(默认)，1：已删状态
	 */
	private Integer isOk;

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getFileType() {
		return fileType;
	}

	public void setFileType(Integer fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeName() {
		return FileType.getFileTypeName(fileType);
	}

	public Integer getCurday() {
		return curday;
	}

	public void setCurday(Integer curday) {
		this.curday = curday;
	}

	public void setFileTypeName(String fileTypeName) {
		this.fileTypeName = fileTypeName;
	}

	public String getUploadByName() {
		return uploadByName;
	}

	public void setUploadByName(String uploadByName) {
		this.uploadByName = uploadByName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Integer getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(Integer uploadBy) {
		this.uploadBy = uploadBy;
	}

	public String getUploadTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return StringUtil.subMysqlTimeStr(uploadTime);
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getUpdateTime() {
		// 解决mysql数据库datetime类型取出时间多个.0的问题，截取前19位即可，格式为:yyyy-MM-dd HH:mm:ss
		return StringUtil.subMysqlTimeStr(updateTime);
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsOk() {
		return isOk;
	}

	public void setIsOk(Integer isOk) {
		this.isOk = isOk;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	@Override
	public String toString() {
		return "FileEntity [fileId=" + fileId + ", fileName=" + fileName + ", fileType=" + fileType + ", fileTypeName="
				+ fileTypeName + ", filePath=" + filePath + ", uploadBy=" + uploadBy + ", uploadByName=" + uploadByName
				+ ", uploadTime=" + uploadTime + ", updateTime=" + updateTime + ", curday=" + curday + ", remark="
				+ remark + ", isOk=" + isOk + "]";
	}

	/**
	 * 拼接日志内容(排除空信息和可指定记录内容字段)
	 * 
	 * @return 日志内容
	 */
	public String getLogContent() {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		if (getFileId() != null) {
			sb.append("文件ID=" + getFileId() + ",");
		}
		if (getFileName() != null) {
			sb.append("文件名=" + getFileName() + ",");
		}
		if (getFileType() != null) {
			sb.append("文件类型名=" + FileType.getFileTypeName(getFileType()) + ",");
		}
		if (getUploadByName() != null) {
			sb.append("上传人=" + getUploadByName() + ",");
		}
		if (getIsOk() != null) {
			sb.append("是否可用=" + EnabledEnum.getDescByCode(getIsOk()) + ",");
		}
		if (getCurday() != null) {
			sb.append("创建日期=" + getCurday() + ",");
		}
		if (uploadTime != null) {
			sb.append("创建时间=" + getUploadTime() + ",");
		}
		if (updateTime != null) {
			sb.append("更新时间=" + getUpdateTime() + ",");
		}
		if (getRemark() != null) {
			sb.append("备注信息=" + getRemark() + ",");
		}
		// 去除最后一个,后再拼接]
		String str = sb.toString();
		str = str.substring(0, str.length() - 1) + "]";
		return str;
	}
}
