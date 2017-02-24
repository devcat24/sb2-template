package com.github.devcat24.mvc.entity.mm;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor     // @RequiredArgsConstructor, @NoArgsConstructor
@EqualsAndHashCode(exclude = {"items", "parent", "child"})      // @EqualsAndHashCode(exclude = {"deptno"})
@ToString(exclude = {"items", "parent", "child"})               // @ToString (exclude = {"deptno"})
@Builder                // Support builder pattern method for creating instance ex. Item item = Item.builder().itemno(1002).name("pen").build();
@Slf4j

@Entity(name="jpa_lab_category")
@Table(name="jpa_lab_category")
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    @Setter @Getter
    private Long id;

    @Setter @Getter
    private String name;

    @ManyToMany
    @JoinTable(name = "CATEGORY_ITEM",
                joinColumns = @JoinColumn(name = "CATEGORY_ID"),
                inverseJoinColumns = @JoinColumn(name = "ITEM_ID"))
    // -> generate table 'CATEGORY_ITEM' which has two columns 'CATEGORY_ID' & 'ITEM_ID'
    // -> @ManyToMany relation is not recommended for real environments
    @Setter @Getter
    private List<Item> items = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    @Setter @Getter
    private Category parent;

    @OneToMany(mappedBy = "parent")
    @Setter @Getter
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }

    public void addItem(Item item){
        items.add(item);
    }

}
