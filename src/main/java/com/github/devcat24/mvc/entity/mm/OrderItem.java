package com.github.devcat24.mvc.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_order_item")
@Table(name="jpa_lab_order_item")
public class OrderItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_ID")
    @Setter @Getter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_ID")    // -> this table has 'ITEM_ID' column which maps 'item table' & 'item table' doesn't have any mapping column
    // @Column(name = "ITEM_ID")     // ->  '@Column(s)' not allowed on a @ManyToOne property : '@JoinColumn' also creates column
    @Setter @Getter
    private Item item;

    @ManyToOne
    @JoinColumn(name = "ORDER_ID")   // -> this table has 'ORDER_ID' column which maps 'order table' & 'Order table' doesn't have any mapping column
    //@Column(name = "ORDER_ID")     // ->  '@Column(s)' not allowed on a @ManyToOne property : '@JoinColumn' also creates column
    @Setter @Getter
    private Order order;

    @Setter @Getter
    private int orderPrice;

    @Setter @Getter
    private int count;
}
