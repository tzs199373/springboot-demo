package com.commonutils.util.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    /**
     * 压缩指定文件或文件夹
     *
     * @param zipParentPath    压缩后文件存储路径
     * @param zipFilename       压缩文件名
     * @param afterDelete       压缩后源文件或文件夹是否删除
     * @param sourceFiles       源文件或文件夹
     *
     *tips:
     *1、如果压缩后不需要根目录，可以把源文件夹所有第一级子目录，作为入参：sourceFiles = file.listFiles()
     */
    public static void compressToZip(String zipParentPath, String zipFilename,boolean afterDelete,File ... sourceFiles) {
        File zipParentFile = new File(zipParentPath);
        if (!zipParentFile.exists()) {
            zipParentFile.mkdirs();
        }
        File zipFile = new File(zipParentPath + File.separator + zipFilename);
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile))) {
            for (File sourceFile:sourceFiles) {
                writeZip(sourceFile, "", zos);
                if(afterDelete){
                    boolean flag = deleteDir(sourceFile);
                    logger.info("删除被压缩文件[" + sourceFile + "]标志：{}", flag);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e.getCause());
        }
    }

    /**
     * 遍历所有文件，压缩
     * @param sourceFile        源文件目录
     * @param parentPath        压缩文件目录
     * @param zos               文件流
     */
    private static void writeZip(File sourceFile, String parentPath, ZipOutputStream zos) {
        if (sourceFile.isDirectory()) {
            //目录
            parentPath += sourceFile.getName() + File.separator;
            File[] files = sourceFile.listFiles();
            for (File f : files) {
                writeZip(f, parentPath, zos);
            }
        } else {
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(sourceFile))) {
                ZipEntry zipEntry = new ZipEntry(parentPath + sourceFile.getName());
                zos.putNextEntry(zipEntry);
                int len;
                byte[] buffer = new byte[1024 * 10];
                while ((len = bis.read(buffer, 0, buffer.length)) != -1) {
                    zos.write(buffer, 0, len);
                    zos.flush();
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        }
    }

    /**
     * 删除文件夹
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        //删除空文件夹
        return dir.delete();
    }

}

