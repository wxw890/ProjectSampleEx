package com.hta.studyroom.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hta.studyroom.repository.StudyroomDto;
import com.hta.studyroom.service.StudyroomService;

@Controller
public class SearchReservationController {
	private StudyroomService studyroomService;
	
	public void setStudyroomService(StudyroomService studyroomService) {
		this.studyroomService = studyroomService;
	}
	@RequestMapping("/reservation.search")
	public ModelAndView SearchList(HttpSession session, @ModelAttribute StudyroomDto studyroomdto){
		ModelAndView mav = new ModelAndView("/study_room/reservation/step1");
		//계정을 세션에 저장.
		//String member_email = (String)session.getAttribute("email");
		//studyroomdto.setMember_email(member_email);
		List list;
		
		//System.out.println("session에 저장한 email가져옵니까?"+member_email);
		try{
			list=studyroomService.Searchreservation();
			mav.addObject("list", list);
		}catch(Exception err){
			System.out.println("찾기 부분에서 에러:"+err);
		}
		
		return mav;
			
			
	}
}
