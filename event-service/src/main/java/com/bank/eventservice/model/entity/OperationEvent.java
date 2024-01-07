package com.bank.eventservice.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    private String action;
    private float amount;
    private float subjectBalance;
    private float objectBalance;
    private String subject;
    private String object;
    private String path;

    public OperationEvent(Date date, String action, float amount, float subjectBalance,
        float objectBalance, String subject, String object, String path) {
        this.date = date;
        this.action = action;
        this.amount = amount;
        this.subjectBalance = subjectBalance;
        this.objectBalance = objectBalance;
        this.subject = subject;
        this.object = object;
        this.path = path;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }
}
