package com.github.devcat24.mvc.db.entity.hr;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;

//@Data                 // @Data gives too much features        -> not recommended for JPA entity
//@Builder              // Do not use @Builder for class scope  -> use on constructor-method scope !
//@AllArgsConstructor   // @RequiredArgsConstructor, @NoArgsConstructor -> define manually with @Builder annotation
@ToString               // @ToString (exclude = {"deptno"})     -> careful to use @ToString for recursive references !
@NoArgsConstructor(access = AccessLevel.PROTECTED)      // JPA requires at least one constructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
//@Builder                // Support builder pattern method for creating instance   -> better to define in constructor with only mandatory fields!
//@Slf4j                  // lombok annotation for logging (@Slf4j/@Log4j/@Log4j2/@CommonsLog/@Log) -> invoke simply with 'log.info("Sample message");'
@Entity(name="Emp01")
@Table(name="tb_emp_01")
//@ApiModel(description = "All details about the Employee. ")
public class Emp01 implements Serializable{

    @SuppressWarnings("unused")
    @Builder // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
    public Emp01(Integer empno, String ename, String job, Integer mgr) {
        this.empno = empno;
        this.ename = ename;
        this.job = job;
        this.mgr = mgr;
    }

    @NotNull
    @Min(0)
    @Setter @Getter @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="empno", unique=true, columnDefinition="Integer")
    //@ApiModelProperty(notes = "The database generated employee ID", example="10001")
    public Integer empno;
    // Although JPA support column type like...  Integer(10), Float(7,2)
    // But, some databases (ex. H2, Postgresql...) have the issues about that patterns.
    // Just use like ...  Integer, Long, Float, String

    @NotEmpty
    @Size(min=1, max=30)
    @Setter @Getter
    @NotEmpty @Column(name="ename", nullable=false, columnDefinition="TEXT")
    //@ApiModelProperty(notes = "The employee name", example="Robert")
    public String ename;

    @SuppressWarnings("DefaultAnnotationParam")
    @Setter @Getter
    @Column(name="job", nullable=false, columnDefinition="TEXT", unique=false, updatable=true, length=100)
    //@ApiModelProperty(notes = "The employee job title", example="Manager")
    public String job;

    @Setter @Getter
    @Column(name="mgr", columnDefinition="Integer")
    //@ApiModelProperty(notes = "The manager of employee", example="10001")
    public Integer mgr;



}
