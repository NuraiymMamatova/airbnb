insert into users(id, name, email, password, image, count_of_booked_house)
values (1, 'Airbnb', 'airbnbhomesweethome1@gmail.com', '$2a$12$HJmsalvo/0.5G2oh1QapuugtYCMHHpFgf27H/02PwYTtKU4FXTfre', null,
        null);

insert into users(id, name, email, password, image, count_of_booked_house)
values (2, 'Nuraiym', 'nuraiym@gmail.com', '$2a$12$QxFJ6yYms8SqIVlOR5R3G.oLzOuC3mVALF6GWzoFbCa0W37usfPkS', null, null);

insert into role(id, name_of_role)
values (1, 'ADMIN');

insert into role(id, name_of_role)
values (2, 'USER');

insert into roles_users(role_id, user_id)
VALUES (1, 1);

insert into roles_users(role_id, user_id)
VALUES (2, 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, count_of_booked_user,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (1, 20, 'Asman', 'flat-screen TV and a shared bathroom with a shower.' ||
                        ' At Garden Hotel & SPA the rooms have bed linen and towels.
    ', 5, 0, 1, 3,false, 1, null, 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, count_of_booked_user,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (2, 15, 'Ihlas', 'Tit box, a flat-screen TV and a shared bathroom with a shower. At Garden' ||
                        ' Hotel & SPA the rooms have bed linen and towels.
    ', 10, 1, 1, 2,false, 1, null, 1);

insert into houses(id, price, title, description_of_listing, max_of_guests, count_of_booked_user,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (3, 29, 'Elite', ' and towels.
', 10, 1, 1, 2,false, 1, null, 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, count_of_booked_user,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (4, 10, 'Delmar', ' boa shared bathroom with a shower. At Garden ' ||
                         'Hotel & SPA the rooms have bed linen and towels.
    ', 10, 1, 0, 3, false,1, null, 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, count_of_booked_user,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (5, 40, 'Best', 'e bed linen and towels.', 10, 1, 0, 3, false,1, null, 2);

insert into locations(id, address, region, town_or_province, house_id)
values (1,'Toktogul 12','Bishkek','Goin',1);

insert into locations(id, address, region, town_or_province, house_id)
values (2,'Kurmanjan 36','Osh','Samsa',2);

insert into locations(id, address, region, town_or_province, house_id)
values (3,'Saburov 19','Bishkek','Vefa',3);

insert into locations(id, address, region, town_or_province, house_id)
values (4,'Pushkin 12','Bishkek','Red',4);

insert into locations(id, address, region, town_or_province, house_id)
values (5,'Rasulov 12','Bishkek','Gum',5);


insert into house_images(house_id, images)
values (1, 'image');
insert into house_images(house_id, images)
values (2, 'image');

insert into house_images(house_id, images)
values (3, 'image');
insert into house_images(house_id, images)
values (4, 'image');
insert into house_images(house_id, images)
values (5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbGoeZg06hhqGsjJ-DrxjgEmU5o7Jua2vB9Q&usqp=CAU');

insert into booking_dates(id, check_in, check_out, price, house_id)
values (1, null, null, 20, 1);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (2, null, null, 15, 2);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (3, null, null, 29, 3);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (4, null, null, 10, 4);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (5, null, null, 40, 5);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (1, now(), 0, 5, 4, 'this was one of the nicest places we stayed in the four weeks w...', 1, 2);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (2, now(), 0, 10, 5, 'ayed in the four weeks w...', 2, 2);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (3, now(), 0, 3, 3, 'Good', 3, 2);

insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (4, now(), 0, 5, 4, 'nicest places we stayed in the four weeks w...', 4, 2);


insert into feedbacks(id, created_feedback, dislike, take_a_look, rating, text, house_id, user_id)
values (5, now(), 2, 5, 4, 'Grlaces we stayed in the four weeks w...', 5, 2);



