package org.poem.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.fusesource.hawtbuf.BufferInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class HdfsUtils {

    private static final Logger logger = LoggerFactory.getLogger( HdfsUtils.class );

    private static String ip;

    private static String port;

    /**
     * 获取文件的内容
     *
     * @param path
     * @param file
     * @param source
     * @return
     */
    public static String getFileContent(String path, String file) {
        Configuration configuration = new Configuration();
        configuration.set( "fs.defaultFS", "hdfs://" + ip + ":" + port );
        configuration.set( "dfs.client.use.datanode.hostname", "true" );
        configuration.setBoolean( "dfs.support.append", true );
        OutputStream ot = null;
        StringBuilder stringBuffer = new StringBuilder();
        FSDataInputStream out = null;
        String pat = "hdfs://" + ip + ":" + port + "/" + path + "/" + file;
        Path hdfsPath = new Path( pat );
        try {
            FileSystem fs = FileSystem.get( configuration );
            if (!fs.exists( hdfsPath )) {
                logger.warn( "file [" + pat + "] not exist !!!!!" );
                return null;
            }
            out = fs.open( hdfsPath );
            int capacity = 1024 << 4;
            ByteBuffer bf = ByteBuffer.allocate( capacity );
            int le = 0;
            while ((le = out.read( bf )) > 0) {
                stringBuffer.append( new String( bf.array(), 0, le ) );
            }
        } catch (IOException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly( ot );
            org.apache.commons.io.IOUtils.closeQuietly( out );
        }
        return stringBuffer.toString();

    }

    /**
     * 上传本地文件到 HDFS
     *
     * @param source
     */
    public static void uploadFile(String path, String file, String source) {
        Configuration configuration = new Configuration();
        configuration.set( "fs.defaultFS", "hdfs://" + ip + ":" + port );
        configuration.set( "dfs.client.use.datanode.hostname", "true" );
        configuration.setBoolean( "dfs.support.append", true );
        OutputStream ot = null;
        InputStream is1 = null;
        FSDataOutputStream out = null;
        String pat = "hdfs://" + ip + ":" + port + "/" + path + "/" + file;
        Path hdfsPath = new Path( pat );
        try {
            FileSystem fs = FileSystem.get( configuration );
            if (!fs.exists( hdfsPath )) {
                logger.info( "create hdfs :" + pat );
                ot = fs.create( hdfsPath );
            }

            is1 = new BufferedInputStream( new BufferInputStream( source.getBytes() ) );
            out = fs.append( hdfsPath );
            IOUtils.copyBytes( is1, out, configuration );
        } catch (IOException e) {
            logger.error( e.getMessage(), e );
            e.printStackTrace();
        } finally {
            org.apache.commons.io.IOUtils.closeQuietly( ot );
        }
    }
}
