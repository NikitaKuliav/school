create table car (
                     id bigserial primary key,
                     mark text,
                     model text,
                     price bigserial
);

create table person (
                        id bigserial primary key,
                        namee text,
                        agee int,
                        has_driver_liceencse bool,
                        car_id bigserial,
                        foreign key (car_id) references car(id)
);