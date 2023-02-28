insert into users(id, name, email, password, image, count_of_booked_house)
values (1, 'Airbnb', 'airbnbhomesweethome1@gmail.com', '$2a$12$HJmsalvo/0.5G2oh1QapuugtYCMHHpFgf27H/02PwYTtKU4FXTfre', 'https://img04.rl0.ru/afisha/720x-/daily.afisha.ru/uploads/images/a/22/a22b5e2976d44c18a2219b79ddd6ce0c.jpg',
        0);

insert into users(id, name, email, password, image, count_of_booked_house)
values (2, 'Nuraiym', 'mnurajym9@gmail.com', '$2a$12$QxFJ6yYms8SqIVlOR5R3G.oLzOuC3mVALF6GWzoFbCa0W37usfPkS', 'https://www.google.com/imgres?imgurl=https%3A%2F%2Fw0.peakpx.com%2Fwallpaper%2F979%2[…]%20images&ved=2ahUKEwiA-YzOibj9AhUHsSoKHU6NA9sQMygregUIARCmAg', 0);

insert into users(id, name, email, password, image, count_of_booked_house)
values (3, 'Eldiar', 'eldiarro2004@gmail.com', '$2a$12$15g.2RvWMyfahTy6aqiXouDFiVvtgQ/bYQaYLYMS3ALdnUUlIUsAu', 'https://i.ytimg.com/vi/Y76mH_qDh5w/sddefault.jpg', 0);

insert into users(id, name, email, password, image, count_of_booked_house)
values (4, 'Jumgalbek', 'adyl6ekov02@gmail.com', '$2a$12$5xALSbeVvAQrlOqnEdeiHegbSZS0/Cebx7ti2WOEknukZVtY.ElXm', 'https://ca.slack-edge.com/T023L1WBFLH-U03LQUL5G7Q-cdd711689d70-72', 0);

insert into users(id, name, email, password, image, count_of_booked_house)
values (5, 'Samarbek', 'samarbekawymuulu@gmail.com', '$2a$12$RQ/ZmZH0nvKiZTs2..IrauVBJwyFZ95DoY74KZ3K1MlRvwYMlok8S', 'https://ca.slack-edge.com/T023L1WBFLH-U03N4BQCAH3-180d127f6c09-512', 0);

insert into users(id, name, email, password, image, count_of_booked_house)
values (6, 'Munarbek', 'munarbek.shorokhov@gmail.com', '$2a$12$z8Maie.N3iiTyj2PRicFxOBYUXM/7cxmnk62BRgNjwluKFj1osllC', 'https://ca.slack-edge.com/T023L1WBFLH-U03M13M6B7T-8c1eac68fad3-512', 0);


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

insert into roles_users(role_id, user_id)
VALUES (2, 4);

insert into roles_users(role_id, user_id)
VALUES (2, 5);

insert into roles_users(role_id, user_id)
VALUES (2, 6);


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

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (8, 20, 'Beka', 'Very good house', 2, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 3);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (9, 67, 'Kut', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 3);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (10, 20, 'Ihlas', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 3);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (11, 20, 'Delmar', 'Very good house', 5, 0, 0, 2,false, 0, '2023-09-13 18:20:03', 4);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (12, 20, 'Ololo', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 4);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (13, 20, 'Drake', 'Very good house', 1, 0, 0, 2,false, 0, '2023-09-13 18:20:03', 5);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (14, 20, 'Travis', 'Very good house', 3, 0, 0, 2,false, 0, '2023-09-13 18:20:03', 6);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (15, 20, 'Kotlin', 'Very good house', 5, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 3);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (16, 20, 'Java', 'Very good house', 2, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 2);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (17, 20, 'Ololo', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 4);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (18, 20, 'Ololo', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 5);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (19, 20, 'Ololo', 'Very good house', 3, 0, 1, 2,false, 0, '2023-09-13 18:20:03', 6);

insert into houses(id, price, title, description_of_listing, max_of_guests, bookings,
                   house_type, houses_status, is_favorite, houses_booked, date_house_created, owner_id)
values (20, 20, 'Flutter', 'Very good house', 3, 0, 0, 2,false, 0, '2023-09-13 18:20:03', 5);

insert into locations(id, address, region, town_or_province, house_id)
values (1,'84B, Masalieva ave.邮政编码: 723500','Osh','Ozgon',1);

insert into locations(id, address, region, town_or_province, house_id)
values (2,'5FPR+6G, Кызыл-Коргон','Osh','Ош',2);

insert into locations(id, address, region, town_or_province, house_id)
values (3,'16 ул. Чынгыза Айтматова','Talas','Talas',3);

insert into locations(id, address, region, town_or_province, house_id)
values (4,'72 Ленина, Tалас','Talas','Talas',4);

insert into locations(id, address, region, town_or_province, house_id)
values (5,'Подгорная ул.83 Ул. Аманбека Сыдыгалиев','Talas','Talas',5);

insert into locations(id, address, region, town_or_province, house_id)
values (6,'FWMM+JMG, Бакай Ата, р','Talas','Bakai-Ata',6);

insert into locations(id, address, region, town_or_province, house_id)
values (7,'улица Усенбаева д.1','Talas','Talas',7);


insert into locations(id, address, region, town_or_province, house_id)
values (8,'92а просп. Манаса','Jalal-Abad','Jalal-Abad',8);

insert into locations(id, address, region, town_or_province, house_id)
values (9,'ул Ленина 159А','Jalal-Abad','Jalal-Abad',9);

insert into locations(id, address, region, town_or_province, house_id)
values (10,'village Kumush Aziz, 2','Jalal-Abad','Jalal-Abad',10);

insert into locations(id, address, region, town_or_province, house_id)
values (11,'ул Нурбек Асанов','Jalal-Abad','Jalal-Abad',11);

insert into locations(id, address, region, town_or_province, house_id)
values (12,'8WQ7+R7Q, Арстанбап','Jalal-Abad','Арстанбап',12);


insert into locations(id, address, region, town_or_province, house_id)
values (13,'143, Alieva str.','Osh','Ozgon',13);

insert into locations(id, address, region, town_or_province, house_id)
values (14,'1 ул. Баяпинова','Osh','Ош',14);

insert into locations(id, address, region, town_or_province, house_id)
values (15,'8C8X+6W8, Боконбаев көчөсү','Osh','Гүлчө',15);


insert into locations(id, address, region, town_or_province, house_id)
values (16,'2RX9+F49, ул.Жусупова','Batken','Баткен',16);

insert into locations(id, address, region, town_or_province, house_id)
values (17,'Самат Сыдыков 8','Batken','Баткен',17);

insert into locations(id, address, region, town_or_province, house_id)
values (18,'2RVH+2C8, Unnamed Road','Batken','Баткен',18);

insert into locations(id, address, region, town_or_province, house_id)
values (19,'ул. Файзуллаева 76','Batken','Баткен',19);

insert into locations(id, address, region, town_or_province, house_id)
values (20,'10 Сабыров','Batken','Баткен',20);


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
insert into house_images(house_id, images)
values (8, 'https://dnm.nflximg.net/api/v6/BvVbc2Wxr2w6QuoANoSpJKEIWjQ/AAAAQZUkwT6XhdDnNqAsPrZiQWWHvhpJo0cviRndWweNeFE0G6sOOa7ltzrwXSocCIsqRqAcruHZtEk-MBx_qLAJz-43yAbJAJXmEYKEMD78GRjJ3ro5x5T97jaAj0NwMiaHvO4mNGLRmwNAPE2yA0LWWV1UfQI.jpg?r=48b');
insert into house_images(house_id, images)
values (9, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTv3_7Rl55HpsR4PEH1mg48290SKY2zoMNHog&usqp=CAU');
insert into house_images(house_id, images)
values (10, 'https://img.onmanorama.com/content/dam/mm/en/lifestyle/decor/images/2022/1/27/4-cent-trivandrum-home-view.jpg');
insert into house_images(house_id, images)
values (11, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRV0X51lPhIckkQn2wrR8YefWf_DHQjbyDr-odPai-P-HDbcvs9xaoZtl7t65YKe5zPygc&usqp=CAU');
insert into house_images(house_id, images)
values (12, 'https://images.adsttc.com/media/images/5e9f/b08e/b357/652a/3c00/10a2/large_jpg/00_FI.jpg?1587523717');
insert into house_images(house_id, images)
values (13, 'https://images.familyhomeplans.com/cdn-cgi/image/fit=scale-down,quality=85/plans/51981/51981-b580.jpg');
insert into house_images(house_id, images)
values (14, 'https://img.etimg.com/photo/msid-89741747/image-2022-02-22t112212-669.jpg');
insert into house_images(house_id, images)
values (15, 'http://cdn.home-designing.com/wp-content/uploads/2020/04/unique-house-design.jpg');
insert into house_images(house_id, images)
values (16, 'https://i.pinimg.com/236x/22/17/25/221725a645b14d15ac66ea9c3f9172ec.jpg');
insert into house_images(house_id, images)
values (17, 'https://amazingarchitecture.com/storage/2577/casa_de_abdullah_jamaliah_arkitects_tenkasi_india.jpg');
insert into house_images(house_id, images)
values (18,'https://images.saymedia-content.com/.image/t_share/MTc0Mjg1NzMxNTc1OTY1MTgw/unusual-homes-and-unique-real-estate.jpg');
insert into house_images(house_id, images)
values (20, 'https://media.istockphoto.com/id/174697847/photo/modern-architecture.jpg?s=612x612&w=0&k=20&c=BkWS-KGB1qWdBToM24BgFE6w_1wIJLxv0cBwqlNIAQI=');


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

insert into booking_dates_users(bookings_id,users_id)
values (1,3);

insert into booking_dates_users(bookings_id,users_id)
values (2,3);

insert into booking_dates_users(bookings_id,users_id)
values (3,4);

insert into booking_dates_users(bookings_id,users_id)
values (4,5);

insert into booking_dates_users(bookings_id,users_id)
values (5,6);

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



