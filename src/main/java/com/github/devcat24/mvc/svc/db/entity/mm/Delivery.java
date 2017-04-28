package com.github.devcat24.mvc.svc.db.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode(exclude = {"orderItems"})      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString(exclude = {"orderItems"})               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_delivery")
@Table(name="jpa_lab_delivery")
public class Delivery implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DELIVERY_ID")
    @Getter @Setter
    private Long id;

    @OneToOne(mappedBy = "delivery")
    @Getter @Setter
    private Order order;

    @Getter @Setter
    private String city;

    @Getter @Setter
    private String street;

    @Getter @Setter
    private String zipcode;

    @Enumerated(EnumType.STRING)
    @Getter @Setter
    private DeliveryStatus status;

}
