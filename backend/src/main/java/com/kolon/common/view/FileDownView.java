package com.kolon.common.view;

import com.kolon.common.prop.PropertiesMap;
import com.kolon.common.util.FileUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class FileDownView extends AbstractView {
    private Logger logger = LoggerFactory.getLogger(FileDownView.class);
    public static final String FILE_PARAM = "storageFile";
    public static final String FILE_PARAM_ORGINL_FILE_NAME = "orignlFileNm";
    public static final String FILE_PARAM_UNIQ_FILE_NAME = "uniqFileNm";
    public static final String FILE_PARAM_FILE_PATH = "filePath";
    public static final String FILE_PARAM_FILE_LOCATION = "fileLocation";
    public static final String FILE_PARAM_FILE_DELETE = "fileDelete";
    public static final String FILE_DELETE_ENABLE = "Y";
    public static final String FOLDER_DELETE_ENABLE = "Y";
    private String fileDownEncoding = PropertiesMap.getInstance().getValue("system.file.down.encoding");

    protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
        String fileNM = "";
        String uniqFileNM = "";
        String filePath = "";
        String fileNotFoundMsg = "The file does not exist";
        String filePathErrorMsg = "File information is incorrect";

        Map<String, String> paramMap = (Map)model.get("storageFile");
        ServletOutputStream outputStream = null;
        FileInputStream fis = null;
        if (paramMap == null)
        {
            paramMap = new HashMap();
            paramMap.put("uniqFileNm", request.getParameter("uniqFileNm"));
            paramMap.put("filePath", request.getParameter("filePath"));
            paramMap.put("fileLocation", request.getParameter("fileLocation"));
            paramMap.put("fileDelete", request.getParameter("fileDelete"));
            paramMap.put("Y", request.getParameter("Y"));
        }
        fileNM = (String)paramMap.get("orignlFileNm");
        uniqFileNM = (String)paramMap.get("uniqFileNm");
        filePath = (String)paramMap.get("filePath") + File.separator;
        String fileEncoding = StringUtils.isEmpty(this.fileDownEncoding) ? "UTF-8" : this.fileDownEncoding;
        String fileDelete = (String)paramMap.get("fileDelete");
        String folderDelete = (String)paramMap.get("Y");

        this.logger.info("##FileDownView [fileName] = " + fileNM);
        this.logger.info("##FileDownView [uniqFileName] = " + uniqFileNM);
        this.logger.info("##FileDownView [filePath] = " + filePath);
        this.logger.info("##FileDownView [fileEncoding] = " + fileEncoding);
        this.logger.info("##FileDownView [fileDelete] = " + fileDelete);
        this.logger.info("##FileDownView [folderDelete] = " + folderDelete);

        response.setHeader("Content-Type", "text/html; charset=" + fileEncoding);
        if ((!StringUtils.isNotBlank(fileNM)) || (!StringUtils.isNotBlank(uniqFileNM)) || (!StringUtils.isNotBlank(filePath)))
        {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + fileNotFoundMsg + "');history.back();</script>");
            return;
        }
        if (fileNM.lastIndexOf("../") > 0)
        {
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + filePathErrorMsg + "');history.back();</script>");
            return;
        }
        try
        {
            File tempFile = new File(filePath + File.separator + uniqFileNM);

            int filesize = (int)tempFile.length();
            this.logger.info("## fullFilePath = " + tempFile.getPath());
            this.logger.info("## fileExists = " + tempFile.exists());
            try
            {
                this.logger.info("tempFile.canRead() = " + tempFile.canRead());
                if ((!tempFile.exists()) || (!tempFile.canRead()))
                {
                    PrintWriter out = response.getWriter();
                    this.logger.info("## " + fileNotFoundMsg);
                    out.println("<script>alert('" + fileNotFoundMsg + "');history.back();</script>");
                    if (fis != null) {
                        fis.close();
                    }
                    if (outputStream != null) {
                        outputStream.flush();
                    }
                    if ((fileDelete != null) && ("Y".equalsIgnoreCase(fileDelete))) {
                        new File(filePath + File.separator + uniqFileNM).delete();
                    }
                    if ((folderDelete != null) && ("Y".equalsIgnoreCase(folderDelete))) {
                        FileUtil.deleteDirectory(new File(filePath));
                    }
                    return;
                }
            }
            catch (Exception e)
            {
                PrintWriter out = response.getWriter();
                this.logger.error("{}", e);
                this.logger.info("## " + fileNotFoundMsg);
                out.println("<script>alert('" + fileNotFoundMsg + "');history.back();</script>");
                if (fis != null) {
                    fis.close();
                }
                if (outputStream != null) {
                    outputStream.flush();
                }
                if ((fileDelete != null) && ("Y".equalsIgnoreCase(fileDelete))) {
                    new File(filePath + File.separator + uniqFileNM).delete();
                }
                if ((folderDelete != null) && ("Y".equalsIgnoreCase(folderDelete))) {
                    FileUtil.deleteDirectory(new File(filePath));
                }
                return;
            }
            String fileExt = FilenameUtils.getExtension(fileNM);
            if (fileExt.trim().equalsIgnoreCase("txt")) {
                response.setContentType("text/plain");
            } else if (fileExt.trim().equalsIgnoreCase("doc")) {
                response.setContentType("application/msword");
            } else if (fileExt.trim().equalsIgnoreCase("docx")) {
                response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
            } else if (fileExt.trim().equalsIgnoreCase("xls")) {
                response.setContentType("application/vnd.ms-excel");
            } else if (fileExt.trim().equalsIgnoreCase("xlsx")) {
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            } else if (fileExt.trim().equalsIgnoreCase("pdf")) {
                response.setContentType("application/pdf");
            } else if (fileExt.trim().equalsIgnoreCase("ppt")) {
                response.setContentType("application/vnd.ms-powerpoint");
            } else if (fileExt.trim().equalsIgnoreCase("pptx")) {
                response.setContentType("application/vnd.openxmlformats-officedocument.presentationml.presentation");
            } else if (fileExt.trim().equalsIgnoreCase("hwp")) {
                response.setContentType("application/hwp");
            } else {
                response.setContentType("application/octet-stream");
            }
            String browser = request.getHeader("User-Agent");

            if ((browser.contains("MSIE")) || (browser.contains("Trident")) || (browser.contains("Chrome"))) {
                this.logger.info("##FileDownView [encode] = MSIE, Trident, Chrome");
                //fileNM = URLEncoder.encode(fileNM, "UTF-8").replaceAll("\\+", "%20");
                fileNM = URLEncoder.encode(fileNM, "ISO-8859-1").replaceAll("\\+", "%20");
            } else {
                this.logger.info("##FileDownView [encode] = UTF-8, ISO-8859-1");
                fileNM = new String(fileNM.getBytes("UTF-8"), "ISO-8859-1");
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileNM + "\"");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setContentLength(filesize);
            response.setHeader("cache-control", "no-cache");

            fis = new FileInputStream(tempFile);
            outputStream = response.getOutputStream();
            FileCopyUtils.copy(fis, outputStream);
        }
        catch (Exception e)
        {
            PrintWriter out = response.getWriter();
            response.setHeader("Content-Type", "text/html; charset=UTF-8");
            out.println("<script>alert('" + fileNotFoundMsg + "');history.back();</script>");

            this.logger.error("{}",e);
            this.logger.info("## " + fileNotFoundMsg);
        }
        finally
        {
            if (fis != null) {
                fis.close();
            }
            if (outputStream != null) {
                outputStream.flush();
            }
            if ((fileDelete != null) && ("Y".equalsIgnoreCase(fileDelete))) {
                new File(filePath + File.separator + uniqFileNM).delete();
            }
            if ((folderDelete != null) && ("Y".equalsIgnoreCase(folderDelete))) {
                FileUtil.deleteDirectory(new File(filePath));
            }
        }
    }
}
