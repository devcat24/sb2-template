package com.github.devcat24.mvc.db.entity.mm;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//    9. invoke Stored Procedure using NamedStoredProcedureQuery
//     -> Basic feature works well with 'Entity' result type but,
//        with projection type result it doesn't work smoothly like other cases.(especially with ResultSet-Mappings)
//     -> utilize 'NamedNativeQuery' type !
//    ------------------------------------------------------------------------------------------------------------------
//    @NamedStoredProcedureQuery(name = "Member.memberAfterWithNamedStoredProcedure", procedureName = "get_member_after", parameters = {
//                    @StoredProcedureParameter(mode = ParameterMode.IN, name = "search_id", type = Long.class),
//                    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "id", type = Long.class),
//                    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "name", type = String.class),
//                    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "city", type = String.class),
//                    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "street", type = String.class),
//                    @StoredProcedureParameter(mode = ParameterMode.OUT, name = "zipcode", type = String.class)
//                }, resultSetMappings="mappingMemberAfterWithNamedStoreProcedure"
//            )
//    @SqlResultSetMapping(name="mappingMemberAfterWithNamedStoreProcedure",
//            classes = {
//                    @ConstructorResult(
//                            targetClass = MemberDTO.class,
//                            columns = {
//                                    @ColumnResult(name="id", type=Long.class),
//                                    @ColumnResult(name="name", type=String.class),
//                                    @ColumnResult(name="city"),
//                                    @ColumnResult(name="street"),
//                                    @ColumnResult(name="zipcode")
//                            }
//                    )
//            }
//    )
//    ------------------------------------------------------------------------------------------------------------------


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode(exclude = {"orders"})      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString(exclude = {"orders"})               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_member")
@Table(name="jpa_lab_member")
public class Member extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String name;

    @Setter @Getter
    private String city;

    @Setter @Getter
    private String street;

    @Setter @Getter
    private String zipcode;

    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    // orphanRemoval -> only for '@OneToMany/@OneToOne' relation - remove orphan slave : be cautious to apply
    @Setter @Getter
    private List<Order> orders = new ArrayList<>();

}
