package util;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import message.*;

public class Messageresend {
	private Map<String,ScheduledFuture<?>> messageidmap=new HashMap<String,ScheduledFuture<?>>();
	
	private ScheduledExecutorService pool=Executors.newSingleThreadScheduledExecutor();
	
	public long SCHEDULEDDELAYTIME=3000;
	
	public Map<String, ScheduledFuture<?>> getMessageidmap() {
		return messageidmap;
	}

	public ScheduledExecutorService getPool() {
		return pool;
	}
}
