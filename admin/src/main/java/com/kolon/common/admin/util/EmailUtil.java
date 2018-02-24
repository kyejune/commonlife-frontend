package com.kolon.common.admin.util;

import java.util.List;
import java.util.Properties;

public class EmailUtil {

    public boolean sendMail(String from, List<String> to, String cc, String subject, String content) {
        boolean isSend = false;
        /*

        String mailTitle;
        StringBuilder mailBody = new StringBuilder();


        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            mailTitle = "[] " + inquireDTO.getTitle() +"(" + codeService.getCodeNameByCode(inquireDTO.getInquireType())+")";
            mailBody.append("작성자 : " + cipherUtil.decrypt(inquireDTO.getWriteName()) +"<br/>");
            mailBody.append("연락처 : " + inquireDTO.getHp1()+"-"+inquireDTO.getHp2()+"-"+inquireDTO.getHp3() + "<br/>");
            mailBody.append("상담구분 : "+ codeService.getCodeNameByCode(inquireDTO.getInquireType()) + "<br/>");
            mailBody.append("상담방법 : "+ codeService.getCodeNameByCode(inquireDTO.getInquireWay())+ "<br/>");
            if(!StringUtils.isEmpty(inquireDTO.getVisitDate()))
//                mailBody.append("방문시간 : "+ inquireDTO.getVisitDate() +" \t "+ inquireDTO.getVisitTime() + "<br/>");

            mailBody.append("문의내용 : "+ inquireDTO.getMessage() + "<br/>");

            messageHelper.setSubject(mailTitle);
            messageHelper.setText(mailBody.toString(), true);
            messageHelper.setFrom(inquireDTO.getEm1() + "@" + inquireDTO.getEm2());
            messageHelper.setTo(SEND_TO);

            mailSender.send(message);

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            isSend = false;
        }
        */

        return isSend;
    }


    public void sendEmail(String from, List<String> to, String cc, String subject, String content) throws Exception {

        // Properties 설정
        // 프로퍼티 값 인스턴스 생성과 기본세션(SMTP 서버 호스트 지정)
        Properties props = new Properties();

//		  // G-Mail SMTP 사용시
//		  props.put("mail.transport.protocol", "smtp");// 프로토콜 설정
//		  props.put("mail.smtp.host", ConfigMng.getValue(IPropertyKey.EMAIL_SMTP_HOST));// gmail SMTP 서비스 주소(호스트)
//		  props.put("mail.smtp.port", ConfigMng.getValue(IPropertyKey.EMAIL_SMTP_PORT));// gmail SMTP 서비스 포트 설정

//		  // 로그인 할때 Transport Layer Security(TLS)를 사용할 것인지 설정
//		  // gmail 에선 tls가 필수가 아니므로 해도 그만 안해도 그만
//		  props.put("mail.smtp.starttls.enable","true");
//		  // gmail 인증용 Secure Socket Layer(SSL) 설정
//		  // gmail 에서 인증때 사용해주므로 요건 안해주면 안됨
//		  props.setProperty("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
//		  // props.put("mail.smtp.user", from);
//		  props.put("mail.smtp.ssl.enable", "true");
//	      props.put("mail.smtp.ssl.trust", ConfigMng.getValue(IPropertyKey.EMAIL_SMTP_HOST));
//		  props.put("mail.smtp.auth", "true");// SMTP 인증을 설정
//		  /**
//		   * SMTP 인증이 필요한 경우 반드시 Properties 에 SMTP 인증을 사용한다고 설정하여야 한다.
//		   * 그렇지 않으면 인증을 시도조차 하지 않는다.
//		   */
//		  Authenticator auth = new SMTPAuthenticator();
//		  Session mailSession = Session.getDefaultInstance(props, auth);

        // SSL 사용하는 경우
//	      props.put("mail.smtp.socketFactory.port", ConfigMng.getValue(IPropertyKey.EMAIL_SMTP_PORT)); //SSL Port
//	      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
//	      props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication


        /** ============= 주석 제거 시작 ================




        // TLS 사용하는 경우
        props.put("mail.transport.protocol", "smtp");// 프로토콜 설정
        props.put("mail.smtp.host", ConfigMng.getValue(IPropertyKey.EMAIL_SMTP_HOST));// gmail SMTP 서비스 주소(호스트)
        props.put("mail.smtp.port", ConfigMng.getValue(IPropertyKey.EMAIL_SMTP_PORT));// gmail SMTP 서비스 포트 설정
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS

        final String fromEmail = from;

        // 인증
        Authenticator auth = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, ConfigMng.getValue(IPropertyKey.EMAIL_SERVER_PASS));
            }
        };

        // 메일 세션
        Session mailSession = Session.getInstance(props, auth);

        // create a message
        Message msg = new MimeMessage(mailSession);

        // set the from and to address
        msg.setFrom(new InternetAddress(from));//보내는 사람 설정
        InternetAddress[] addressTo = new InternetAddress[to.size()];
        for (int i = 0; i < to.size(); i++){
            addressTo[i] = new InternetAddress(to.get(i));
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);//받는 사람설정

        if(!cc.trim().equals("")) {
            msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(cc, false));
        }

        // Setting the Subject and Content Type
//		  msg.setSubject(subject); // 제목 설정
//		  msg.setText(content);  // 내용 설정
        msg.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B")); // 제목 설정
        msg.setContent(content.replaceAll("\r\n", "<br/>"), "text/html; charset=UTF-8"); // 내용 설정
        msg.setSentDate(new Date());// 보내는 날짜 설정

        Transport.send(msg);  // 메일 보내기



        ============= 주석 제거 종료 ================ **/
    }
}
