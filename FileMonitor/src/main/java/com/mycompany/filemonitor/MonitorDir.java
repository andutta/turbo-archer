/**
 * 
 */
package com.mycompany.filemonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.mycompany.filemonitor.classes.DirFile;
import com.mycompany.filemonitor.classes.Directory;

/**
 * @author U324167
 *
 */
public class MonitorDir implements Callable {
	
	private static final Logger logger = Logger.getLogger(MonitorDir.class);
	private static final Integer WAIT_TIME = 5000; 
	private Directory directory;
	
	/**
	 * @param directory
	 */
	public MonitorDir(Directory directory) {
		super();
		this.directory = directory;
	}

	public Directory getDirectory() {
		return directory;
	}

	@Override
	public List<DirFile> call() throws Exception {
		try {
			return CheckDir(Boolean.FALSE);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			directory=null;
		}
		return null;
	}

	private List<DirFile> CheckDir(Boolean stopMonitoring) throws InterruptedException{
		File folder = new File(directory.getDirPath());
		logger.info("Monitoring >>  " + this.directory.getDirPath());
		List<DirFile> foundFiles = new ArrayList<DirFile>();
 
		//while (!stopMonitoring){
			 File[] listOfFiles = folder.listFiles();
			 for (int i=0;i<listOfFiles.length;i++){
				 if (listOfFiles[i].isFile()){
					 DirFile fl = new DirFile();
					 fl.setFileName(listOfFiles[i].getName());
					 fl.setFilePath(directory.getDirPath());
					 foundFiles.add(fl);
					 //updateCurrentList(listOfFiles[i]);
				 }
			 }
			 //logger.info("Thread monitor for dir " + this.directory.getDirPath() + " WAITING ........");
			 //Thread.sleep(WAIT_TIME);
			 //logger.info("done");
			 return foundFiles;
		//}
	}
	
	/*
	private synchronized void updateCurrentList(File file){
		logger.info("Thread monitor for dir " + this.directory.getDirPath() + " found the file >>>>>>>>> " + file.getName());
		
	}*/	
	
}
