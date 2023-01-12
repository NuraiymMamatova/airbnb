insert into users(id,name,email,password,image,count_of_booked_house)
values (1,'Eldiar','eldiarro2004@gmail.com','eldiar',null,null);

insert into users(id,name,email,password,image,count_of_booked_house)
values (2,'Nuraiym','nuraiym@gmail.com','nuraiym',null,null);

insert into role(id,name_of_role)
values (1,'ADMIN');

insert into role(id,name_of_role)
values (2,'USER');

insert into roles_users(role_id, user_id)
VALUES(1,1);

insert into roles_users(role_id, user_id)
VALUES(2,2);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,owner_id)
values(1,20,'Asman','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle,' ||
                    ' a fridge, a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower.' ||
                    ' At Garden Hotel & SPA the rooms have bed linen and towels.
',5,0,0,2,1,null,1);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,owner_id)
values(2,15,'Ihlas','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge,' ||
                    ' a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden' ||
                    ' Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,null,1);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,owner_id)
values(3,29,'Elite','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge' ||
                    ', a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower.' ||
                    ' At Garden Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,null,1);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,owner_id)
values(4,10,'Delmar','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge,' ||
                     ' a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden ' ||
                     'Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,null,2);

insert into houses(id,price,title,description_of_listing,max_of_guests,count_of_booked_user,
                   house_type,houses_status,houses_booked,date_house_created,owner_id)
values(5,40,'Best','The hotel will provide guests with air-conditioned rooms offering a desk, a kettle, a fridge,' ||
                   ' a minibar, a safety deposit box, a flat-screen TV and a shared bathroom with a shower. At Garden ' ||
                   'Hotel & SPA the rooms have bed linen and towels.
',10,1,0,2,1,null,2);

insert into location(id, address, region, town_or_province, house_id)
values (1,'Toktogul 12','Bishkek','Goin',1);

insert into location(id, address, region, town_or_province, house_id)
values (2,'Kurmanjan 36','Osh','Samsa',2);

insert into location(id, address, region, town_or_province, house_id)
values (3,'Saburov 19','Bishkek','Vefa',3);

insert into location(id, address, region, town_or_province, house_id)
values (4,'Pushkin 12','Bishkek','Red',4);

insert into location(id, address, region, town_or_province, house_id)
values (5,'Rasulov 12','Bishkek','Gum',5);


insert into house_images(house_id, images)
values (1,'image');
insert into house_images(house_id, images)
values (2,'image');

insert into house_images(house_id, images)
values (3,'image');
insert into house_images(house_id, images)
values (4,'image');
insert into house_images(house_id, images)
values (5,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbGoeZg06hhqGsjJ-DrxjgEmU5o7Jua2vB9Q&usqp=CAU');

insert into booking_dates(id, check_in, check_out, price, house_id)
values (1,'12.01.2023','17.01.2023',20,1);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (2,'22.01.2023','17.01.2023',15,2);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (3,'17.01.2023','23.01.2023',29,3);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (4,'28.01.2023','02.02.2023',10,4);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (5,'12.21.2023','15.02.2023',40,5);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (1,now(),0,5,4,'Great location, really pleasant and clean rooms, but the thing that makes this such a good place to stay are the staff. All' ||
                      ' of the people are incredibly helpful and generous with their time and advice. We travelled with two six year olds and lots of ' ||
                      'luggage and despite the stairs up to the elevator this was one of the nicest places we stayed in the four weeks w...',1,2);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (2,now(),0,10,5,'Great location, really pleasant and clean rooms, but the thing that makes this such a good place to stay are the staff. All' ||
                      ' of the people are incredibly helpful and generous with their time and advice. We travelled with two six year olds and lots of ' ||
                      'luggage and despite the stairs up to the elevator this was one of the nicest places we stayed in the four weeks w...',2,2);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (3,now(),0,3,3,'Great location, really pleasant and clean rooms, but the thing that makes this such a good place to stay are the staff. All' ||
                      ' of the people are incredibly helpful and generous with their time and advice. We travelled with two six year olds and lots of ' ||
                      'luggage and despite the stairs up to the elevator this was one of the nicest places we stayed in the four weeks w...',3,2);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (4,now(),0,5,4,'Great location, really pleasant and clean rooms, but the thing that makes this such a good place to stay are the staff. All' ||
                      ' of the people are incredibly helpful and generous with their time and advice. We travelled with two six year olds and lots of ' ||
                      'luggage and despite the stairs up to the elevator this was one of the nicest places we stayed in the four weeks w...',4,2);


insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (5,now(),2,5,4,'Great location, really pleasant and clean rooms, but the thing that makes this such a good place to stay are the staff. All' ||
                      ' of the people are incredibly helpful and generous with their time and advice. We travelled with two six year olds and lots of ' ||
                      'luggage and despite the stairs up to the elevator this was one of the nicest places we stayed in the four weeks w...',5,2);



