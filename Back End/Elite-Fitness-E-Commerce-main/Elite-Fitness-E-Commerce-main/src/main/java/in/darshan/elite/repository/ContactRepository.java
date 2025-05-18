package in.darshan.elite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.darshan.elite.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{

	@Query(value = "select count(contact_id) from contact where status = 'unanswered'", nativeQuery = true)
	Integer getUnanswered();

}
