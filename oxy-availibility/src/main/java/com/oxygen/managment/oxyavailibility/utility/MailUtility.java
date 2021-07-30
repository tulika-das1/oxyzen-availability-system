package com.oxygen.managment.oxyavailibility.utility;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.security.core.context.SecurityContextHolder;

import com.oxygen.managment.oxyavailibility.pojo.CustomUser;
import com.oxygen.managment.oxyavailibility.pojo.OxygenRequestPojo;

public class MailUtility {
	
	public static String FROM_EMAIL = "oxygenavailabilityinfo1022@gmail.com";
	public static String PASS = "OAS20212021@";

	public static void sendEmail(Session session, String toEmail, String subject, String body) { 
		try {
			CustomUser user = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			InternetAddress[] myCc = InternetAddress.parse(user.getUsername());
			
			MimeMessage msg = new MimeMessage(session);
			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			

			
			String test="<html><head><style>.colored {color: blue;}#body {font-size: 14px;}</style></head><body><div id='body'><p>Hi Pierce,</p><p class='colored'>This text is blue.</p><p>Jerry</p></div></body></html>";
			msg.setContent(body, "text/html; charset=utf-8");
			msg.setFrom(new InternetAddress("no_reply@example.com", "NoReply-JD"));


			msg.setSubject(subject, "UTF-8");

			msg.setSentDate(new Date());

			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
			msg.setRecipients(Message.RecipientType.CC,myCc);
			
			Transport.send(msg);

			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Session configureMail() {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtp.port", "587"); //TLS Port
		props.put("mail.smtp.auth", "true"); //enable authentication
		props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
		
                //create Authenticator object to pass in Session.getInstance argument
		Authenticator auth = new Authenticator() {
			//override the getPasswordAuthentication method
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(FROM_EMAIL, PASS);
			}
		};
		Session session = Session.getInstance(props, auth);
		return session;
	}
	
	
	public static String  configureMailSubject(OxygenRequestPojo req) {
		StringBuilder br = new StringBuilder();
		if(ApplicationConstant.REQ_STATUS_APPROVED.equalsIgnoreCase(req.getReqStatus())) {
			br.append("Your Request (Request id:"+req.getRequestId()+") Has been Approved by System Administrator.");
			 
			
		}else if(ApplicationConstant.REQ_STATUS_CANCEL.equalsIgnoreCase(req.getReqStatus())) {
			
			br.append("Your Request (Request id:"+req.getRequestId()+") Has been Cancelled by System Administrator.");
			
		}else if(ApplicationConstant.REQ_STATUS_COMPLETE.equalsIgnoreCase(req.getReqStatus())) {
			br.append("The workflow of your request (Request id:"+req.getRequestId()+") Has been Completed by System Administrator.");
		}
		return br.toString();
	}
	
	
	public static String  configureMailBody(OxygenRequestPojo req, CustomUser requestedUser) {
		StringBuilder br = new StringBuilder();
		br.append("Hi User:"+requestedUser.getDisplayName()+"<br>");
		
		if(ApplicationConstant.REQ_STATUS_APPROVED.equalsIgnoreCase(req.getReqStatus())) {
			br.append("Your Request (Request id:"+req.getRequestId()+") Has been Approved by System Administrator.")
			.append("<br>The Approval message by Administrator is:"+req.getApprovalRejectionComment())
			.append("<br> <b>The standard cylinder of oxygen will be delivered to you. Details provided below:</b>")
			.append("<br> ")
			.append("<br> Diameter (in.): 4.3")
			.append("<br> Height (in.): 25.5")
			.append("<br> Empty Weight (lb): 7.9")
			.append("<br> Capacity (L.) at 2200 psi: 680")
			.append("<br> Regulator Type: CGA 870")
			.append("<br><br>")
			 .append("<br> Please Return the cylinder within 7 days of interval")
			 .append("<br>Penalty Amount would be added in case Cylinders are not returned in Time.")
			 .append("<br><br>Thanks and Regards,<br>")
			 .append("Oxygen Availability Team");
			 
			
		}else if(ApplicationConstant.REQ_STATUS_CANCEL.equalsIgnoreCase(req.getReqStatus())) {
			
			br.append("Your Request (Request id:"+req.getRequestId()+") Has been Cancelled by System Administrator.")
			.append("<br>The Cancellation message by Administrator is:"+req.getApprovalRejectionComment())
			.append("<br>Thank You for your request")
			.append("<br>Thanks and Regards,<br><br><br> ")
			.append("Oxygen Availability Team");
			
		}else if(ApplicationConstant.REQ_STATUS_COMPLETE.equalsIgnoreCase(req.getReqStatus())) {
			br.append("Your Request (Request id:"+req.getRequestId()+") Has been Completed by System Administrator.")
			.append("<br>The Completion message by Administrator is:"+req.getApprovalRejectionComment())
			.append("<br>Thank You for your request")
			.append("<br>Thanks and Regards,<br><br><br>")
			.append("Oxygen Availability Team");
		}
		return br.toString();
	}
}
