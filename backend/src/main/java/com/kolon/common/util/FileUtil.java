package com.kolon.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtils extends org.apache.commons.io.FileUtils
{
    protected static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    protected static final String CLASS_NAME = "FileUtils";
    public static final int BUFFER_SIZE = 8192;
    public static final String SEPERATOR = File.separator;

    public static void createDirectory(String rootPath)
            throws Exception
    {
        File dFile = new File(rootPath);
        if ((!dFile.isDirectory()) &&
                (!dFile.mkdir()))
        {
            logger.error("createDirectory error!");
            throw new Exception("createDirectory error!");
        }
    }

    public static void deleteDirectory(String rootPath, String directory)
    {
        try
        {
            File file = new File(rootPath, directory);
            if (file.exists()) {
                deleteDirectory(file);
            }
        }
        catch (Exception ex)
        {
            logger.error("");
        }
    }

    public static void deleteDirectory(File file)
    {
        if (!file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                deleteDirectory(files[i]);
            } else {
                files[i].delete();
            }
        }
        file.delete();
    }

    public static boolean deleteFile(String filePath)
    {
        File file = null;
        File delFile = null;
        boolean isDelete = false;
        try
        {
            file = new File(filePath);
            if (!file.exists()) {
                return false;
            }
            if (file.isFile())
            {
                file.delete();
                return true;
            }
            String[] files = file.list();
            for (int i = 0; i < files.length; i++)
            {
                delFile = new File(filePath, files[i]);
                delFile.delete();
            }
            if (file.exists())
            {
                if (file.delete()) {
                    isDelete = true;
                } else {
                    logger.info("File deletion failed : " + filePath);
                }
            }
            else {
                logger.info("Not exist File : " + filePath);
            }
        }
        catch (Exception e)
        {
            logger.error("File Delete Exception : " + filePath, e);
        }
        return isDelete;
    }

    public static void deleteFile(String realPath, String fileName)
    {
        try
        {
            File tempFile = new File(realPath, fileName);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
        catch (Exception ex)
        {
            logger.error("파일 삭제 처리에서 에러가 발생했습니다.");
        }
    }

    public static List<String> getFileList(String dirPath, String prefix)
    {
        List<String> fileNameList = new ArrayList();
        try
        {
            File dir = new File(dirPath);
            if (!dir.exists()) {
                return null;
            }
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if ((!files[i].isDirectory()) && (files[i].getName().startsWith(prefix))) {
                    fileNameList.add(files[i].getName());
                }
            }
        }
        catch (Exception ex)
        {
            logger.error("파일 삭제 처리에서 에러가 발생했습니다.");
        }
        return fileNameList;
    }

    public static List<String> getFileList(String dirPath, String prefix, String except)
    {
        List<String> fileNameList = new ArrayList();
        try
        {
            fileNameList = getFileList(dirPath, prefix);
            for (int i = 0; i < fileNameList.size(); i++) {
                if (except.equals(fileNameList.get(i)))
                {
                    fileNameList.remove(i);
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            logger.error("파일 삭제 처리에서 에러가 발생했습니다.");
        }
        return fileNameList;
    }

    public static String getUniqueFileName(String realPath, String fileName)
    {
        fileName = StringUtils.replace(fileName, " ", "");
        File tempFile = new File(realPath, fileName);
        int idx = 0;
        int cnt = 1;
        String temp = fileName;
        while (tempFile.exists())
        {
            if ((idx = fileName.indexOf(".")) != -1)
            {
                String left = fileName.substring(0, idx);
                String right = fileName.substring(idx);
                temp = left + "[" + cnt + "]" + right;
            }
            else
            {
                temp = fileName + "[" + cnt + "]";
            }
            tempFile = new File(realPath, temp);
            cnt++;
        }
        fileName = temp;

        return fileName;
    }

    public static void fileMove(String inFileName, String outFileName)
    {
        try
        {
            FileInputStream fis = new FileInputStream(inFileName);
            FileOutputStream fos = new FileOutputStream(outFileName);

            int data = 0;
            while ((data = fis.read()) != -1) {
                fos.write(data);
            }
            fis.close();
            fos.close();

            File I = new File(inFileName);
            I.delete();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList listInDir(String path, String extention)
    {
        File dir = new File(path);
        if (!dir.isDirectory()) {
            return null;
        }
        File[] allList = dir.listFiles();
        ArrayList list = new ArrayList();
        String name = null;
        String ext = null;
        int extLength = extention.length();
        for (int i = 0; i < allList.length; i++)
        {
            if (allList[i].isDirectory()) {
                list.addAll(listInDir(allList[i].getPath(), extention));
            }
            name = allList[i].getName();
            if (name.length() > extLength)
            {
                ext = name.substring(name.length() - extLength, name.length());
                if ((ext.equalsIgnoreCase(extention)) || (extention == "*")) {
                    list.add(allList[i]);
                }
            }
        }
        return list;
    }

    public static String filePathBlackList(String value)
    {
        String returnValue = value;
        if ((returnValue == null) || (returnValue.trim().equals(""))) {
            return "";
        }
        returnValue = returnValue.replaceAll("\\.\\./", "");
        returnValue = returnValue.replaceAll("\\.\\.\\\\", "");

        return returnValue;
    }

    public static String getPhysicalFileName()
    {
        //return BaseUUIDUtil.randomUUID().toString().replaceAll("-", "").toUpperCase();
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

}