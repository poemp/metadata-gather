package org.poem.loghelper.file.impl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.poem.loghelper.file.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.attribute.FileAttribute;

/**
 * @author Administrator
 */
@Service
@ConditionalOnProperty(name = "logging.gather.storage",havingValue = "LOCAL")
public class LocalFileServiceImpl implements FileService {


    private static final FileAttribute<?>[] NO_ATTRIBUTES = new FileAttribute[0];

    private static final Logger logger = LoggerFactory.getLogger( LocalFileServiceImpl.class );

    /**
     * @param path     文件路径
     * @param fileName 文件名字
     * @param content  文件内容
     */
    @Override
    public void write(String path, String fileName, String content) {
        if (StringUtils.isBlank( content )) {
            logger.warn( "content is empty" );
            return;
        }
        if (StringUtils.isBlank( path )){
            path = System.getProperty("user.dir");
        }
        ByteArrayInputStream fin = null;
        FileOutputStream fout = null;
        FileChannel fic = null;
        FileChannel foc = null;
        try {
            if (!new File( path ).exists()) {
                Files.createDirectory( new File( path ).toPath(), NO_ATTRIBUTES );
            }
            File dst = new File( path + File.separator + fileName );
            if (!dst.exists()) {
                Files.createFile( dst.toPath(), NO_ATTRIBUTES );
            }
            fin = new ByteArrayInputStream( content.getBytes() );
            fout = new FileOutputStream( dst , true);
            foc = fout.getChannel();
            // 16KB缓冲区
            byte[] bb = new byte[1024 << 4];
            // 根据 read返回实际读出的字节数 中止循环
            int len;
            do {
                len = fin.read( bb );
                foc.write( ByteBuffer.wrap( bb, 0, len ) );
            } while (fin.read( bb ) > 0);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error( e.getMessage(), e );
        } finally {
            IOUtils.closeQuietly( fin );
            IOUtils.closeQuietly( fout );
            IOUtils.closeQuietly( fic );
            IOUtils.closeQuietly( foc );
        }
    }

    /**
     * @param path     文件路径
     * @param fileName 文件名字
     * @return
     */
    @Override
    public String read(String path, String fileName) {
        if (StringUtils.isBlank( path )){
            path = System.getProperty("user.dir");
        }
        if (!new File( path ).exists()) {
            logger.warn( "[" + path + "] not exist!!!!!!" );
            return "";
        }
        File file = new File( path + File.separator + fileName );
        if (!file.exists()) {
            logger.warn( "file[" + file.getAbsolutePath() + "] not exists!!!!" );
            return "";
        }
        StringBuilder content = new StringBuilder(  );
        FileInputStream fin = null;
        try {
            fin = new FileInputStream( file);
            FileChannel channel = fin.getChannel();
            // 字节
            int capacity = 1024 << 4;
            ByteBuffer bf = ByteBuffer.allocate( capacity );
            int length = -1;
            while ((length = channel.read( bf )) != -1) {
                bf.clear();
                byte[] bytes = bf.array();
                content.append( new String( bytes, 0, length ) );
            }
            channel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error( e.getMessage(),e );
        } catch (IOException e) {
            e.printStackTrace();
            logger.error( e.getMessage(),e );
        } finally {
            if (fin != null) {
                try {
                    fin.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
}
