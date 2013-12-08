/**
 * 
 */
package com.mycompany.filemonitor.classes;

import java.util.Date;

/**
 * @author U324167
 *
 */
public class DirFile {
	
	private String filePath;
	private String fileName;
	private Date lastProcessedTime;
	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Date getLastProcessedTime() {
		return lastProcessedTime;
	}
	public void setLastProcessedTime(Date lastProcessedTime) {
		this.lastProcessedTime = lastProcessedTime;
	}

}
