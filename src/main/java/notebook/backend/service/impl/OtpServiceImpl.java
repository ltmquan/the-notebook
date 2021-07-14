package notebook.backend.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import notebook.backend.api.exception.BadRequestException;
import notebook.backend.constants.ApiActions;
import notebook.backend.constants.EntityName;
import notebook.backend.constants.OtpConstant;
import notebook.backend.messages.Messages;
import notebook.backend.service.OtpService;

@Service
public class OtpServiceImpl implements OtpService {

	private LoadingCache<String, String> otpCache;

	public OtpServiceImpl() {
		super();
		otpCache = CacheBuilder.newBuilder().expireAfterWrite(OtpConstant.expireTime, TimeUnit.SECONDS)
				.build(new CacheLoader<String, String>() {
					public String load(String key) {
						return "";
					}
				});
	}

	@Override
	public String generateOtp(String username) {
		Random random = new Random();
		String otp = String.valueOf(100000 + random.nextInt(900000));
		otpCache.put(otp, username);
		
		return otp;
	}

	@Override
	public String findByOtp(String otp) {
		try {
			return otpCache.get(otp);
		} catch (Exception e) {
			throw new BadRequestException(
					Messages.ERROR_INCORRECT_OTP, 
					ApiActions.AUTHENTICATE, EntityName.USER);
		}
	}

	@Override
	public void clearOtp(String otp) {
		otpCache.invalidate(otp);
	}

}
