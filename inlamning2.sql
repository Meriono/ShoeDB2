drop database if exists inlamning2;
create database inlamning2;
use inlamning2;

create table cities
(id int not null auto_increment primary key,
city varchar(30) not null);

insert into cities(city) values
('Solna'),('Malung'),('Nacka'),('Danderyd');


create table customers
(id int not null auto_increment primary key,
firstname varchar(30) not null,
lastname varchar(30) not null,
city_id int not null,
password varchar(30) not null,
foreign key(city_id) references cities(id));

insert into customers(firstname, lastname, city_id, password) values
('Hanna', 'Edlund', 1, 'password1'),
('Pelle', 'Persson', 2, 'password2'),
('Patrich', 'Nordstand', 1, 'password3'),
('Karl', 'Karlsson', 3, 'password4'),
('Stina', 'Stefansson', 4, 'password5'),
('Filip', 'Edlund', 4, 'password6');

create table categories
(id int not null auto_increment primary key,
category varchar(30) not null);

insert into categories(category) values
('Athletic'),('Sandaler'),('Sneakers'),('Kids'),('Women');


create table sizes
(id int not null auto_increment primary key,
size int not null);

insert into sizes(size) values
(38),(22),(40),(36),(25),(3);


create table colors
(id int not null auto_increment primary key,
color varchar(30) not null);

insert into colors(color) values
('Black'),('Pink'),('Grey'),('Red'),('Blue');


create table brands
(id int not null auto_increment primary key,
brand varchar(30));

insert into brands(brand) values
('Ecco'),('Nike'),('Puma'),('Nly'),('Babies');


create table products
(id int not null auto_increment primary key,
name varchar(30) not null,
size_id int not null,
color_id int not null,
brand_id int not null,
price int not null,
saldo int not null,
foreign key(size_id) references sizes(id),
foreign key(color_id) references colors(id),
foreign key(brand_id) references brands(id) on update cascade);

insert into products(name, size_id, color_id, brand_id, price, saldo) values
('Namn1', 1, 1, 1, 599, 10),
('Namn2', 2, 2, 2, 299, 8),
('Namn3', 3, 3, 3, 1999, 6),
('Namn4', 3, 4, 3, 1500, 4),
('Namn5', 4, 2, 4, 599, 2),
('Namn6', 5, 5, 1, 399, 1),
('Namn7', 6, 3, 5, 100, 3),
('Namn8', 1, 2, 3, 699, 5),
('Namn9',1,2,2,599,7);


create table orders
(id int not null auto_increment primary key,
datum date not null,
customer_id int not null);

insert into orders(datum, customer_id) values
('2020-12-30', 2),
('2020-11-30', 2),
('2020-11-15', 1),
('2020-10-17', 3),
('2020-12-25', 5),
('2020-12-30', 4);


create table order_details
(id int not null auto_increment primary key,
order_id int not null,
product_id int not null,
amount int not null);

insert into order_details(order_id, product_id, amount) values
(1,7,3),
(2,7,1),
(3,5,1),
(3,3,2),
(4,1,1),
(5,1,1),
(4,5,2),
(6,8,1),
(6,2,2);


create table category_product
(id int not null auto_increment primary key,
product_id int,
category_id int,
foreign key(product_id) references products(id),
foreign key(category_id) references categories(id));

insert into category_product(product_id, category_id) values
(1,2),
(2,3),
(2,4),
(3,3),
(3,1),
(4,3),
(5,5),
(6,4),
(7,4),
(8,5),
(8,2);


create table gradings
(id int not null auto_increment primary key,
iText varchar(30),
iSiffror int not null);

insert into gradings (iText, iSiffror) values
('Mycket nöjd',5), ('Nöjd',4), ('Ganska nöjd',3), ('Missnöjd',2), ('Hatar',1);


create table reviews
(id int not null auto_increment primary key,
customer_id int not null,
product_id int not null,
grade_id int not null,
comment varchar(50) default 'No comment',
foreign key(customer_id) references customers(id) on delete cascade,
foreign key(product_id) references products(id) on delete cascade,
foreign key(grade_id) references gradings(id) on delete cascade);

insert into reviews (customer_id, product_id, grade_id) values
(2,1,1);


create or replace view review_details as
select customers.firstname as Namn, customers.lastname as Efternamn, 
products.name as Produkt, brands.brand as Brand, 
gradings.iText as Betyg, gradings.iSiffror as Siffra, reviews.comment as Kommentar 
from reviews
inner join products on products.id = reviews.product_id
inner join brands on brands.id = products.brand_id
inner join customers on customers.id = reviews.customer_id
inner join gradings on gradings.id = reviews.grade_id;

select * from review_details;

-- delete from gradings where id = 1;


create index ix_categoryname on categories(category);


-- HÄR BÖRJAR INLÄMNING 2

create table slutiLager
(id int not null auto_increment primary key,
datum date not null,
product_id int not null,
foreign key(product_id) references products(id));

Delimiter //
create trigger after_products_update
after update
on products
for each row
BEGIN
	if new.saldo = 0 then
		insert into slutiLager(datum, product_id) values (current_date(), NEW.id);
        end if;
END//
Delimiter ;

delimiter //
create procedure AddToCart
	(in _customerID int,
    in _orderID int,
    in _productID int)
begin
	declare orderID int default 0;
    declare orderDetailsID int default 0;
    
    declare saldoCount int default 0;
    declare orderCount int default 0;
    declare productCount int default 0;
    
    
	DECLARE EXIT HANDLER FOR SQLEXCEPTION 
    BEGIN
          ROLLBACK;
          select ('SQLEXCEPTION occurred, rollback done')as error;
    END;
    
    DECLARE EXIT HANDLER FOR SQLWARNING 
    BEGIN
          ROLLBACK;
          select ('SQLWARNING occurred, rollback done') as error;
    END;
    
START transaction;

    select count(*) into orderCount from orders where _orderID like id;

        if(orderCount = 0) then 								-- order finns inte i db
			insert into orders(datum, customer_id) values
			(current_date(), _customerID);
            set orderID = last_insert_id();
		else													-- order finns i db
			set orderID = _orderID;
		end if;

    select count(*) into productCount from order_details where _productID like product_id AND order_id like orderID;

		if (productCount = 0) then								-- om produkt inte finns i en order	
            insert into order_details(order_id, product_id, amount)
            values (orderID, _productID, 1);     
		
        else													-- om produkt finns i order
            select id into orderDetailsID from order_details where order_id = orderID AND product_id = _productID;
            Update order_details set amount = amount+1 WHERE id = orderDetailsID;
			
		end if;
		
        select saldo into saldoCount from products where id = _productID;

		if(saldoCount != 0) then
        update products set saldo = saldo-1 where id = _productID;
        else
        ROLLBACK;
		select ('Skon är slut!') as error;
        end if;

	commit;
end//
delimiter ;


delimiter //
create procedure Rate
	(in gradeID int,
    in aComment varchar(50),
    in customerID int,
    in productID int)
begin
	insert into reviews (customer_id, product_id, grade_id, comment) values
	(customerID , productID ,gradeID, aComment);
end//
delimiter ;


delimiter //
create function getMedelBetyget (productID int)
returns int
READS SQL DATA
DETERMINISTIC
begin
	declare medelbetyg int;
    set medelbetyg = 0;
    
    select avg(iSiffror) into medelbetyg from reviews
    inner join products on products.id = reviews.product_id
    inner join gradings on gradings.id = reviews.grade_id
    where product_id = productID;
    
	return medelbetyg;
end//
delimiter ;


create or replace view review_details_OnAllProducts as
select products.name as Namn, brands.brand as Brand,
(select getMedelBetyget(products.id)) as Medelbetyg,
(select gradings.iText where gradings.iSiffror = Medelbetyg) as Betyg
from products 
inner join brands on brands.id = products.brand_id
left join reviews on reviews.product_id = products.id
left join gradings on gradings.id = reviews.grade_id
group by products.id
order by products.id
;



-- inför redovisning

-- Lagerstatus tillagd
select * from products;

-- AddToCart
-- Om ordern inte finns + saldo minskar
-- call AddToCart(6,0,6); -- C, O, P
-- Om ordern finns + antal ökar + saldo minskar
-- call AddToCart(6,7,6)

-- SlutiLager
Select * from slutiLager;

-- Betyg och kommentarer
select * from review_details;
-- call Rate (1,'Toppen', 2,1);
-- call Rate (1,'Grymt', 2,1);
select getMedelBetyget(1);
select * from review_details_OnAllProducts;