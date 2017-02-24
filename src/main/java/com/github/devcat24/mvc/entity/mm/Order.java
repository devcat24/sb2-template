package com.github.devcat24.mvc.entity.mm;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// === Master of OneToMany/ManyToOne/OneToOne relation ===
// The entity which uses '@JoinColumn(name = "**_ID")' has a field (containing opposite table information) -> master (save mapping information to table column)
// The entity which uses 'mappedBy = "**")' -> slave

// === Unique value generation ===
// GeneratedValue strategy - GenerationType.AUTO : automatically selects strategy based on the dialect
//    > @GeneratedValue(strategy = GenerationType.AUTO)
// GeneratedValue strategy - GenerationType.IDENTITY : auto-increments database column
//    > @GeneratedValue(strategy = GenerationType.IDENTITY)
// GeneratedValue strategy - GenerationType.SQEUENCE : acquire from database sequence
//    > @SequenceGenerator(name="order_seq_generator", sequenceName = "tb_seq_order", initialValue=10, allocationSize=100)
//    > @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_seq_generator")
// GeneratedValue strategy - GenerationType.TABLE : simulates sequence with table
//    > @TableGenerator(name="dev_template_jpa_seq3", initialValue = 1, allocationSize = 100)
//    > @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dev_template_jpa_seq3")


// === infinite loop in OneToMany/ManyToOne Entity ===
// 1. Lombok annotation
//    > exclude field from '@ToString, @EqualsAndHashCode'
//      (required for only one side of relations but, can exclude in both direction(OneToMany/ManyToOne).
// 2. Jackson configuration (each column definition)
//    > OneToMany Entity : define '@JsonBackReference' annotation
//    > ManyToOne Entity : define '@JsonManagedReference' annotation
// 3. Gson configration (each column definition)
//    > ??? (seems to be working automatically)


// === 'No Session' exception on 'FetchType.LAZY' ===
// 1. Spring JPA use LAZY loading by default, to escape 'No Session' exception
//    > Bind JPA operation with Transaction with '@Transactional(value="fooTransactionManager", propagation = Propagation.REQUIRED)' key word
//    > Put JPA operation on '@Service' classes (Do not put JPA operation itself to '@PostConstruct' things ! -> put it '@Service' & invoke that method)
//    > refer to -> 'JPAService.loadInitalData()'


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode(exclude = {"orderItems", "delivery", "member"})      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString(exclude = {"orderItems", "delivery", "member"})               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_order")
@Table(name="jpa_lab_order")
public class Order extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    @Setter @Getter
    private Long id;


    @ManyToOne
    @JsonManagedReference // prevents infinite loop !
    @JoinColumn(name = "MEMBER_ID")
    @Getter
    // '@Setter' should implemented manually to reset relation (Member -> Order)
    private Member member;
    // 'variable name' matched with 'mapped by in @OneToMany Entity'

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "order")
    @JsonBackReference // prevents infinite loop !
    @Setter @Getter
    private List<OrderItem> orderItems = new ArrayList<OrderItem>();

    @Getter
    @OneToOne
    @JoinColumn(name = "DELIVERY_ID")
    private Delivery delivery;

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    @Setter @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Setter @Getter
    @Enumerated(EnumType.STRING)
    private OrderStatus status;


    // @Setter @Getter
    // @Column(name = "MEMBER_ID")
    // private Long memberId;

    public void setMember(Member member){
        if(this.member != null) {
            this.member.getOrders().remove(this);
        }
        this.member = member;
        member.getOrders().add(this);
    }


    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }




}
