package com.github.devcat24.util.monitoring;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor(access = AccessLevel.PROTECTED)      // JPA requires at least one constructor
//@AllArgsConstructor   // @RequiredArgsConstructor, @NoArgsConstructor -> define manually with @Builder annotation
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
//@Builder                // Support builder pattern method for creating instance   -> better to define in constructor with only mandatory fields!
//      ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Entity(name="TXRunRecord")
@Table(name="tx_run_record")
public class TXRunRecord implements Serializable{

    @SuppressWarnings("unused")
    @Builder // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
    public TXRunRecord(Long runRecordId, String taskType, String taskGroup, String taskName, String txId, String txUser, String txStatus, Date startTime, Date completionTime, Long elapsedTime, String params, String returns, String returnDesc, String errorCode, String errorDesc, String invokedFrom, String returnTo, String desc1, String desc2, String desc3) {
        this.runRecordId = runRecordId;
        this.taskType = taskType;
        this.taskGroup = taskGroup;
        this.taskName = taskName;
        this.txId = txId;
        this.txUser = txUser;
        this.txStatus = txStatus;
        this.startTime = startTime;
        this.completionTime = completionTime;
        this.elapsedTime = elapsedTime;
        this.params = params;
        this.returns = returns;
        this.returnDesc = returnDesc;
        this.errorCode = errorCode;
        this.errorDesc = errorDesc;
        this.invokedFrom = invokedFrom;
        this.returnTo = returnTo;
        this.desc1 = desc1;
        this.desc2 = desc2;
        this.desc3 = desc3;
    }

    @Setter @Getter @Id
    @Column(name="run_record_id", unique=true)
    public Long runRecordId;

    @Setter @Getter
    @Column(name="task_type", columnDefinition="TEXT")
    public String taskType;

    @Setter @Getter
    @Column(name="task_group", columnDefinition="TEXT")
    public String taskGroup;

    @Setter @Getter
    @Column(name="task_name", nullable=false, columnDefinition="TEXT")
    public String taskName;

    @Setter @Getter
    @Column(name="tx_id", nullable=false, columnDefinition="TEXT")
    public String txId;

    @Setter @Getter
    @Column(name="tx_user", columnDefinition="TEXT")
    public String txUser;

    @Setter @Getter
    @Column(name="tx_status", columnDefinition="TEXT")
    public String txStatus;
    // tx_failed / tx_success / tx_ongoing

    @Setter @Getter
    @Column(name="start_time") @Temporal(TemporalType.TIMESTAMP)
    public Date startTime;

    @Setter @Getter
    @Column(name="completion_time") @Temporal(TemporalType.TIMESTAMP)
    public Date completionTime;

    @Setter @Getter
    @Column(name="elapsed_time")
    public Long elapsedTime;

    @Setter @Getter
    @Column(name="params", columnDefinition="TEXT")
    public String params;

    @Setter @Getter
    @Column(name="returns", columnDefinition="TEXT")
    public String returns;

    @Setter @Getter
    @Column(name="return_desc", columnDefinition="TEXT")
    public String returnDesc;

    @Setter @Getter
    @Column(name="error_code", columnDefinition="TEXT")
    public String errorCode;

    @Setter @Getter
    @Column(name="error_desc", columnDefinition="TEXT")
    public String errorDesc;

    @Setter @Getter
    @Column(name="invoked_from", columnDefinition="TEXT")
    public String invokedFrom;

    @Setter @Getter
    @Column(name="return_to", columnDefinition="TEXT")
    public String returnTo;

    @Setter @Getter
    @Column(name="desc1", columnDefinition="TEXT")
    public String desc1;

    @Setter @Getter
    @Column(name="desc2", columnDefinition="TEXT")
    public String desc2;

    @Setter @Getter
    @Column(name="desc3", columnDefinition="TEXT")
    public String desc3;


}
