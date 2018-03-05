package com.kolon.comlife.admin.complexes.model;

import org.springframework.stereotype.Component;

@Component("complexConst")
public class ComplexConst {
    // 아래 상수 값은 COMPLEX_GRP_TYPE_M 테이블의 CMPLX_GRP_TYPE_ID 값과 동일해야 합니다.
    final static public int GRP_UNCATEGORIZED_ID = 0;  // 미분류: COMPLEX_GRP_TYPE_M.CMPLX_GRP_TYPE_ID = 0
    final static public int GRP_MINGAN_ID = 1;         // 민간: COMPLEX_GRP_TYPE_M.CMPLX_GRP_TYPE_ID = 1
    final static public int GRP_GONGGONG_ID = 2;        // 공공: COMPLEX_GRP_TYPE_M.CMPLX_GRP_TYPE_ID = 2

    final static public String GRP_UNCATEGORIZED = "uncategorized";
    final static public String GRP_MINGAN        = "mingan";
    final static public String GRP_GONGGONG       = "gonggong";


    public ComplexConst() {
        // do nothing
    }

    public int getGrpUncategorizedId() {
        return GRP_UNCATEGORIZED_ID;
    }

    public int getGrpMinganId() {
        return GRP_MINGAN_ID;
    }

    public int getGrpGonggongId() {
        return GRP_GONGGONG_ID;
    }

    public String getGrpUncategorized() {
        return GRP_UNCATEGORIZED;
    }

    public String getGrpMingan() {
        return GRP_MINGAN;
    }

    public String getGrpGonggong() {
        return GRP_GONGGONG;
    }
}
