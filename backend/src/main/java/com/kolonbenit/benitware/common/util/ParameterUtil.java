package com.kolonbenit.benitware.common.util;

/*
 * Copyright 2001-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the ";License&quot;);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.benitware.framework.http.parameter.RequestParameter;
import com.benitware.framework.xplaform.domain.ResultSetMap;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParameterUtil {
    /**
     * 빈 문자열 <code>""</code>.
     */
	public static List getParameterList(RequestParameter param , String key){
	    
		List list = new ArrayList();
		List rtnList = new ArrayList();
		
		String data = param.getString(key);
		
		String siteId = "";
		String userId = "";
		ResultSetMap userInfo = SessionUserUtils.getUserInfo();
		if(userInfo != null){ //사용자 정보 없으면 로그인 폼으로 포워드.
		
			userId = userInfo.get("userId").toString(); 
		}
		if(data!=null){
			ObjectMapper mapper = new ObjectMapper();
			try {
				list= mapper.readValue(data, new TypeReference<List<Map>>(){});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		for(int i=0; i<list.size(); i++){
			
			Map map = (Map) list.get(i);
			map.put("regId", userId);
			map.put("chgId", userId);
			rtnList.add(map);
		}
		
		return rtnList;
	}
	
    
}



