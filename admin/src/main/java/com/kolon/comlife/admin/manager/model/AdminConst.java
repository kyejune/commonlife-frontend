package com.kolon.comlife.admin.manager.model;

import org.springframework.stereotype.Component;

@Component("adminConst")
public class AdminConst {
    // 아래 상수 값은 CL_ADMIN_GRP 테이블의 관리자 그룹 아이디와 동일해야 합니다. GRP_ID
    final static public int ADMIN_GRP_SUPER   = 0; // CL_ADMIN_GRP.GRP_ID == 0
    final static public int ADMIN_GRP_COMPLEX = 1; // CL_ADMIN_GRP.GRP_ID == 1

    public AdminConst() {
        // do nothing
    }

    public int getAdminGrpSuper() {
        return ADMIN_GRP_SUPER;
    }

    public int getAdminGrpComplex() {
        return ADMIN_GRP_COMPLEX;
    }
}
