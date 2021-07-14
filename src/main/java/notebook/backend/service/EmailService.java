package notebook.backend.service;

public interface EmailService {
	void sentOtpMail(String to, String otp);
}
