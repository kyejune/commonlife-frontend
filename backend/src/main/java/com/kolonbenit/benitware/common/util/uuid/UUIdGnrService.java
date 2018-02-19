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

import com.kolonbenit.benitware.framework.exception.FrameworkException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;


public class UUIdGnrService  implements IIdGnrService,
        ApplicationContextAware {
	
	/**
	 * Message Source
	 */
	private MessageSource messageSource;

	/**
	 * Message Source Injection
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.messageSource = (MessageSource) applicationContext
				.getBean("messageSource");
	}

	/**
	 * Class 사용 로거 지정
	 */
	private static Log logger = LogFactory.getLog(UUIdGnrService.class);

	private static final String ERROR_STRING = "address in the configuration should be a valid IP or MAC Address";

	/**
	 * Address Id
	 */
	private String mAddressId;

	/**
	 * UUID 생성에 필요한 Time Factor에 의한 ID
	 */
	private String mTimeId;

//	사용자 지정 Address
//	- PMD error report: Avoid unused private fields such as 'address'
//	private String address;

	/** 
	 * Loop Counter
	 */
	private short mLoopCounter = 0;

	/**
	 * BigDecimal 타입을 아이디 제공
	 * 
	 * @return BigDecimal 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */
	public BigDecimal getNextBigDecimalId() throws FrameworkException {
		String newId = getNextStringId();
		byte[] bytes = newId.getBytes(); // get 16 bytes
		BigDecimal bd = BigDecimal.ZERO;

		for (int i = 0; i < bytes.length; i++) {
			bd = bd.multiply(new BigDecimal(256));
			bd = bd.add(new BigDecimal(((int) bytes[i]) & 0xFF));
		}

		return bd;
	}

	/**
	 * byte 타입을 아이디 제공
	 * 
	 * @return byte 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */
	public byte getNextByteId() throws FrameworkException {
		throw new FrameworkException(new Exception(),"error.idgnr.not.supported","byte");
//		try{
//			int val = 0/0;
//		}catch(Exception e){
//			throw new FrameworkException(e,"error.idgnr.not.supported","byte"); 
//		}
//		return 0;
	}

	/** 
	 * int 타입을 아이디 제공을 요청하면 불가능한 요청이라는 Exception 발생
	 * 
	 * @return int 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */
	public int getNextIntegerId() throws FrameworkException {
		throw new FrameworkException(new Exception(),"error.idgnr.not.supported","int"); 
	}

	/**
	 * long 타입을 아이디 제공을 요청하면 불가능한 요청이라는 Exception 발생
	 * 
	 * @return long 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */
	public long getNextLongId() throws FrameworkException {
		throw new FrameworkException(new Exception(),"error.idgnr.not.supported","long"); 
	}

	/**
	 * short 타입을 아이디 제공을 요청하면 불가능한 요청이라는 Exception 발생
	 * 
	 * @return short 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */
	public short getNextShortId() throws FrameworkException {
		throw new FrameworkException(new Exception(),"error.idgnr.not.supported","short"); 
	}

	/**
	 * String 타입을 아이디 제공
	 * 
	 * @return String 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */
	public String getNextStringId() throws FrameworkException {
		return getUUId();
	}

	
	/**
	 * 정책정보를 입력받아 String 타입을 아이디 제공을 요청하면 불가능한 요청이라는 에러 발생
	 * 
	 * @param   strategy
	 *            정책 String
	 * @return String 타입 ID
	 * @throws Exception
	 *             아이디 생성에 실패한 경우
	 */

	public String getNextStringId(String strategyId) throws FrameworkException {
		throw new FrameworkException(new Exception(),"error.idgnr.not.supported","string"); 
	}

	/**
	 * Config 정보에 지정된 Address 세팅
	 * 
	 * @param address
	 *               Config 에 지정된 address 정보
	 * @throws Exception
	 *               IP 정보가 이상한 경우
	 */
	public void setAddress(String address) throws FrameworkException {

//		this.address = address;
		byte[] addressBytes = new byte[6];

		if (null == address) {
			logger.warn("IDGeneration Service : Using a random number as the "
					+ "base for id's.  This is not the best method for many "
					+ "purposes, but may be adequate in some circumstances."
					+ " Consider using an IP or ethernet (MAC) address if "
					+ "available. ");
			for (int i = 0; i < 6; i++) {
				addressBytes[i] = (byte) (255 * Math.random());
			}
		} else {
			if (address.indexOf(".") > 0) {
				// we should have an IP
				StringTokenizer stok = new StringTokenizer(address, ".");
				if (stok.countTokens() != 4) {
					throw new FrameworkException(new Exception(),"error.string");
				}

				addressBytes[0] = (byte) 255;
				addressBytes[1] = (byte) 255;
				int i = 2;
				try {
					while (stok.hasMoreTokens()) {						
						addressBytes[i++] = Integer.valueOf(stok.nextToken(), 16).byteValue();						
					}
				} catch (Exception e) {
					throw new FrameworkException(new Exception(),"error.string");
				}
			} else if (address.indexOf(":") > 0) {
				// we should have a MAC
				StringTokenizer stok = new StringTokenizer(address, ":");
				if (stok.countTokens() != 6) {
					throw new FrameworkException(new Exception(),"error.string");
				}
				int i = 0;
				try {
					while (stok.hasMoreTokens()) {
						addressBytes[i++] = Integer.valueOf(stok.nextToken(), 16).byteValue();						
					}
				} catch (Exception e) {
					throw new FrameworkException(new Exception(),"error.string");
				}
			} else {
				throw new FrameworkException(new Exception(),"error.string");
			}
		}
		mAddressId = Base64.encode(addressBytes);

	}

	/**
	 * UUID 얻기
	 * 
	 * @return String unique id
	 */
	private String getUUId() {
		byte[] bytes6 = new byte[6];


		long timeNow = System.currentTimeMillis();

		timeNow = (int) timeNow & 0xFFFFFFFF;

		byte[] bytes4 = new byte[4];

		toFixSizeByteArray(new BigInteger(String.valueOf(timeNow)), bytes4);
		bytes6[0] = bytes4[0];
		bytes6[1] = bytes4[1];
		bytes6[2] = bytes4[2];
		bytes6[3] = bytes4[3];

		short counter;
		synchronized (this) {
			counter = getLoopCounter();
		}

		byte[] bytes2 = new byte[2];

		toFixSizeByteArray(new BigInteger(String.valueOf(counter)), bytes2);
		bytes6[4] = bytes2[0];
		bytes6[5] = bytes2[1];

		mTimeId = Base64.encode(bytes6);

		return (mAddressId + mTimeId).replace('+', '_').replace('/', '@');
	}

	/**
	 * Get the counter value as a signed short
	 * 
	 * 
	 * @original Get the counter value as a signed short
	 * @return short loop count
	 */
	private short getLoopCounter() {
		return mLoopCounter++;
	}

	/**
	 * @original This method transforms Java BigInteger type into a fix size
	 *           byte array containing the two's-complement representation of
	 *           the integer. The byte array will be in big-endian byte-order:
	 *           the most significant byte is in the zeroth element. If the
	 *           destination array is shorter then the BigInteger.toByteArray(),
	 *           the the less significant bytes will be copy only. If the
	 *           destination array is longer then the BigInteger.toByteArray(),
	 *           destination will be left padded with zeros.
	 * @param bigInt
	 *            Java BigInteger type
	 * @param destination
	 *            destination array
	 */
	private void toFixSizeByteArray(BigInteger bigInt, byte[] destination) {
		// Prepare the destination
		for (int i = 0; i < destination.length; i++) {
			destination[i] = 0x00;
		}

		// Convert the BigInt to a byte array
		byte[] source = bigInt.toByteArray();

		// Copy only the fix size length
		if (source.length <= destination.length) {
			for (int i = 0; i < source.length; i++) {
				destination[destination.length - source.length + i] = source[i];
			}
		} else {
			for (int i = 0; i < destination.length; i++) {
				destination[i] = source[source.length - destination.length + i];
			}
		}
	}

}
