package com.trungnguyen.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MailContentBuilder {
	
	private TemplateEngine templateEngine;
	
	public String build(String message) {
		Context ct = new Context();
		ct.setVariable("message", message);
		return templateEngine.process("email_template", ct);
	}
}
