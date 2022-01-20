package com.drools.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author <a href="mailto:hongwen0928@outlook.com">Karas</a>
 * @date 2019/9/26 17:18
 * @since 1.0.0
 */
public class KieAccessor implements InitializingBean {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    public static final String PATH_SPLIT = ",";

    private String path;

    private String mode;

    private Long update;

    private String listener;

    private String verify;

    private String charset;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (path == null || path.length() == 0) {
            logger.error("Please set base path(spring.drools.path = xxx).");
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Long getUpdate() {
        return update;
    }

    public void setUpdate(Long update) {
        this.update = update;
    }

    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }
}
