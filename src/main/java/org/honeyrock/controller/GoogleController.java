package org.honeyrock.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.honeyrock.domain.UserVO;
import org.honeyrock.service.GoogleLogin;
import org.honeyrock.service.KakaoLogin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.extern.java.Log;

@Controller
@Log
public class GoogleController {

	@RequestMapping(value = "/googlelogin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, HttpServletRequest request,
			HttpServletResponse response, HttpSession session, Model model) {

		JsonNode token = GoogleLogin.getAccessToken(code);
		log.info("=====" + code);
		log.info("=====" + token);

		JsonNode userInfo = GoogleLogin.getGoogleUserInfo(token.path("access_token").asText());

		UserVO vo = GoogleLogin.changeData(userInfo);

		vo.setUser_snsId("g" + vo.getUser_snsId());

		session.setAttribute("login", vo);
		model.addAttribute("login", vo);

		return "login/googleLogin";
	}

}
