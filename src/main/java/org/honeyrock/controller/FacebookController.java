package org.honeyrock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.honeyrock.domain.UserVO;
import org.honeyrock.service.FacebookLogin;
import org.honeyrock.service.GoogleLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;

@Controller
@Log
public class FacebookController {

	@RequestMapping(value = "/facebooklogin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {

		JsonNode token = FacebookLogin.getAccessToken(code);
		log.info("=====" + code);
		log.info("====" + token);

		JsonNode userInfo = FacebookLogin.getFacebookUserInfo(token.path("access_token").asText());

		UserVO vo = FacebookLogin.changeData(userInfo);

		vo.setUser_snsId("f" + vo.getUser_snsId());

		session.setAttribute("login", vo);
		model.addAttribute("login", vo);

		return "login/facebookLogin";
	}

}
