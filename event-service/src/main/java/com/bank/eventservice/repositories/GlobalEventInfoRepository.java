package com.bank.eventservice.repositories;


import com.bank.eventservice.model.entity.GlobalEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GlobalEventInfoRepository extends JpaRepository<GlobalEvent, Long> {

  List<GlobalEvent> findAllByOrderById();
}
