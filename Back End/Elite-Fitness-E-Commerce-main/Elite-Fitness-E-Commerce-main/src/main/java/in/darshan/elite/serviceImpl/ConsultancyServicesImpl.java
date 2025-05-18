package in.darshan.elite.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.darshan.elite.entity.Consultancy;
import in.darshan.elite.repository.ConsultRepo;
import in.darshan.elite.response.ConsultResponse;
import in.darshan.elite.service.ConsultancyServices;

@Service
public class ConsultancyServicesImpl implements ConsultancyServices{

	@Autowired
	private ConsultRepo consultRepo;
	
	@Override
	public Integer book(Consultancy consultancy) {
		// TODO Auto-generated method stub
		return consultRepo.save(consultancy).getConsult_id();
	}

	@Override
	public List<ConsultResponse> getConsultencies(Integer userId) {
		// TODO Auto-generated method stub
		return consultRepo.findByUserId(userId);
	}



}
