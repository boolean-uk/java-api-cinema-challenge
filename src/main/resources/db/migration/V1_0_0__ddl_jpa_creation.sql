
    create table customer (
       id bigserial not null,
        created_at timestamp(6),
        updated_at timestamp(6),
        email varchar(255) not null,
        name varchar(255) not null,
        phone varchar(255) not null,
        primary key (id)
    );

    create table movie (
       id bigserial not null,
        created_at timestamp(6),
        updated_at timestamp(6),
        description varchar(255) not null,
        rating varchar(255) not null,
        runtime_mins integer not null,
        title varchar(255) not null,
        primary key (id)
    );

    create table screening (
       id bigserial not null,
        created_at timestamp(6),
        updated_at timestamp(6),
        capacity integer not null,
        screen_number bigint not null,
        starts_at timestamp(6),
        movie_id bigint,
        primary key (id)
    );

    create table ticket (
       id bigserial not null,
        created_at timestamp(6),
        updated_at timestamp(6),
        num_seats integer not null,
        customer_id bigint,
        screening_id bigint,
        primary key (id)
    );

    alter table if exists screening 
       add constraint FKfp7sh76xc9m508stllspchnp9 
       foreign key (movie_id) 
       references movie;

    alter table if exists ticket 
       add constraint FKmli0eqrecnnqvdgv3kcx7d9m8 
       foreign key (customer_id) 
       references customer;

    alter table if exists ticket 
       add constraint FKslsbfjfvsw5v43w11jm31x0c6 
       foreign key (screening_id) 
       references screening;

    create table hibernate_sequences (
       sequence_name varchar(255) not null,
        next_val bigint,
        primary key (sequence_name)
    );

    insert into hibernate_sequences(sequence_name, next_val) values ('default',0);