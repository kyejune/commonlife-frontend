package com.kolon.comlife.admin.login.model;

import com.kolon.common.admin.model.BaseUserInfo;
import org.apache.ibatis.type.Alias;

/**
 * 로그인 Value Object
 * @author nacsde
 * @version 1.0
 * @see <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일        수정자           수정내용
 *  ----------   --------    ---------------------------
 *   2017-07-31    조신득          최초 생성
 * </pre>
 */
@Alias("loginInfo")
public class LoginInfo extends BaseUserInfo {
}
