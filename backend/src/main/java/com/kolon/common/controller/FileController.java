package com.kolon.common.controller;

import com.kolon.common.prop.SystemPropertiesMap;
import com.kolon.common.view.FileDownView;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * FILE 컨트롤러
 */
//@Controller
public class FileController {
    private String rootPath = SystemPropertiesMap.getInstance().getValue("system.storage.file.path");


    /**
     * 파일다운로드
     */
    @RequestMapping(value = "/common/file/fileDownProc.do", method = RequestMethod.GET)
    public ModelAndView fileDown(HttpServletRequest request
            , HttpServletResponse response
            , ModelMap model
            , @RequestParam("fileName") String fileName
            , @RequestParam("fileOrgName") String fileOrgName
            , @RequestParam("subPath") String subPath) {

        String fullPath = rootPath +"/"+ subPath;

        if (!"".equals(fileName) && !"".equals(fileOrgName)){
            Map<String, String> paramMap = new HashMap<String, String>();

            paramMap.put(FileDownView.FILE_PARAM_FILE_PATH, fullPath);
            paramMap.put(FileDownView.FILE_PARAM_ORGINL_FILE_NAME, fileOrgName);
            paramMap.put(FileDownView.FILE_PARAM_UNIQ_FILE_NAME, fileName);
            model.addAttribute(FileDownView.FILE_PARAM, paramMap);
        }
        return new ModelAndView("fileDownView", model);
    }
}
