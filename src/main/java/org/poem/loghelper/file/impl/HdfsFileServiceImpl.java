package org.poem.loghelper.file.impl;

import org.poem.loghelper.file.FileService;
import org.poem.utils.HdfsUtils;

/**
 * hdfs file service
 * @author Administrator
 */
public class HdfsFileServiceImpl implements FileService {


    /**
     *
     * @param path 文件路径
     * @param fileName 文件名字
     * @param content  文件内容
     */
    @Override
    public void write(String path, String fileName, String content) {
        HdfsUtils.uploadFile( path, fileName , content );
    }

    /**
     *
     * @param path 文件路径
     * @param fileName 文件名字
     * @return
     */
    @Override
    public String read(String path, String fileName) {
        return HdfsUtils.getFileContent( path, fileName);
    }
}
