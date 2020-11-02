package com.trungnguyen.service;

import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.trungnguyen.exception.SpringRedditException;
import com.trungnguyen.model.NotificationEmail;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class MailService {
	
	private final MailContentBuilder mailContentBuilder;
	private final JavaMailSender mailSender;
	
	/*
	 *  @Async annotation states that the method will be executed in a different thread.
	 */
	@Async
	public void sendMail(NotificationEmail nEmail) {
		MimeMessagePreparator messagePreparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("redditclone@gmail.com");
			messageHelper.setTo(nEmail.getRecipient());
			messageHelper.setText(mailContentBuilder.build(nEmail.getBody()));
		};
		try {
			mailSender.send(messagePreparator);
			log.info("Activation email sent");
		} catch (MailException e) {
			throw new SpringRedditException("Exception occured when sending email to " + nEmail.getRecipient());
		}
	}
}
