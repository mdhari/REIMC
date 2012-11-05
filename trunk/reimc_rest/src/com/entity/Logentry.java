package com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the logentry database table.
 * 
 */
@Entity
@Table(name="logentry")
public class Logentry implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false)
	private int logEntryId;

	@Column(length=45)
	private String appName;

	@Column(length=45)
	private String deviceId;

	@Column(length=45)
	private String logDateTime;

	@Column(length=45)
	private String logPhoneNum;

	@Column(length=45)
	private String logType;

	@Column(length=45)
	private String logValue;

    public Logentry() {
    }

	public int getLogEntryId() {
		return this.logEntryId;
	}

	public void setLogEntryId(int logEntryId) {
		this.logEntryId = logEntryId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getLogDateTime() {
		return this.logDateTime;
	}

	public void setLogDateTime(String logDateTime) {
		this.logDateTime = logDateTime;
	}

	public String getLogPhoneNum() {
		return this.logPhoneNum;
	}

	public void setLogPhoneNum(String logPhoneNum) {
		this.logPhoneNum = logPhoneNum;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogValue() {
		return this.logValue;
	}

	public void setLogValue(String logValue) {
		this.logValue = logValue;
	}

	@Override
	public String toString() {
		return "Logentry [logEntryId=" + logEntryId + ", appName=" + appName + ", deviceId=" + deviceId 
				+ ", logDateTime=" + logDateTime + ", logPhoneNum=" + logPhoneNum
				+ ", logType=" + logType + ", logValue=" + logValue
				+ "]";
	}
}