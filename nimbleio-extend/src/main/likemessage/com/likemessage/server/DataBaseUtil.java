package com.likemessage.server;

import java.util.concurrent.atomic.AtomicBoolean;

import com.generallycloud.nio.common.LifeCycleUtil;
import com.generallycloud.nio.common.database.DataBaseContext;

public class DataBaseUtil {

	private static DataBaseContext dataBaseContext = null;
	
	private static AtomicBoolean initialized = new AtomicBoolean(); 
	
	public static void initializeDataBaseContext() throws Exception{
		
		if (initialized.compareAndSet(false, true)) {
			
			dataBaseContext = new DataBaseContext();
			
			LifeCycleUtil.start(dataBaseContext);
		}
	}
	
	public static DataBaseContext getDataBaseContext(){
		return dataBaseContext;
	}
	
	public static void destroyDataBaseContext(){
		
		if (initialized.compareAndSet(true, false)) {
			
			LifeCycleUtil.stop(dataBaseContext);
		}
	}
	
	
}
