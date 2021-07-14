package notebook.backend.service;

public interface OtpService {

	String generateOtp(String username);
	
	String findByOtp(String otp);
	
	void clearOtp(String otp);
}
