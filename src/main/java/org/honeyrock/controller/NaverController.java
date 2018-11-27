package org.honeyrock.controller;

import javax.servlet.http.HttpSession;

import org.honeyrock.domain.UserVO;
import org.honeyrock.service.NaverLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.java.Log;

@Controller
@Log
public class NaverController {

	@RequestMapping(value = "/naverlogin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String callback(Model model, @RequestParam("code") String code, @RequestParam("state") String state,
			HttpSession session) {

		JsonNode token = NaverLogin.getAccessToken(code);
		log.info("=====" + code);
		log.info("=====" + state);
		log.info("=====" + token);

		JsonNode userInfo = NaverLogin.getNaverUserInfo(token.path("access_token").toString());

		UserVO vo = NaverLogin.changeData(userInfo);

		vo.setUser_snsId("n" + vo.getUser_snsId());

		session.setAttribute("login", vo);
		model.addAttribute("login", vo);

		return "login/naverLogin";
	}
	
	@RequestMapping(value = "/naver", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String login(Model model, HttpSession session) {
		
		return "naver";
		
	}

}
