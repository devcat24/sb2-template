-- noinspection SqlNoDataSourceInspectionForFile

-- noinspection SqlDialectInspectionForFile


    create table item01 (
        itemno Integer not null,
        product_name TEXT not null,
        primary key (itemno)
    );
    create table CATEGORY_ITEM (
        CATEGORY_ID bigint not null,
        ITEM_ID bigint not null
    );
    create table jpa_lab_category (
        CATEGORY_ID bigint not null auto_increment,
        name varchar(255),
        PARENT_ID bigint,
        primary key (CATEGORY_ID)
    );
    create table jpa_lab_delivery (
        DELIVERY_ID bigint not null auto_increment,
        city varchar(255),
        status varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (DELIVERY_ID)
    );
    create table jpa_lab_item (
        DTYPE varchar(31) not null,
        ITEM_ID bigint not null auto_increment,
        name varchar(255),
        price integer not null,
        stockQuantity integer not null,
        actor varchar(255),
        director varchar(255),
        artist varchar(255),
        etc varchar(255),
        author varchar(255),
        isbn varchar(255),
        primary key (ITEM_ID)
    );
    create table jpa_lab_member (
        MEMBER_ID bigint not null auto_increment,
        createDate datetime,
        lastModifiedDate datetime,
        city varchar(255),
        name varchar(255),
        street varchar(255),
        zipcode varchar(255),
        primary key (MEMBER_ID)
    );
    create table jpa_lab_member_trends (
        ITEM_ID bigint not null,
        MEMBER_ID bigint not null,
        count integer not null,
        primary key (ITEM_ID, MEMBER_ID)
    );
    create table jpa_lab_order (
        ORDER_ID bigint not null auto_increment,
        createDate datetime,
        lastModifiedDate datetime,
        orderDate datetime,
        status varchar(255),
        DELIVERY_ID bigint,
        MEMBER_ID bigint,
        primary key (ORDER_ID)
    );
    create table jpa_lab_order_item (
        ORDER_ITEM_ID bigint not null auto_increment,
        count integer not null,
        orderPrice integer not null,
        ITEM_ID bigint,
        ORDER_ID bigint,
        primary key (ORDER_ITEM_ID)
    );
    create table tb_emp_01 (
        empno Integer not null,
        ename TEXT not null,
        job TEXT not null,
        mgr Integer,
        primary key (empno)
    );
    alter table CATEGORY_ITEM
        add constraint FK5hxkg1ym11ky3afk2816c5ns1
        foreign key (ITEM_ID)
        references jpa_lab_item (ITEM_ID);

    alter table CATEGORY_ITEM
        add constraint FKa4rug9xo4tx1peauv272knrkb
        foreign key (CATEGORY_ID)
        references jpa_lab_category (CATEGORY_ID);
    alter table jpa_lab_category
        add constraint FKmlhy5o1bnhxlfcujlysyu54q3
        foreign key (PARENT_ID)
        references jpa_lab_category (CATEGORY_ID);
    alter table jpa_lab_order
        add constraint FKex3wtaguuvothf749bhcrt1xp
        foreign key (DELIVERY_ID)
        references jpa_lab_delivery (DELIVERY_ID);
    alter table jpa_lab_order
        add constraint FKg55vwqw1h42mx432mujubxt2y
        foreign key (MEMBER_ID)
        references jpa_lab_member (MEMBER_ID);
    alter table jpa_lab_order_item
        add constraint FKarla3xbaydifuprrw1g0e9opj
        foreign key (ITEM_ID)
        references jpa_lab_item (ITEM_ID)
		    alter table jpa_lab_order_item
        add constraint FKh0tl77rb0d4w93rw6bvhfuj9t
        foreign key (ORDER_ID)
        references jpa_lab_order (ORDER_ID);

	-- CREATE PROCEDURE get_member_after (IN search_id int)
	-- BEGIN select member_id as id, name, city, street, zipcode from jpa_lab_member where MEMBER_ID > search_id ;
	-- END

	-- CALL get_member_after(0);
