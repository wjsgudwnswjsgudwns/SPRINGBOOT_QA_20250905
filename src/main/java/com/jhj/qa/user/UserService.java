package com.jhj.qa.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jhj.qa.DataNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// 회원 가입
	public SiteUser create(String username, String password, String email) {
		SiteUser user = new SiteUser();
		
		user.setUsername(username);
		user.setEmail(email);
		
		String cryptPassword = passwordEncoder.encode(password); // 비밀번호 암호화
		user.setPassword(cryptPassword); // 암호화 후 DB 입력
		
		
		userRepository.save(user);
		
		return user;
	}
	
	//유저 가져오기
	public SiteUser getUser(String username) {
		Optional<SiteUser> siteUser = userRepository.findByUsername(username);
		if(siteUser.isPresent()) {
			SiteUser _siteUser = siteUser.get();
			return _siteUser;
		} else {
			throw new DataNotFoundException("존재하지 않는 유저입니다");
		}
	}
	
	
}
