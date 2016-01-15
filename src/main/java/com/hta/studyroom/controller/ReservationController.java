package com.hta.studyroom.controller;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hta.studyroom.repository.StudyroomDto;
import com.hta.studyroom.service.StudyroomService;
import com.hta.member.service.MemberService;


@Controller
public class ReservationController {
	private StudyroomService studyroomService;
//	private MemberService memberService;
	
//	public void setMemberService(MemberService memberService) {
//		this.memberService = memberService;
//	}

	public void setStudyroomService(StudyroomService studyroomService) {
		this.studyroomService = studyroomService;
	}

	@RequestMapping("/reservation.study")
	public ModelAndView form(){
		ModelAndView mav = new ModelAndView("/study_room/reservation/step1");
		return mav;
		
	}
	/*
	@RequestMapping(value="/reservation2.study", method=RequestMethod.POST)//POST방식 이동, 예form로 통해 이동
	public String  resSubmitted(@ModelAttribute StudyroomDto studyroomdto){//@ModelAttribute란
		System.out.println("예약 저장....");
		System.out.println(studyroomdto.getMember_email());
		System.out.println(studyroomdto.getReservation_date());
		System.out.println(studyroomdto.getTable_num());
		System.out.println(studyroomdto.getReservation_time());
		
		//System.out.println(dto.getB_title() +","+ dto.getB_content());
		try {
			studyroomService.resWrite(studyroomdto);
		} catch (Exception err) {
			System.out.println("resWriteErrorMessage:"+ err);
		}
		return "/study_room/reservation/step2";//"/WEB-INF/views/board/list.jsp";//dB를 거치지 않고 가기때문에 입력한 결과값이 보지 않고 나온다.
																		//이것을 해결하기위해 redirect:를 사용한다. 마치 클라이언트가 다시 서버에 요청한것 처럼...결과를 준다.
	}
	
	*/
	
	@RequestMapping(value="/reservation2.study", method=RequestMethod.POST )
	public ModelAndView submit(@ModelAttribute StudyroomDto studyroomdto, HttpSession session, HttpServletRequest req , HttpServletResponse resp){
		Calendar cal = Calendar.getInstance();//현재시각
		String nowyear = String.valueOf(cal.get(Calendar.YEAR));
		int nowmonth = cal.get(Calendar.MONTH)+1;
		int nowday = cal.get(Calendar.DAY_OF_MONTH);	
		System.out.println("현재시간은" + nowyear +" , "+ nowmonth +" , "+ nowday);
		
		//선택한 (문자형)날짜 정확한 (숫자)날짜로 변환시키기(charAt()-->0부터 시작)
		int month = studyroomdto.getReservation_date().charAt(6)-48;//월 숫자
		int firstday = (studyroomdto.getReservation_date().charAt(8)-48)*10;//날짜의 십자리 숫자를 만드는 수식
		int secondday = studyroomdto.getReservation_date().charAt(9)-48;//날짜 일자리 숫자 
		int sumday = (firstday + secondday);//선택한 정확한 날짜
		String year = studyroomdto.getReservation_date().substring(0, 4);//처음 인자 순서 값은 가져가고 마지막 인자 순서는 전에 인자순서를 가지고 온다.
		
		/*//Map 형식으로 저장하여 addAllObject()로 page에 볼낼 수있는데 이때 페이지에서 EL태그로 출력하는 방법는 그냥 key이름 값으로 불러서 쓸수 있다는데 나중에 해봐야겠다....
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("day", sumday);
		map.put("month", month);
		map.put("year", new Integer(year));
		System.out.println("Map들어가나욤??"+ map.get("year")+" , "+map.get("month")+ " , " +map.get("day"));
		*/
		
		List list = new ArrayList();
		list.add(year);//0
		list.add(month);//1
		list.add(sumday);//2
		System.out.println("list 출력 ㅅㅂ"+ list.get(0));
		System.out.println("월!!!!!!!!"+month);
		System.out.println("일!!!!!!!!"+firstday + " and " +secondday + " = " + sumday);
		System.out.println("년!!!!!!!!"+year);
		System.out.println("1예약 저장....");
		System.out.println("1확인"+studyroomdto.getMember_email());
		System.out.println("2확인"+studyroomdto.getReservation_date());
		System.out.println("3확인"+studyroomdto.getTable_num());
		System.out.println("4확인"+studyroomdto.getReservation_time());
		System.out.println("5확인"+studyroomdto.getClass());
		System.out.println("6확인"+studyroomdto.getMember_name());
		try {
			
			
			//계정을 세션에 저장.
			String memberEmail = (String)session.getAttribute("email");
				session.setAttribute("member_email", memberEmail); //session에 이메일값 저장.
				studyroomdto.setMember_email(memberEmail);
				
			String memberName = (String) session.getAttribute("name");
				session.setAttribute("member_name", memberName);
				studyroomdto.setMember_name(memberName);
				
			//StudyroomDto dto = studyroomService.findBySameResurvation(studyroomdto);
			if(nowyear.equals(year) && nowmonth<month){
				//if(nowday<=sumday){
					System.out.println("1. write 시~~~~~~~~작!!!");
					//step1.jsp에서 전달받은 데이터를 스터디룸 좌석 예약 DB에 입력
					studyroomService.resWrite(studyroomdto);
				//}
				/*else{
					System.out.println("1. mav2 시~~~~~~~~작!!!");
					ModelAndView mav2 = new ModelAndView("/study_room/reservation/step1Error");
					mav2.addObject("list", list);//List 형식으로 보낼때
					//mav2.addAllObjects(map); //Map 형식으로 보낼때
					//mav2.addObject("month", month);//선택한 월
					//mav2.addObject("year", year);//선택한 년도
					//mav2.addObject("day", sumday);//선택한 정확날짜
					return mav2;
					
				}*/
			}
			else if(nowyear.equals(year) && nowmonth==month && nowday<=sumday){
				System.out.println("2. write 시~~~~~~~~작!!!");
				//step1.jsp에서 전달받은 데이터를 스터디룸 좌석 예약 DB에 입력
				studyroomService.resWrite(studyroomdto);
			}
			else{
				System.out.println("2. mav2 시~~~~~~~~작!!!");
				ModelAndView mav2 = new ModelAndView("/study_room/reservation/step1Error");
				mav2.addObject("list", list);//List 형식으로 보낼때
				//mav2.addAllObjects(map); //Map 형식으로 보낼때
				//mav2.addObject("month", month);//선택한 월
				//mav2.addObject("year", year);//선택한 년도
				//mav2.addObject("day", sumday);//선택한 정확날짜
				return mav2;
				
			}
			
			
		}catch (Exception err) {
			
			System.out.println("스터디룸 좌석 예약 부분:"+ err);
			System.out.println("3. mav2 시~~~~~~~~작!!!");
			ModelAndView mav2 = new ModelAndView("/study_room/reservation/step1Error");
			mav2.addObject("list", list);//List 형식으로 보낼때
			//mav2.addAllObjects(map); //Map 형식으로 보낼때
			//mav2.addObject("month", month);//선택한 월
			//mav2.addObject("year", year);//선택한 년도
			//mav2.addObject("day", sumday);//선택한 정확날짜
			return mav2;
		}
		
		ModelAndView mav = new ModelAndView("/study_room/reservation/step2");
		mav.addObject("studyroomdto", studyroomdto);
		
		
		
		
		
		System.out.println("예약 저장....");
		System.out.println("1-2확인"+studyroomdto.getMember_email());
		System.out.println("2확인"+studyroomdto.getReservation_date());
		System.out.println("3확인"+studyroomdto.getTable_num());
		System.out.println("4확인"+studyroomdto.getReservation_time());
		System.out.println("5확인"+studyroomdto.getClass());
		System.out.println("6확인"+studyroomdto.getMember_name());
		System.out.println("6확인"+studyroomdto.getMember_name());
		return mav;
	}
	
	

}