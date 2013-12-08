/**
 * 
 */
package com.mycompany.filemonitor.classes;

import java.util.Date;

/**
 * @author U324167
 *
 */
public final class Directory {

	
	private String dirPath;
	private Date lastMonitoredTime;
	private Boolean locked;
	
	/**
	 * @param dirPath
	 */
	public Directory(String dirPath) {
		super();
		this.dirPath = dirPath;
		this.locked = Boolean.FALSE;
	}
	public String getDirPath() {
		return dirPath;
	}
	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
	}
	public Date getLastMonitoredTime() {
		return lastMonitoredTime;
	}
	public void setLastMonitoredTime(Date lastMonitoredTime) {
		this.lastMonitoredTime = lastMonitoredTime;
	}
	public void lock() {
		this.locked = Boolean.TRUE;
	}
	public void unlock() {
		this.locked = Boolean.FALSE;
	}
	
	public Boolean isLocked(){
		return this.locked;
	}
	
	

}
