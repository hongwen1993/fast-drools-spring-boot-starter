package com.drools.core.util;

import java.io.File;
import java.util.List;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27 14:26
 * @since 1.0.0
 */
public class FileUtil {

    public static List<File> fileList(String path, List<File> fileList) {
        File file = new File(path);
        if (file.exists()) {
            if (!file.isDirectory()) {
                // base case
                fileList.add(file);
            }
            if (file.isDirectory()) {
                File[] f = file.listFiles();
                if (f != null) {
                    for (File nextFile : f) {
                        fileList(nextFile.getPath(), fileList);
                    }
                }
            }
        }
        return fileList;
    }



}
