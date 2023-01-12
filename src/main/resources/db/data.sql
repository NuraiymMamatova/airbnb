

insert into users(id,name,email,password,image,count_of_booked_house)
values (1,'Eldiar','eldiarro2004@gmail.com','eldiar',null,null);

insert into users(id,name,email,password,image,count_of_booked_house)
values (2,'Nuraiym','nuraiym@gmail.com','nuraiym',null,null);

insert into role(id,name_of_role)
values (1,'Admin');

insert into roles_users(role_id, user_id)
VALUES(1,1);


insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,location_id
    ,owner_id)
values(1,20,'Asman','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge, a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden Hotel & SPA the rooms have bed linen and towels.
',5,0,0,2,1,now(),null,1);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,location_id
    ,owner_id)
values(2,15,'Ihlas','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge, a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,now(),null,2);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,location_id
    ,owner_id)
values(3,15,'Elite','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge, a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,now(),null,2);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,location_id
    ,owner_id)
values(4,15,'Delmar','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge, a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,now(),null,1);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,location_id
    ,owner_id)
values(5,15,'Best','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge, a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,now(),null,1);




insert into location(id, address, region, town_or_province, house_id)
values (1,'Toktogul 12','Bishkek','Goin',1);

insert into location(id, address, region, town_or_province, house_id)
values (1,'Kurmanjan 36','Osh','Samsa',2);

insert into location(id, address, region, town_or_province, house_id)
values (1,'Saburov 19','Bishkek','Vefa',3);

insert into location(id, address, region, town_or_province, house_id)
values (1,'Pushkin 12','Bishkek','Red',4);

insert into location(id, address, region, town_or_province, house_id)
values (1,'Rasulov 12','Bishkek','Gum',5);






