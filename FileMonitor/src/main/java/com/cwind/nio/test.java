package com.cwind.nio;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class test {
	
	public static void main(String[] args) throws IOException, InterruptedException {
		WatchServer ws =new WatchServer();
		Path path=Paths.get(args[0]);
		System.out.println(path);
		String mess=ws.FileChanged(path);
		System.out.println(mess);
	}
}
