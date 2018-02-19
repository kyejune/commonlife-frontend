/*
 * Copyright 2008-2009 MOPAS(Ministry of Public Administration and Security).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kolonbenit.benitware.common.util.uuid;

import java.math.BigDecimal;

public interface IIdGnrService {

	
	/**
	 * BigDecimal 형식의 Id 제공
	 * 
	 * @return 다음 ID
	 * @throws BaseException
	 *             다음 BigDecimal Id 를 얻지 못했을 경우
	 */
	BigDecimal getNextBigDecimalId() throws Exception;

	/**
	 * Long 형식의 Id 제공
	 * 
	 * @return 다음 ID
	 * @throws BaseException
	 *             유효한 Long Id의 범위를 벗어 났을 경우
	 */
	long getNextLongId() throws Exception;

	/**
	 * Integer 형식의 Id 제공
	 * 
	 * @return 다음 ID
	 * @throws FdlException
	 *             유효한 Integer Id의 범위를 벗어 났을 경우
	 */
	int getNextIntegerId() throws Exception;

	/**
	 * Short 형식의 Id 제공
	 * 
	 * @return 다음 ID
	 * @throws FdlException
	 *             유효한 Short Id의 범위를 벗어 났을 경우
	 */
	short getNextShortId() throws Exception;

	/**
	 * Byte 형식의 Id 제공
	 * 
	 * @return 다음 ID
	 * @throws FdlException
	 *             유효한 Byte Id의 범위를 벗어 났을 경우
	 */
	byte getNextByteId() throws Exception;

	/**
	 * String 형식의 Id 제공
	 * 
	 * @return 다음 ID
	 * @throws FdlException
	 *             유효한 String Id의 범위를 벗어 났을 경우
	 */
	String getNextStringId() throws Exception;

	/**
	 * 정책을 스트링으로 입력받고 String 형식의 Id 제공
	 * 
	 * @param strategyId
	 *            정책 String 정보
	 * @return 다음 ID
	 * @throws FdlException
	 *             유효한 String Id의 범위를 벗어 났을 경우
	 */
	String getNextStringId(String strategyId) throws Exception;

	
}
