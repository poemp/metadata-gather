package org.poem.loghelper.file;

/**
 * 文件
 */
public interface FileService {


    /**
     * 写入文件的信息
     * @param path 文件路径
     * @param fileName 文件名字
     * @param content  文件内容
     */
    void  write(String path, String fileName, String content);


    /**
     * 获取日志文件的内容
     * @param path 文件路径
     * @param fileName 文件名字
     * @return 文件内容
     */
    String read(String path, String fileName);
}
