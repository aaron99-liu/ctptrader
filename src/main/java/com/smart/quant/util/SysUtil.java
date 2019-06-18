package com.smart.quant.util;

import java.lang.reflect.Field;

public class SysUtil {
	public static String getOsName() {
		return System.getProperty("os.name").toLowerCase();
	}
	
	public static void addLibraryDir(String libraryPath) {
	    try {
			Field userPathsField = ClassLoader.class.getDeclaredField("usr_paths");
			userPathsField.setAccessible(true);
			String[] paths = (String[]) userPathsField.get(null);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < paths.length; i++) {
			    if (libraryPath.equals(paths[i])) {
			        continue;
			    }
			    sb.append(paths[i]).append(';');
			}
			sb.append(libraryPath);
			System.setProperty("java.library.path", sb.toString());
			final Field sysPathsField = ClassLoader.class.getDeclaredField("sys_paths");
			sysPathsField.setAccessible(true);
			sysPathsField.set(null, null);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
