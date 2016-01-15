package com.hta.studyroom.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hta.studyroom.repository.StudyroomDto;
import com.hta.studyroom.service.StudyroomService;

@Controller
public class DeleteReservationController {
	
	private StudyroomService studyroomService;
	
	public void setStudyroomService(StudyroomService studyroomService) {
		this.studyroomService = studyroomService;
	}
	
	@RequestMapping("/reservation.delete")
	public String delete(StudyroomDto dto){
		System.out.println("삭제할 예약 넘버:"+dto.getReservation_num());
		return null;
	}
	
}
