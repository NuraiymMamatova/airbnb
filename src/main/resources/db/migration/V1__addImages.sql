alter table house_images add column image_id serial primary key;
alter table house_images rename column  images to image_url;

