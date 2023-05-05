package com.ism.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ism.bean.CustomResponse;
import com.ism.entity.UserEntity;
import com.ism.repository.UserRepository;
import com.ism.service.TokenGenerate;
@CrossOrigin
@RestController 
public class SessionController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	TokenGenerate tokenGeneraate;
		
	
	@PostMapping("/signUp")
	public ResponseEntity<CustomResponse<UserEntity>> signUpp(@RequestBody UserEntity userEntity)
	{
		UserEntity authenticate = userRepository.findByEmail(userEntity.getEmail());
		CustomResponse<UserEntity> resp = new CustomResponse<>();
		
		
		if(authenticate == null )
		{
			userRepository.save(userEntity);	
			
			resp.setData(userEntity);
			resp.setMsg("User Addes Successfully");
			return ResponseEntity.ok(resp);
		}
		else
		{
			resp.setMsg("User Already Exist Please Login 😉");
			 
			return ResponseEntity.unprocessableEntity().body(resp);
		}
		
	}


	@PostMapping("/login")
	public ResponseEntity<CustomResponse<UserEntity>> loginn(@RequestBody UserEntity userEntity)
	{
		UserEntity emailpass = userRepository.findByEmailAndPassword(userEntity.getEmail(), userEntity.getPassword());
		
		CustomResponse<UserEntity> resp = new CustomResponse<>();
		
		if(emailpass == null)
		{
			resp.setMsg("Please Enter Valid Email");
			return ResponseEntity.unprocessableEntity().body(resp);
		}
		else 
		{
			
			String token = tokenGeneraate.generateToken(16);
			
			emailpass.setToken(token);
			userRepository.save(emailpass);
			
			 resp.setData(emailpass);
		    resp.setMsg("Login Successfull");
			return ResponseEntity.ok(resp);
			
		}
	}

//	@GetMapping("/forgotpasword/{email}")
//	public ResponseEntity<CustomResponse<UserEntity>> forgotpaswordd(@PathVariable("email") String email)
//	{
//		UserEntity userEntity = userRepository.findByEmail(email);
//		CustomResponse<UserEntity> resp = new CustomResponse<>();
//		
//		if(userEntity == null)
//		{
//			resp.setMsg("Email Does Not Exist");
//			return ResponseEntity.unprocessableEntity().body(resp);
//		}
//		else
//		{
//			Integer otp = (int) Math.random()*100000;
//			userEntity.setOtp(otp);
//			userRepository.save(userEntity);
//			
//			resp.setData(userEntity);
//			resp.setMsg("OTP Share On Your Mail");
//			return ResponseEntity.ok(resp);
//		}
//	}
	
	@GetMapping("/forgotPassword")
	public ResponseEntity<CustomResponse<UserEntity>> forgotPasseord(@RequestParam("email") String email){
		
		UserEntity tempUserByEmail = userRepository.findByEmail(email);
		CustomResponse<UserEntity> usr = new CustomResponse<>();
		if(tempUserByEmail == null)
		{
			usr.setData(null);
			usr.setMsg("Otp will share to your Email, if email is exists !!");
			return ResponseEntity.ok(usr);
		}
		else
		{
			Integer min = 100000;
			Integer max = 999999;
			Integer temp = (int) (Math.random() * (max - min + 1) + min)  ;
			System.out.println("-----------------"+temp+"-----------------");
			tempUserByEmail.setOtp(temp);
			userRepository.save(tempUserByEmail);
			usr.setData(tempUserByEmail);
			usr.setMsg("Otp will share to your Email, if email is exists (OTP set)!!");
			return ResponseEntity.ok(usr);
		}
	}
	
	@GetMapping("/resetPassword")
	public ResponseEntity<CustomResponse<UserEntity>> resetPassword(@RequestParam("otp") Integer otp, @RequestParam("password") String password, @RequestParam("conformpassword") String conformpassword)
	{
		UserEntity user = userRepository.findByOtp(otp);
			if((password.equals(conformpassword)) && !(user.getPassword().equals(password)))
			{
				CustomResponse<UserEntity> usr = new CustomResponse<>();
				user.setPassword(conformpassword);
				userRepository.save(user);
				usr.setData(user);
				usr.setMsg("Password sucessfully reset !!");
				return ResponseEntity.ok(usr);
			}
			else
			{
				CustomResponse<UserEntity> usr = new CustomResponse<>();
				usr.setData(null);
				usr.setMsg("Please enter password and conformpassword same !!");
				return ResponseEntity.ok(usr);
			}
		}

}




















 