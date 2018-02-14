package com.kolon.common.servlet;

import com.kolon.common.prop.SystemPropertiesMap;
import com.kolon.common.util.DateUtils;
import com.kolon.common.util.FileUtils;
import com.kolon.common.util.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class FileUploadServlet
        extends HttpServlet
{
    private static final long serialVersionUID = 2985219151871053427L;
    public static final org.slf4j.Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        doPost(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
        try
        {
            performTask(req, resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new ServletException(e.getMessage());
        }
    }

    protected void performTask(HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        this.logger.info("## fileUploadServlet Logging");

        List<Map<String, Object>> paramList = new ArrayList();
        //BaseUserInfo baseUserInfo = (BaseUserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
        List<FileItem> fileItems = sfu.parseRequest(request);

        if (fileItems == null) {
            this.logger.info("## file not found");
        }

        Iterator iter = fileItems.iterator();
        FileItem actual = null;
        File fichero = null;
        //String uuid = baseUserInfo.getUuid();
        String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        String tempPath = SystemPropertiesMap.getInstance().getValue("system.temp.file.path");
        String uploadPath = SystemPropertiesMap.getInstance().getValue("system.storage.file.path");
        String fileSubPath = DateUtils.getToday().substring(0, 6);
        iter = fileItems.iterator();
        int sttsCode = 0;



        this.logger.info("## fileUploadServlet fileItems.size : {}", fileItems.size());
        this.logger.info("## fileUploadServlet While Start");

        while (iter.hasNext())
        {
            this.logger.info("## fileUploadServlet While IN");
            actual = (FileItem)iter.next();
            if (!actual.isFormField()) {
                Map<String, Object> paramMap = new HashMap();

                String fullFileName = actual.getName();
                String baseFileName = FilenameUtils.getName(fullFileName);
                String fileExtn = FilenameUtils.getExtension(fullFileName).toLowerCase();
                String physicalFileName = FileUtils.getPhysicalFileName() + "." + fileExtn;

                paramMap.put("contentType", actual.getContentType());
                paramMap.put("orginlFileNm", baseFileName);
                paramMap.put("streFileNm", physicalFileName);
                paramMap.put("fileSize", Long.valueOf(actual.getSize()));
                paramMap.put("fileExtsn", fileExtn);
                paramMap.put("fileSubPath", fileSubPath);

                int MAX_FILE_SIZE = 10485760; // 10메가

                if(Long.valueOf(actual.getSize()) > MAX_FILE_SIZE){
                    sttsCode = 405;
                    paramMap.put("sttsCode", "405");
                    paramMap.put("actionMessage", "첨부 가능한 최대 용량이 초과 하였습니다.\n파일 최대 사이즈는 " + Math.round(MAX_FILE_SIZE / 1024 / 1024) + "MB 입니다.");
                    this.logger.info("## 업로드가 불가능한 확장자 파일 입니다. = " + fullFileName);
                }
                else if (!extCheck(fileExtn))
                {
                    sttsCode = 405;
                    paramMap.put("sttsCode", "405");
                    paramMap.put("actionMessage", baseFileName+"는 업로드가 불가능한 확장자 파일 입니다.");
                    this.logger.info("## 업로드가 불가능한 확장자 파일 입니다. = " + fullFileName);
                }
                else
                {
                    //String filePath = FilenameUtils.separatorsToSystem(getServletContext().getRealPath("")) + tempPath + File.separator + uuid;
                    //String filePath = uploadPath + File.separator + uuid;
                    String filePath = uploadPath + File.separator + fileSubPath;


                    File tempFilePath = new File(filePath);

                    if (!tempFilePath.isDirectory()) {
                        tempFilePath.mkdirs();
                    }

                    fichero = new File(tempFilePath.getPath() + File.separator + physicalFileName);
                    actual.write(fichero);

                    paramMap.put("sttsCode", "200");
                    this.logger.info("## fileName = " + fullFileName);
                    this.logger.info("## filePath = " + fichero.getPath());

                    sttsCode = 200;
                }
                paramList.add(paramMap);
            }
        }

        this.logger.info("## fileUploadServlet While End ");
        Map<String, Object> returnParam = new HashMap();
        returnParam.put("atchFileList", paramList);
        WebUtils.setAjaxAction(response, sttsCode, returnParam);
    }

    private boolean extCheck(String fileExt)
    {
        boolean returnFlag = true;
        String fileNotExtension = SystemPropertiesMap.getInstance().getValue("system.file.attch.extsn.lmtt");
        if (StringUtils.isNotBlank(fileNotExtension))
        {
            String[] checkExts = fileNotExtension.split(",");
            for (String notExt : checkExts) {
                if (fileExt.toUpperCase().equals(notExt.toUpperCase()))
                {
                    returnFlag = false;
                    break;
                }
            }
        }
        else
        {
            this.logger.info("## 파일업로드 제한 설정이 존재하지 않습니다. [system.storage.notExtension]");
        }
        return returnFlag;
    }
}