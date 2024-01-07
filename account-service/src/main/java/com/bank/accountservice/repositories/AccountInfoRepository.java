package com.bank.accountservice.repositories;


import com.bank.accountservice.model.entities.Account;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountInfoRepository extends JpaRepository<Account, Long> {

  Optional<Account> findByEmailIgnoreCase(String email);
}
