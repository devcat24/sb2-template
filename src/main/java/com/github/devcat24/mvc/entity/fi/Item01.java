package com.github.devcat24.mvc.entity.fi;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               //@ToString (exclude = {"deptno"})
@Entity(name="Item01")
@Table(name="item01")
public class Item01 implements Serializable {

    @Setter @Getter @Id
    @Column(name="itemno", unique=true, columnDefinition="Integer")
    public Integer itemno;
    // Although JPA support column type like...  Integer(10), Float(7,2)
    // But, some databases (ex. H2, Postgresql...) have the issues about that patterns.
    // Just use like ...  Integer, Long, Float, String

    @Setter @Getter
    @Column(name="product_name", nullable=false, columnDefinition="TEXT")
    public String productName;


}
