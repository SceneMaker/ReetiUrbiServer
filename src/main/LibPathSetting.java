package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibPathSetting {

    public static String CereproTts_Used;
    public static Properties mserverConfig = new Properties();
    // the path of the properties file
    public static final String sPath = "res/serverConfig.properties";
    
    public static String gostai_lib_path;
    public static String cereproc_lib_path;
    public static String cereproc_jar_Name;
    public static String cereproc_voice;
    public static String cereproc_license;

    public static void loadConfig() {
        try {
            File file = new File(sPath);
            FileInputStream stream = null;

            if (file.exists()) {
                stream = new FileInputStream(file);
                mserverConfig.load(stream);
                stream.close();
                readConfig(mserverConfig);
            } else {
                System.err.println("Could not load serverConfig: file " + sPath + "not found");
            }
        } catch (IOException ex) {
            System.err.println("not found" + "Could not load serverConfig: file " + ex.getMessage());
        }

    }

    public static void readConfig(Properties mserverConfig) {
        
        // The cerevoice_eng library must be on the path,
        // specify with eg:
        // java -Djava.library.path=/path/to/library/
        // System.setProperty("java.library.path",
        // "/home/alvaro/Documentos/Tesis/cerevoice_sdk_3.2.0_linux_x86_64_python26_10980_academic/cerevoice_eng");
        
        CereproTts_Used = mserverConfig.getProperty("CereproTts_Used", "nulll").trim();
        gostai_lib_path = mserverConfig.getProperty("gostai_lib_path", "nulll");
        
        try { // Adding Library path
            addDir(LibPathSetting.gostai_lib_path);
            System.loadLibrary("urbijava");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (CereproTts_Used.equalsIgnoreCase("true")) {
            cereproc_lib_path = mserverConfig.getProperty("cereproc_lib_path", "nulll");
            cereproc_voice = mserverConfig.getProperty("cereproc_voice", "nulll");
            cereproc_license = mserverConfig.getProperty("cereproc_license", "nulll");
            
            try { // Adding Library path
                addDir(LibPathSetting.cereproc_lib_path);
                System.loadLibrary("cerevoice_eng");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void addDir(String s) throws IOException {
        try {
            // This enables the java.library.path to be modified at runtime
            // From a Sun engineer at
            // http://forums.sun.com/thread.jspa?threadID=707176
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[]) field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (s.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length + 1];
            System.arraycopy(paths, 0, tmp, 0, paths.length);
            tmp[paths.length] = s;
            field.set(null, tmp);
            System.setProperty("java.library.path",
                    System.getProperty("java.library.path")
                    + File.pathSeparator + s);
        } catch (IllegalAccessException e) {
            throw new IOException(
                    "Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException(
                    "Failed to get field handle to set library path");
        }
    }
}