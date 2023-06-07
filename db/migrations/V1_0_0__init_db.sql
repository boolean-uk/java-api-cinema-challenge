create table customers (
       id serial not null,
        created_at timestamp(6) with time zone,
        email varchar(255),
        name varchar(255),
        phone varchar(255),
        updated_at timestamp(6) with time zone,
        primary key (id)
    );

    create table movies (
       id serial not null,
        created_at timestamp(6) with time zone,
        description varchar(255),
        rating varchar(255),
        runtime_mins integer,
        title varchar(255),
        updated_at timestamp(6) with time zone,
        primary key (id)
    );

    create table screenings (
       id serial not null,
        capacity integer,
        created_at timestamp(6) with time zone,
        screen_number integer,
        starts_at timestamp(6) with time zone,
        updated_at timestamp(6) with time zone,
        movie_id integer,
        primary key (id)
    );

    create table tickets (
       id serial not null,
        created_at timestamp(6) with time zone,
        num_seats integer,
        updated_at timestamp(6) with time zone,
        customer_id integer,
        screening_id integer,
        primary key (id)
    );

    alter table if exists screenings
       add constraint FKrnko8743nv2o7jd7ix2wtcyf
       foreign key (movie_id)
       references movies;

    alter table if exists tickets
       add constraint FKi81xre2n3j3as1sp24j440kq1
       foreign key (customer_id)
       references customers;

    alter table if exists tickets
       add constraint FKa8cgc3b3atbn8sr3nebdygo4a
       foreign key (screening_id)
       references screenings;