package com.cwind.nio;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WatchServer {
	public String FileChanged(Path path) throws IOException, InterruptedException {
		String messageflag=null;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		WatchService watchService=FileSystems.getDefault().newWatchService();
		//登记目录到检测名单中
		final WatchKey watchKey=path.register(watchService, 
				StandardWatchEventKinds.OVERFLOW,StandardWatchEventKinds.ENTRY_CREATE,
				StandardWatchEventKinds.ENTRY_DELETE,StandardWatchEventKinds.ENTRY_MODIFY);
		//初始化flag，文件没有被改变。
		boolean fileNotChanged=true;
		while(fileNotChanged) {
			final WatchKey wk=watchService.take();
			for(WatchEvent<?> event:wk.pollEvents()) {
				final Path changed=(Path) event.context();
				String message=event.kind().toString();
				Date date=new Date();
				String dates=sdf.format(date);
				if(message.equals("OVERFLOW")) {
					System.out.println(changed+","+"覆盖 "+dates);
				}else if(message.equals("ENTRY_CREATE")) {
					System.out.println(changed+","+"新建 "+dates);
				}else if(message.equals("ENTRY_DELETE")) {
					System.out.println(changed+","+"删除 "+dates);
				}else if(message.equals("ENTRY_MODIFY")) {
					System.out.println(changed+","+"修改 "+dates);
				}
			}
			boolean valid = wk.reset();
		    if (!valid) {
		        System.out.println("Key has been unregisterede");
		    }
		}
		return null;
	}
}
