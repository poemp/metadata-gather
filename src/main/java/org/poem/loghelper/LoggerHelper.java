package org.poem.loghelper;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.poem.loghelper.file.FileService;
import org.poem.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 日志管理
 *
 * @author Administrator
 */
@Data
@Service
public class LoggerHelper {

    @Autowired
    private FileService fileService;

    @Value( "${logging.gather.storage}" )
    private static String storage;

    @Value( "${logging.gather.path}" )
    private static String path;

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