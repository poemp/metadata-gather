package org.poem.loghelper;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.poem.loghelper.file.FileService;
import org.poem.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * 日志管理
 *
 * @author Administrator
 */
@Data
@Service
@ConfigurationProperties(prefix = "logging.gather")
public class LoggerHelper {

    @Autowired
    private FileService fileService;

    private String storage;

    private String path;

    /**
     * 保存数据
     * @param content
     */
    public void  info(String content){
       String gatherId = ThreadUtils.getTaskId().get();
       if (StringUtils.isEmpty(content)){
           content += "";
       }
       content += "\n";
        fileService.write( path, gatherId + ".log", content );
    }

}