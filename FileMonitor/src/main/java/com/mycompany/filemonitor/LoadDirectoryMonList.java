/**
 * 
 */
package com.mycompany.filemonitor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import com.mycompany.filemonitor.classes.DirFile;
import com.mycompany.filemonitor.classes.Directory;

/**
 * @author U324167
 *
 */
public class LoadDirectoryMonList implements Runnable{
	private static final String MON_LIST="c://VBC_DATA//monlist.dat";
	private static final Integer SLEEP_MIN = 60000;
	private static BufferedReader in=null;
	
	private Map<String, Directory> directoryList;
	
	public Map<String, Directory> getDirectoryList() {
		return directoryList;
	}


	public void setDirectoryList(Map<String, Directory> directoryList) {
		this.directoryList = directoryList;
	}


	public void ReadAndLoadMonitorList(Map<String, Directory> directoryList){
		try {
			in = new BufferedReader(new FileReader(MON_LIST));
			String line=null;
	        while ((line = in.readLine()) != null) {
	        	Directory dir = new Directory(line);
	        	if (!directoryList.containsKey(dir.getDirPath())){
	        		directoryList.put(dir.getDirPath(), dir);
	        	}
	        }			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}


	@Override
	public void run() {
		while (true){
			ReadAndLoadMonitorList(directoryList);
			try {
				Thread.sleep(SLEEP_MIN * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
