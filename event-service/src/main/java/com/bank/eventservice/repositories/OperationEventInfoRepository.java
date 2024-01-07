package com.bank.eventservice.repositories;


import com.bank.eventservice.model.entity.OperationEvent;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationEventInfoRepository extends JpaRepository<OperationEvent, Long> {

  List<OperationEvent> findBySubjectOrObject(String subject, String object);

  List<OperationEvent> findByObjectAndDate(String object, Date date);


}
