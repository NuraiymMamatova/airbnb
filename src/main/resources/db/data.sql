insert into users(id, name, email, password, image, count_of_booked_house)
values (1, 'Airbnb', 'airbnbhomesweethome1@gmail.com', '$2a$12$HJmsalvo/0.5G2oh1QapuugtYCMHHpFgf27H/02PwYTtKU4FXTfre', null,
        null);

insert into users(id, name, email, password, image, count_of_booked_house)
values (2, 'Nuraiym', 'nuraiym@gmail.com', '$2a$12$QxFJ6yYms8SqIVlOR5R3G.oLzOuC3mVALF6GWzoFbCa0W37usfPkS', null, null);

insert into users(id, name, email, password, image, count_of_booked_house)
values (3, 'Nuraiym', 'nuraiym1@gmail.com', '$2a$12$QxFJ6yYms8SqIVlOR5R3G.oLzOuC3mVALF6GWzoFbCa0W37usfPkS', null, null);

insert into role(id, name_of_role)
values (1, 'ADMIN');

insert into role(id, name_of_role)
values (2, 'USER');

insert into roles_users(role_id, user_id)
VALUES (1, 1);

insert into roles_users(role_id, user_id)
VALUES (2, 2);

insert into roles_users(role_id, user_id)
VALUES (2, 3);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (1, 20, 'Asman', 'flat-screen TV and a shared bathroom with a shower.' ||
                        ' At Garden Hotel & SPA the rooms have bed linen and towels.
    ', 5, 0, 1, 2,false, 1, '2023-09-13 18:20:03', 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (2, 15, 'Ihlas', 'Tit box, a flat-screen TV and a shared bathroom with a shower. At Garden' ||
                        ' Hotel & SPA the rooms have bed linen and towels.
    ', 10, 1, 1, 2,false, 1, '2023-09-13 18:20:03', 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (3, 29, 'Elite', ' and towels.
', 10, 1, 1, 2,false, 1, '2023-09-13 18:20:03', 1);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (4, 10, 'Delmar', ' boa shared bathroom with a shower. At Garden ' ||
                         'Hotel & SPA the rooms have bed linen and towels.
    ', 10, 1, 0, 2, false,1,'2023-09-13 18:20:03', 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status,is_favorite, houses_booked, date_house_created, owner_id)
values (5, 40, 'Best', 'e bed linen and towels.', 10, 1, 0, 2, false,1, '2023-09-13 18:20:03', 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (6, 20, 'Comfort', 'Very good house', 6, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (7, 20, 'Ololo', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 2);


insert into locations(id, address, region, town_or_province, house_id)
values (1,'Toktogul 12','Chui','Goin',1);

insert into locations(id, address, region, town_or_province, house_id)
values (2,'Kurmanjan 36','Osh','Ozgon',2);

insert into locations(id, address, region, town_or_province, house_id)
values (3,'Saburov 19','Naryn','Vefa',3);

insert into locations(id, address, region, town_or_province, house_id)
values (4,'Pushkin 12','Issyk-Kul','Red',4);

insert into locations(id, address, region, town_or_province, house_id)
values (5,'Rasulov 12','Talas','Gum',5);

insert into locations(id, address, region, town_or_province, house_id)
values (6,'Rasulov 20','JalalAbad','Gum',6);

insert into locations(id, address, region, town_or_province, house_id)
values (7,'Tolstoy 43','Batken','Gum',7);

insert into house_images(house_id, images)
values (1, 'https://images.unsplash.com/photo-1568605114967-8130f3a36994?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8aG91c2V8ZW58MHx8MHx8&w=1000&q=80');
insert into house_images(house_id, images)
values (2, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTRh_1qqXmUX9CCTv5xw9UFTx5eZwR9w63SjA&usqp=CAU');
insert into house_images(house_id, images)
values (3, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ18pcad8DDEjL0Jw2sImcnYPQ4lRUOPitbvQ&usqp=CAU');
insert into house_images(house_id, images)
values (4, 'https://images.unsplash.com/photo-1600596542815-ffad4c1539a9?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Mnx8bWFuc2lvbnxlbnwwfHwwfHw%3D&w=1000&q=80');
insert into house_images(house_id, images)
values (5, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbGoeZg06hhqGsjJ-DrxjgEmU5o7Jua2vB9Q&usqp=CAU');
insert into house_images(house_id, images)
values (6, 'https://images.adsttc.com/media/images/63c5/648e/760d/d201/7078/a81d/medium_jpg/bernaung-house-rad-plus-ar-research-artistic-design-plus-architecture_11.jpg?1673880794');
insert into house_images(house_id, images)
values (7, 'https://www.dhanvibuilders.com/wp-content/uploads/2022/07/28-1.jpg');


insert into booking_dates(id, check_in, check_out, price, house_id)
values (1, '2023-09-13 18:20:03', '2023-09-23 18:20:03', 20, 1);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (2, '2023-09-30 18:20:03', '2023-10-5 18:20:03', 15, 2);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (3, '2023-09-3 18:20:03', '2023-09-7 18:20:03', 29, 3);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (4, '2023-08-2 18:20:03', '2023-09-3 18:20:03', 10, 4);

insert into booking_dates(id, check_in, check_out, price, house_id)
values (5, '2023-07-7 18:20:03', '2023-07-15 18:20:03', 40, 5);

insert into feedbacks(id, created_feedback, dislike, likes, rating, text, house_id, user_id)
values (1, now(), 0, 5, 4, 'this was one of the nicest places we stayed in the four weeks w...', 1, 2);

insert into feedbacks(id, created_feedback, dislike, likes, rating, text, house_id, user_id)
values (2, now(), 0, 10, 5, 'ayed in the four weeks w...', 2, 2);

insert into feedbacks(id, created_feedback, dislike, likes, rating, text, house_id, user_id)
values (3, now(), 0, 3, 3, 'Good', 3, 1);

insert into feedbacks(id, created_feedback, dislike, likes, rating, text, house_id, user_id)
values (4, now(), 0, 5, 4, 'nicest places we stayed in the four weeks w...', 4, 2);


insert into feedbacks(id, created_feedback, dislike, likes, rating, text, house_id, user_id)
values (5, now(), 2, 5, 4, 'Grlaces we stayed in the four weeks w...', 5, 2);



