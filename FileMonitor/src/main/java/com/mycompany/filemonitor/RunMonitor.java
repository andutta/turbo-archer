/**
 * 
 */
package com.mycompany.filemonitor;

import java.io.BufferedReader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.mycompany.filemonitor.classes.DirFile;
import com.mycompany.filemonitor.classes.Directory;

/**
 * @author U324167
 *
 */
public class RunMonitor {

	private static final Logger logger = Logger.getLogger(RunMonitor.class);
	private static final String STOP_MON="c://VBC_DATA//stopme.dat";
	private static final int THREAD_POOL_SIZE = 50;
	private static final Integer WAIT_TIME = 30000;
	private static Map<String, Directory> monitorList;
	private static Map<String, Future<List<DirFile>>> fileList;
	private static ExecutorService configLoaderService = Executors.newSingleThreadExecutor();
	private static ExecutorService dirMonitorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

        initialize();
        startConfigLoaderThread();
        startDirectoryMonitorThreads();
        
        
        File stopFile =null;
        try{
	        while (true) {
	        	stopFile = new File(STOP_MON);
		        if (stopFile.exists()){
		        	logger.info("Stop Monitor Notification Received");
		        	stopFile.delete();
		        	break;
		        }
		        /*
		        else {
		        	if (!executor.isShutdown()){
		        		LoadDirectoryMonList.ReadAndLoadMonitorList(monitorList);
		        		Iterator<Directory> ir = monitorList.values().iterator();
		        		while (ir.hasNext()){
		        			Directory currDir = ir.next();
		        			currDir.lock();
		        			MonitorDir monitorDir = new MonitorDir(currDir);
		        			Future<List<DirFile>> foundFiles = executor.submit(monitorDir);
		        			
		        			
		        		}
				        logger.info("Waiting for next Cycle....");
			        	Thread.sleep(WAIT_TIME);
			        	logger.info("Done");
		        	}
		        }	*/        	
	        }
	        stopAllThreads();
	        
        }catch(Exception e){
        	e.printStackTrace();
        }finally{
        	configLoaderService = null;
        	dirMonitorService = null;
        	monitorList = null;
        	fileList = null;
        	stopFile=null;
        }
        
        logger.info("Finished all threads");

	}
	
	private static void initialize(){
		monitorList = new HashMap<String, Directory>();
		fileList = new HashMap<String, Future<List<DirFile>>>();
	}
	
	private static void startConfigLoaderThread(){
		LoadDirectoryMonList loadDirectoryMonList = new LoadDirectoryMonList();
		loadDirectoryMonList.setDirectoryList(monitorList);
		configLoaderService.execute(loadDirectoryMonList);
		logger.info("Started Config Loader Thread");
	}
	
	private static void startDirectoryMonitorThreads(){
		Iterator<Directory> ir = monitorList.values().iterator();
		while (true){
			while (ir.hasNext()){
				Directory currDir = ir.next();
				if (!currDir.isLocked()){
					currDir.lock();
					MonitorDir monitorDir = new MonitorDir(currDir);
					Future<List<DirFile>> foundFiles = dirMonitorService.submit(monitorDir);
					fileList.put(currDir.getDirPath(), foundFiles);
				}
			}
			
		}

	}
	
	private static void stopAllThreads(){
		dirMonitorService.shutdown();
		logger.info("Stopped Directory Monitor Thread");
		configLoaderService.shutdown();
		logger.info("Stopped Config Loader Thread");
	}

}
