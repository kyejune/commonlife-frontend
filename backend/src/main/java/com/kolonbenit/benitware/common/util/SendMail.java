package com.kolonbenit.benitware.common.util;

//import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Component
public class SendMail
{
	private static final Logger logger = LoggerFactory.getLogger(SendMail.class);
	private static String host;
	  @Value("#{applicationProps['mail.host']}")
	  public void setMailHost(String host){
		  this.host = host;
//		 log.debug("this.host = " + this.host);
	  }
	
	public boolean Sendmail ( String host, String mailFrom, String mailTo, String tocc,
			String tobcc, String title, String content ){

		//log.info("SendMail Start!!");
		if(host==null || host.equals("")){
			host	= this.host;  // SMTP Mail Server
		}
		Properties prop =System.getProperties();
		Session sess = Session.getInstance(prop,null);

		try {

			logger.info("[Send Mail]");
			logger.info(">> mailFrom = "+mailFrom);
			logger.info(">> mailTo = "+mailTo);
			logger.info(">> tocc = "+tocc);
			logger.info(">> tobcc = "+tobcc);
			logger.info(">> title = "+title);
			logger.info(">> content = "+content);
			
			MimeMessage msg = new MimeMessage(sess);
			msg.setHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(mailFrom));

			InternetAddress[] address = InternetAddress.parse(mailTo);
			msg.setRecipients(javax.mail.Message.RecipientType.TO,address);

			if(tocc != null && tocc.trim().length() !=0){
				InternetAddress[] atocc = InternetAddress.parse(tocc);
				msg.setRecipients(javax.mail.Message.RecipientType.CC, atocc);
			}

			if(tobcc != null && tobcc.trim().length() !=0){
				InternetAddress[] atobcc = InternetAddress.parse(tobcc);
				msg.setRecipients(javax.mail.Message.RecipientType.BCC, atobcc);
			}

			msg.setSubject(title,"EUC-KR");
			msg.setSentDate(new Date());
			msg.setContent(content,"text/html;charset=euc-kr");   // HTML 형식
			Transport transport = sess.getTransport("smtp");

			if (host == null || host.trim().length() ==0 || host.trim().equals("localhost")){
				transport.send(msg,msg.getAllRecipients());
			}else{
//				log.info(">>host="+host);
				transport.connect(host,"","");
				transport.sendMessage(msg, msg.getAllRecipients());

				
			}
			transport.close();
//			log.info(host+"");

		} catch (Exception me) {
			me.printStackTrace();
			logger.info("접속에 문제가 생겨서 발송하지 못했습니다.");
			return false;
		}
		return true;
	}
}
