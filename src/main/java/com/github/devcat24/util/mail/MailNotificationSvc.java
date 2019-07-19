package com.github.devcat24.util.mail;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
@Slf4j
@Service("MailNotificationSvc")
public class MailNotificationSvc {
    private JavaMailSender mailSender;
    @Autowired
    void setMailSender(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }

    @Value("${template.mail.recipients}")
    private String recipients;

    @Value("${template.mail.from}")
    private String from;

    @Value("${template.mail.bcc:}")
    private String bcc;

    @Value("${template.mail.from-realname:}")
    private String fromRealName;

    @Value("${template.mail.reply-to:}")
    private String replyTo;

    public void sendHtmlEmail(String[] recipients, String subject, String htmlText) throws MessagingException {
        if (recipients == null || recipients.length == 0) {
            log.warn("No recipients provided, aborting sending e-mail with subject " + subject);
            return;
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        try {
            if (StringUtils.isNotBlank(fromRealName)) {
                helper.setFrom(from, fromRealName);
            } else {
                helper.setFrom(from);
            }
        } catch (UnsupportedEncodingException e) {
            log.warn("Cannot set sender's real name to \"" + fromRealName + "\", unsupported encoding: " + e.getMessage(), e);
            helper.setFrom(from);
        }
        if (StringUtils.isNotBlank(replyTo)) {
            helper.setReplyTo(replyTo);
        }
        helper.setTo(recipients);
        if (StringUtils.isNotBlank(bcc)) {
            String[] bccRecipients = bcc.split(",\\s*");
            if (bccRecipients.length > 0) {
                helper.setBcc(bccRecipients);
            }
        }
        helper.setSubject(subject);
        helper.setText(htmlText, true);

        mailSender.send(message);
    }

    public List<String> getValidRecipients(){
        String regExp = "\\w+@\\w+\\.\\w+(\\.\\w+)?";

        List<String> recipientList = new ArrayList<>();
        if(recipients != null && StringUtils.isNotBlank(recipients)){
            String[] rArray = recipients.split(",");
            for(String mailAddr : rArray){
                if(StringUtils.isNotBlank(mailAddr) && Pattern.matches(regExp, mailAddr)){
                    recipientList.add(StringUtils.trimToEmpty(mailAddr));
                }
            }
        }
        return recipientList;
    }
}
