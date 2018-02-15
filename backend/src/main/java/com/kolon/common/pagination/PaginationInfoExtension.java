package com.kolon.common.pagination;

import com.kolon.common.model.BaseInfo;
import com.kolon.common.util.NumberUtil;

import java.util.List;
import java.util.Map;


public class PaginationInfoExtension<T>
        extends PaginationInfo
{
    private BaseInfo baseVO;
    private Map<String, Object> paramMap;
    private String defaultPageEnable;
    private int defaultRecordCountPerPage;
    private int defaultPageSize;

    public PaginationInfoExtension(Object o)
    {
        this(o, "1", 10, 10);
    }

    public PaginationInfoExtension(Map<String, Object> paramMap)
    {
        this(paramMap, "1", 10, 10);
    }

    public PaginationInfoExtension(Object o, String defaultPageEnable, int defaultRecordCountPerPage, int defaultPageSize)
    {
        this.defaultPageEnable = defaultPageEnable;
        this.defaultRecordCountPerPage = defaultRecordCountPerPage;
        this.defaultPageSize = defaultPageSize;

        this.baseVO = ((BaseInfo)o);
        setCurrentPageNo(this.baseVO.getPageIndex());
        if (this.baseVO.getRecordCountPerPage() <= 0) {
            setRecordCountPerPage(this.defaultRecordCountPerPage);
        } else {
            setRecordCountPerPage(this.baseVO.getRecordCountPerPage());
        }
        if (this.baseVO.getPageSize() <= 0) {
            setPageSize(this.defaultPageSize);
        } else {
            setPageSize(this.baseVO.getPageSize());
        }
    }

    public PaginationInfoExtension(Map<String, Object> paramMap, String defaultPageEnable, int defaultRecordCountPerPage, int defaultPageSize)
    {
        this.defaultPageEnable = defaultPageEnable;
        this.defaultRecordCountPerPage = defaultRecordCountPerPage;
        this.defaultPageSize = defaultPageSize;

        this.paramMap = paramMap;
        setCurrentPageNo(NumberUtil.objToInt(paramMap.get("pageIndex")));
        if (NumberUtil.objToInt(paramMap.get("recordCountPerPage")) <= 0) {
            setRecordCountPerPage(this.defaultRecordCountPerPage);
        } else {
            setRecordCountPerPage(NumberUtil.objToInt(paramMap.get("recordCountPerPage")));
        }
        if (NumberUtil.objToInt(paramMap.get("pageSize")) <= 0) {
            setPageSize(this.defaultPageSize);
        } else {
            setPageSize(NumberUtil.objToInt(paramMap.get("pageSize")));
        }
    }

    public Object createPaginationInfo()
    {
        return this;
    }

    public BaseInfo createCustomVO()
    {
        this.baseVO.setFirstIndex(getFirstRecordIndex());
        this.baseVO.setLastIndex(getLastRecordIndex());
        if (this.baseVO.getRecordCountPerPage() <= 0) {
            this.baseVO.setRecordCountPerPage(this.defaultRecordCountPerPage);
        }
        if (this.baseVO.getPageSize() <= 0) {
            this.baseVO.setPageSize(this.defaultPageSize);
        }
        return this.baseVO;
    }

    public Object createCustomMap()
    {
        this.paramMap.put("firstIndex", Integer.valueOf(getFirstRecordIndex()));
        this.paramMap.put("lastIndex", Integer.valueOf(getLastRecordIndex()));
        if (NumberUtil.objToInt(this.paramMap.get("recordCountPerPage")) <= 0) {
            this.paramMap.put("recordCountPerPage", Integer.valueOf(this.defaultRecordCountPerPage));
        }
        if (NumberUtil.objToInt(this.paramMap.get("pageSize")) <= 0) {
            this.paramMap.put("pageSize", Integer.valueOf(this.defaultPageSize));
        }
        return this.paramMap;
    }

    public void setTotalRecordCountVO(List<? extends BaseInfo> list)
    {
        int totalCnt = 0;
        if ((list != null) &&
                (list.size() > 0)) {
            totalCnt = ((BaseInfo)list.get(0)).getTot();
        }
        super.setTotalRecordCount(totalCnt);
    }

    public void setTotalRecordCountMap(List<Map<String, Object>> list)
    {
        int totalCnt = 0;
        if ((list != null) &&
                (list.size() > 0)) {
            totalCnt = NumberUtil.objToInt(((Map)list.get(0)).get("TOT"));
        }
        super.setTotalRecordCount(totalCnt);
    }
}
