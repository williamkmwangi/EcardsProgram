/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.eistream.sonora.myclasses;

import com.eistream.sonora.exceptions.SonoraException;
import com.eistream.sonora.property.ImmutableProperty;
import com.eistream.sonora.property.PropertyManager;
import com.eistream.sonora.workflow.rules.UserClass;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 *
 * @author HP
 */
public class SheriaECardsProgram implements UserClass
{
    static Properties mailServerProperties;
  static Session getMailSession;
  static MimeMessage msg;
  public static String MemberName;
  public static String toEmail;

    @Override
    public void userMethod(PropertyManager pm, String string) throws SonoraException {
        toEmail = pm.get("fields.EMAIL_ADDRESS").getValue().toString();
        MemberName = pm.get("fields.MEMBER_NAME").getValue().toString();
    
        System.out.println(toEmail + " " + MemberName);
        
        
    }
    
    public static void generateAndSendEmail(Session session, String toEmail2, String subject, String body)
  {
    try
    {
      System.out.println("\n3rd ===> generateAndSendEmail() starts..");
      
      toEmail2 = toEmail;
      
      System.out.println("\n Email" + toEmail2);
      
      MimeMessage SheriaBirtDayMessage = new MimeMessage(session);
      SheriaBirtDayMessage.addHeader("Content-type", "text/HTML; charset=UTF-8");
      SheriaBirtDayMessage.addHeader("format", "flowed");
      SheriaBirtDayMessage.addHeader("Content-Transfer-Encoding", "8bit");
      
      SheriaBirtDayMessage.setFrom(new InternetAddress("sheria.edms@sheriasacco.coop", "NoReply-Sheria"));
      SheriaBirtDayMessage.setReplyTo(InternetAddress.parse("sheria.edms@sheriasacco.coop", false));
      SheriaBirtDayMessage.setSubject(subject, "UTF-8");
      SheriaBirtDayMessage.setSentDate(new Date());
      
      SheriaBirtDayMessage.setRecipients(Message.RecipientType.TO, toEmail);
      
      BodyPart messageBodyPart = new MimeBodyPart();
      messageBodyPart.setContent(body, "text/html");
      
      Multipart multipart = new MimeMultipart();
      
      multipart.addBodyPart(messageBodyPart);
      
      messageBodyPart = new MimeBodyPart();
      
      String filename = "C:/images/appreciation_certificate.jpg";
      DataSource source = new FileDataSource(filename);
      messageBodyPart.setDataHandler(new DataHandler(source));
      messageBodyPart.setFileName(filename);
      
      messageBodyPart.setHeader("Content-ID", "image_id");
      multipart.addBodyPart(messageBodyPart);
      
      System.out.println("\n4th ===> third part for displaying image in the email body..");
      
      SheriaBirtDayMessage.setContent(multipart);
      
      System.out.println("\n5th ===> Finally Send message..");
      
      Transport.send(SheriaBirtDayMessage);
      
      System.out.println("\n6th ===> Email Sent Successfully With Image Attachment. Check your email now..");
      System.out.println("\n7th ===> generateAndSendEmail() ends..");
    }
    catch (MessagingException|UnsupportedEncodingException e) {}
  }
    public SheriaECardsProgram()
  {
      //get current year
      
      int year = Calendar.getInstance().get(Calendar.YEAR);
      String strYear = Integer.toString(year);
    System.out.println("\n1st ===> setup Mail Server Properties..");
    
    String sourceEmail = "sheria.edms@sheriasacco.coop";
    String password = "SheriRec@2099";
    
    String MembersName = MemberName;
    
    System.out.println("\n Member Name" + MembersName + "EMail" + toEmail);
    
    Properties props = new Properties();
    //props.put("mail.smtp.host", "smtp.office365.com");
    //props.put("mail.smtp.port", "587");
    props.put("mail.smtp.host", "192.168.30.74");
    props.put("mail.smtp.port", "25");
    props.put("mail.smtp.auth", "true");
    props.put("mail.smtp.starttls.enable", "false");
    
    System.out.println("\n2nd ===> create Authenticator object to pass in Session.getInstance argument..");
    
    Authenticator authentication = new Authenticator()
    {
      protected PasswordAuthentication getPasswordAuthentication()
      {
        //return new PasswordAuthentication("sheria.edms@sheriasacco.coop", "SheriRec@2099");
          return new PasswordAuthentication("sheria.edms@sheriassacco.coop", "SheriRec@2099");
      }
    };
    Session session = Session.getInstance(props, authentication);
    generateAndSendEmail(session, toEmail, "PAYMENT OF INTEREST ON DEPOSITS AND DIVIDEND ON SHARE CAPITAL, ", "Dear " + MembersName + ", <br><br>Sheria Sacco Family wishes to appreciate you, as our esteemed member.<br><br> We are proud to be associated with you. Have a blessed year " + strYear + " ! <br><br>Kindly find attached Certificate of Appreciation.<br><br> Regards, <br>SHERIA SACCO");
  }

    @Override
    public void userValidate(PropertyManager pm, String string) throws SonoraException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
