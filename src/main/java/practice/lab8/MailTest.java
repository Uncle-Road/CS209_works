package practice.lab8;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.security.GeneralSecurityException;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class MailTest
{
   public static void main(String[] args) throws MessagingException, IOException, GeneralSecurityException {
      // read properties
      Properties props = new Properties();
      try (InputStream in = Files.newInputStream(Paths.get("src/main/java/practice/lab8/mail.properties")))
      {
         props.load(in);
      }

      // read message info
      List<String> lines = Files.readAllLines(Paths.get("src/main/java/practice/lab8/message.txt"), StandardCharsets.UTF_8);

      String from = lines.get(0);
      String to = lines.get(1);
      String subject = lines.get(2);

      StringBuilder builder = new StringBuilder();
      for (int i = 3; i < lines.size(); i++)
      {
         builder.append(lines.get(i));
         builder.append("\n");
      }

      // read password for your email account
      System.out.println("Password: ");
      Scanner scanner = new Scanner(System.in);
      String password = scanner.next();

      MailSSLSocketFactory sf = new MailSSLSocketFactory();
      sf.setTrustAllHosts(true);
      props.put("mail.smtp.ssl.socketFactory", sf);

      Session mailSession = Session.getDefaultInstance(props, new Authenticator() {
         @Override
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from,password);
         }
      });

      MimeMessage message = new MimeMessage(mailSession);
      try{
         message.setFrom(new InternetAddress(from));
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
         message.setSubject(subject);
         message.setText(builder.toString());
         Transport.send(message);
         System.out.println("Sent message successfully");
      }catch (MessagingException mex) {
         mex.printStackTrace();
      }

   }
}