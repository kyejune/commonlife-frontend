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
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;

import java.math.BigDecimal;

public abstract class AbstractIdGnrService implements
		IIdGnrService, ApplicationContextAware, BeanFactoryAware {
	
	/**
	 * BeanFactory
	 */
	private BeanFactory beanFactory;

	/**
	 * BIG_DECIMAL_MAX_LONG 정의
	 */
	private static final BigDecimal BIG_DECIMAL_MAX_LONG = new BigDecimal(
			Long.MAX_VALUE);

	/**
	 * 내부 synchronization을 위한 정보
	 */
	private final Object mSemaphore = new Object();

	
	/**
	 * BigDecimal 사용 여부
	 */
	protected boolean useBigDecimals = false ;

	/**
	 * MessageSource
	 */
	protected MessageSource messageSource;

	/**
	 * 기본 생성자
	 */
	public AbstractIdGnrService() {
	}

	

	/**
	 * BigDecimal 타입의 유일 아이디 제공
	 * 
	 * @return BigDecimal 타입의 다음 ID
	 * @throws Exception
	 *             if an Id could not be allocated for any reason.
	 */
	protected abstract BigDecimal getNextBigDecimalIdInner()
			throws Exception;

	/**
	 * long 타입의 유일 아이디 제공
	 * 
	 * @return long 타입의 다음 ID
	 * @throws Exception
	 *             여타이유에 의해 아이디 생성이 불가능 할때
	 *             	
	 */
	protected abstract long getNextLongIdInner() throws Exception;

	/**
	 * BigDecimal 사용여부 세팅
	 * 
	 * @param useBigDecimals
	 *            BigDecimal 사용여부 
	 */
	public final void setUseBigDecimals(boolean useBigDecimals) {
		this.useBigDecimals = useBigDecimals;
	}

	/**
	 * BigDecimal 사용여부 정보 리턴
	 * 
	 * @return boolean check using BigDecimal
	 */
	protected final boolean isUsingBigDecimals() {
		return useBigDecimals;
	}

	/**
	 * 특별한 최대 값보다 작은 Long 타입의 다음 ID
	 * 
	 * @param maxId
	 *            최대값
	 * @return long value to be less than the specified maxId
	 * @throws Exception
	 *             다음 ID가 입력받은 MaxId보다 클때
	 */
	protected final long getNextLongIdChecked(long maxId) throws Exception {
		long nextId;
		if (useBigDecimals) {
			// Use BigDecimal data type
			BigDecimal bd;
			synchronized (mSemaphore) {
				bd = getNextBigDecimalIdInner();
			}

			if (bd.compareTo(BIG_DECIMAL_MAX_LONG) > 0) {
//				getLogger().error(
//						messageSource.getMessage("error.idgnr.greater.maxid",
//								new String[] {"Long"}, Locale.getDefault()));
//				throw new Exception(messageSource, "error.idgnr.greater.maxid");
				try{
					int val = 0/0;
				}catch(Exception e){
					throw new FrameworkException(e,"error.idgnr.greater.maxid","long"); 
				}
			}
			nextId = bd.longValue();
		}
		else {
			// Use long data type
			synchronized (mSemaphore) {
				nextId = getNextLongIdInner();
			}
		}

		// Make sure that the id is valid for the requested data type.
		if (nextId > maxId) {
//			getLogger().error(
//					messageSource.getMessage("error.idgnr.greater.maxid",
//							new String[] {"Long"}, Locale.getDefault()));
//			throw new Exception(messageSource, "error.idgnr.greater.maxid");
			try{
				int val = 0/0;
			}catch(Exception e){
				throw new FrameworkException(e,"error.idgnr.greater.maxid","long"); 
			}
		}

		return nextId;
	}

     /**
	 * Returns BigDecimal 타입의 다음 ID 제공
	 * 
	 * @return BigDecimal the next Id.
	 * @throws Exception
	 *             다음 아이디가 유효한 BigDecimal의 범위를 벗어날때
	 */
	public final BigDecimal getNextBigDecimalId() throws Exception {
		BigDecimal bd;
		if (useBigDecimals) {
			// Use BigDecimal data type
			synchronized (mSemaphore) {
				bd = getNextBigDecimalIdInner();
			}
		}
		else {
			// Use long data type
			synchronized (mSemaphore) {
				bd = new BigDecimal(getNextLongIdInner());
			}
		}

		return bd;
	}

	/**
	 * Returns long 타입의 다음 ID 제공
	 * 
	 * @return the next Id.
	 * @throws Exception
	 *             다음 아이디가 유효한 long의 범위를 벗어날때
	 */
	public final long getNextLongId() throws Exception {
		return getNextLongIdChecked(Long.MAX_VALUE);
	}

	/**
	 * Returns int 타입의 다음 ID 제공
	 * 
	 * @return the next Id.
	 * @throws Exception
	 *             다음 아이디가 유효한 integer의 범위를 벗어날때
	 */
	public final int getNextIntegerId() throws Exception {
		return (int) getNextLongIdChecked(Integer.MAX_VALUE);
	}

	/**
	 * Returns Short 타입의 다음 ID 제공
	 * 
	 * @return the next Id.
	 * @throws Exception
	 *             다음 아이디가 유효한 Short의 범위를 벗어날때
	 */
	public final short getNextShortId() throws Exception {
		return (short) getNextLongIdChecked(Short.MAX_VALUE);
	}

	/**
	 * Returns Byte 타입의 다음 ID 제공
	 * 
	 * @return the next Id.
	 * @throws Exception
	 *             다음 아이디가 유효한 Byte 범위를 벗어날때
	 */
	public final byte getNextByteId() throws Exception {
		return (byte) getNextLongIdChecked(Byte.MAX_VALUE);
	}

	/**
	 * set BeanFactory
	 * 
	 * @param beanFactory
	 *            to be set by Spring Framework
	 */
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	/**
	 * Message Source Injection 
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.messageSource = (MessageSource) applicationContext
				.getBean("messageSource");

	}
}
