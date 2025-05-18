package in.darshan.elite.service;

import java.util.List;

import in.darshan.elite.entity.Consultancy;
import in.darshan.elite.response.ConsultResponse;

public interface ConsultancyServices {
	
	public Integer book(Consultancy consultancy);

	public List<ConsultResponse> getConsultencies(Integer userId);



}
