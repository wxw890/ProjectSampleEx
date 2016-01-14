package com.hta.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.aop.config.AdvisorComponentDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hta.board.service.BoardService;
import com.hta.member.repository.Member;
import com.hta.member.repository.RegisterRequest;
import com.hta.member.service.MemberService;

@Controller
public class RegisterController {
	private MemberService memberService;
	
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	@RequestMapping(value="/register.member")
	public String handleStep1(){
		return "/member/register/stap1";
	}
	@RequestMapping("/stap2") //BundleViewResolver사용
	public String handleStep2(@RequestParam(value="agree", defaultValue="false") Boolean agree){ //defaultValue는 체크안될때 기본값을 false로 한다는 뜻
		if(!agree){			//@RequestParam는 주로 .jsp의 파라미터값을 가져올때 사용
			
			return "/member/register/stap1";
		}
		else{
		
			return "stap2";
		}
	}
	//이메일 중복확인 아직 미완성
	/*
	@RequestMapping(value="/stap2", method=RequestMethod.POST)
	public ModelAndView emailCheck(@ModelAttribute Member dto, HttpServletResponse resp, HttpServletRequest req) throws IOException{
		ModelAndView mav = new ModelAndView("stap2");
		try{
			mav.addObject(dto);
			System.out.println("dto이메일"+req.getParameter("member_email"));
			System.out.println("dto이메일"+dto.getMember_email());
			
			memberService.selectByEmail(dto.getMember_email());
		}
		catch(Exception err){
			System.out.println("EmailCheckErr"+err);
			PrintWriter out = resp.getWriter();     
			out.println("<script>alert('이메일이 중복됩니다!!!');</script>");
			out.flush();
		}
		
		return mav;
		
	}*/
	/*
	@RequestMapping("/stap3") //BundleViewResolver사용, 위처럼 파라미터를 4개를 받아와도 되지만 파라미터가 많은 관계로 dto형태로 가져오는게 편하다.
	public ModelAndView handleStep3(@ModelAttribute RegisterRequest dto){//@ModelAttribute는 dto등의 객체를 주로 받아올때 사용
		//System.out.println("dto:"+dto.getName());
		ModelAndView mav = new ModelAndView("stap3");
		mav.addObject("mem", dto);
		return mav;
	}*/
	
	@RequestMapping(value="/stap3") //BundleViewResolver사용, 위처럼 파라미터를 4개를 받아와도 되지만 파라미터가 많은 관계로 dto형태로 가져오는게 편하다.
	public ModelAndView handleStep3(@ModelAttribute Member dto){//@ModelAttribute는 dto등의 객체를 주로 받아올때 사용
		System.out.println("1뭐야뭐야:"+dto.getMember_name());
		
		ModelAndView mav = new ModelAndView("stap3");
		try {
			
			mav.addObject("dto", dto);
			memberService.write(dto);
			
		} catch (Exception err) {
			System.out.println("!!!!!!writeErrorMessage!!!!!:"+ err);
			
		}
		return mav;
		//"/WEB-INF/views/board/list.jsp";//dB를 거치지 않고 가기때문에 입력한 결과값이 보지 않고 나온다. list.board는 액션
																		//이것을 해결하기위해 redirect:를 사용한다. 마치 클라이언트가 다시 서버에 요청한것 처럼...결과를 준다.
		
	}
}
