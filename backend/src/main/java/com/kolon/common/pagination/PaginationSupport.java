package com.kolon.common.pagination;

import com.kolon.common.model.BaseInfo;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class PaginationSupport
{
    public static PaginationInfoExtension setPaginationVO(HttpServletRequest request, BaseInfo paramVO, String pageEnable, int recordCount, int pageSize)
    {
        return setPaginationVO(request, paramVO, pageEnable, recordCount, pageSize, paramVO.getCondOrder(), paramVO.getCondAlign());
    }

    public static PaginationInfoExtension setPaginationVO(HttpServletRequest request, BaseInfo paramVO, String pageEnable, int recordCount, int pageSize, String condOrder, String condAlign)
    {
        pageSize = 5;

        paramVO.setPagingEnable(StringUtils.isNotEmpty(request.getParameter("pagingEnable")) ? paramVO.getPagingEnable() : pageEnable);
        paramVO.setRecordCountPerPage(StringUtils.isNotEmpty(request.getParameter("recordCountPerPage")) ? paramVO.getRecordCountPerPage() : recordCount);
        paramVO.setPageSize(StringUtils.isNotEmpty(request.getParameter("pageSize")) ? paramVO.getPageSize() : pageSize);

        paramVO.setCondOrder(StringUtils.isNotEmpty(request.getParameter("condOrder")) ? paramVO.getCondOrder() : condOrder);

        paramVO.setCondAlign(StringUtils.isNotEmpty(request.getParameter("condAlign")) ? paramVO.getCondAlign() : condAlign);

        PaginationInfoExtension pagination = new PaginationInfoExtension(paramVO);
        pagination.createCustomVO();

        return pagination;
    }

    public static PaginationInfoExtension setPaginationMap(HttpServletRequest request, Map<String, Object> paramMap, String pageEnable, int recordCount, int pageSize, String condOrder, String condAlign)
    {
        paramMap.put("pageIndex", StringUtils.isNotEmpty(request.getParameter("pageIndex")) ? paramMap.get("pageIndex") : Integer.valueOf(1));
        paramMap.put("pagingEnable", StringUtils.isNotEmpty(request.getParameter("pagingEnable")) ? paramMap.get("pagingEnable") : pageEnable);
        paramMap.put("recordCountPerPage", StringUtils.isNotEmpty(request.getParameter("recordCountPerPage")) ? paramMap.get("recordCountPerPage") : Integer.valueOf(recordCount));
        paramMap.put("pageSize", StringUtils.isNotEmpty(request.getParameter("pageSize")) ? paramMap.get("pageSize") : Integer.valueOf(pageSize));

        paramMap.put("condOrder", StringUtils.isNotEmpty(request.getParameter("condOrder")) ? paramMap.get("condOrder") : condOrder);

        paramMap.put("condAlign", StringUtils.isNotEmpty(request.getParameter("condAlign")) ? paramMap.get("condAlign") : condAlign);

        PaginationInfoExtension pagination = new PaginationInfoExtension(paramMap);
        pagination.createCustomMap();

        return pagination;
    }
}