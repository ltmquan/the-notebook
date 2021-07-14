package notebook.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import notebook.backend.constants.EmailTemplateId;
import notebook.backend.model.EmailTemplate;
import notebook.backend.service.EmailService;
import notebook.backend.service.EmailTemplateService;

@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private EmailTemplateService emailTemplateService;
	
	private void sendMail(String to, String subject, String content) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject(subject);
		message.setText(content);
		
		javaMailSender.send(message);
	}

	@Override
	public void sentOtpMail(String to, String otp) {
		EmailTemplate template = emailTemplateService.findById(EmailTemplateId.OTP_EMAIL);
		
		this.sendMail(to, template.getSubject(), String.format(template.getContent(), otp));
	}

}
