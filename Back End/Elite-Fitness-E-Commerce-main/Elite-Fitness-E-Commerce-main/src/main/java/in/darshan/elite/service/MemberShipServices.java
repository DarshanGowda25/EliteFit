package in.darshan.elite.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import in.darshan.elite.DTO.UserNameMembershipDTO;
import in.darshan.elite.entity.MemberShip;
import in.darshan.elite.response.MemberShipResponse;

public interface MemberShipServices {
	
	public Integer register(MemberShip memberShip);
	public boolean iscenterPresent(String center);
	public boolean istypePresent(String type);
	public LocalDate vaildTill(Integer id);
	public List<MemberShip> getMemberships();
	public List<MemberShipResponse> getMemberships(Integer id);
	public Integer getMemberShipCount();
	public List<UserNameMembershipDTO> getMembershipsDashData(PageRequest pageRequest);
	public List<MemberShip> getAllMemberships();
	public MemberShip getMembership(Integer id);
	public void updateStatus(MemberShip membership);
	

}
