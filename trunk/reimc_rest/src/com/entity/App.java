package com.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the app database table.
 * 
 */
@Entity
@Table(name="app")
public class App implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(unique=true, nullable=false)
	private int appId;

	@Column(length=45)
	private String appName;

    public App() {
    }

	public int getAppId() {
		return this.appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	@Override
	public String toString() {
		return "App [appId=" + appId + ", appName=" + appName
				+ "]";
	}
	
}