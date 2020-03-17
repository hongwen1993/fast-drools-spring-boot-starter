package com.drools.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/27
 * @since 1.0.0
 */
public class FileUtil {

    private static Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 整合所有文件，到所传的List中
     *
     * @param path      可能含有classpath形式，因此需要进行判断
     * @param fileList  {@code List<File>} 所传入的FileList
     */
    public static void fileList(String path, List<File> fileList) {
        if (path.startsWith(ResourceLoader.CLASSPATH_URL_PREFIX)) {
            try {
                ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
                Resource[] resources = resolver.getResources(path);
                for (Resource resource : resources) {
                    fileList.add(resource.getFile());
                }
            } catch (IOException e) {
                LOG.error("资源读取异常！", e);
            }
        } else {
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
        }
    }


}
