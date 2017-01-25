-- Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
--
-- You may not modify, use, reproduce, or distribute this software
-- except in compliance with the terms of the license at:
-- http://developer.sun.com/berkeley_license.html
--
-- author: tgiunipero
--

--
-- Database: `affablebean`
--

-- --------------------------------------------------------

--
-- Sample data for table `category`
--
INSERT INTO `category` (name) VALUES ('dairy'),('meats'),('bakery'),('fruit & veg');


--
-- Sample data for table `product`
--
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('milk', 1.70, 'semi skimmed (1L)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('cheese', 2.39, 'mild cheddar (330g)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('butter', 1.09, 'unsalted (250g)', 1);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('free range eggs', 1.76, 'medium-sized (6 eggs)', 1);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('organic meat patties', 2.29, 'rolled in fresh herbs<br>2 patties (250g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('parma ham', 3.49, 'matured, organic (70g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('chicken leg', 2.59, 'free range (250g)', 2);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('sausages', 3.55, 'reduced fat, pork<br>3 sausages (350g)', 2);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('sunflower seed loaf', 1.89, '600g', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('sesame seed bagel', 1.19, '4 bagels', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('pumpkin seed bun', 1.15, '4 buns', 3);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('chocolate cookies', 2.39, 'contain peanuts<br>(3 cookies)', 3);

INSERT INTO `product` (`name`, price, description, category_id) VALUES ('corn on the cob', 1.59, '2 pieces', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('red currants', 2.49, '150g', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('broccoli', 1.29, '500g', 4);
INSERT INTO `product` (`name`, price, description, category_id) VALUES ('seedless watermelon', 1.49, '250g', 4);

-- (without description)
--INSERT INTO `product` (`name`, price, category_id) VALUES ('milk', 1.70, 1);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('cheese', 2.39, 1);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('butter', 1.09, 1);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('free range eggs', 1.76, 1);
--
--INSERT INTO `product` (`name`, price, category_id) VALUES ('organic meat patties', 2.29, 2);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('parma ham', 3.49, 2);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('chicken leg', 2.59, 2);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('sausages', 3.55, 2);
--
--INSERT INTO `product` (`name`, price, category_id) VALUES ('sunflower seed loaf', 1.89, 3);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('sesame seed bagel', 1.19, 3);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('pumpkin seed bunbun', 1.15, 3);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('chocolate cookies', 2.39, 3);
--
--INSERT INTO `product` (`name`, price, category_id) VALUES ('corn on the cob', 1.59, 4);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('red currants', 2.49, 4);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('broccoli', 1.29, 4);
--INSERT INTO `product` (`name`, price, category_id) VALUES ('seedless watermelon', 1.49, 4);


--
-- Sample data for table `customer`
--
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Charlie Pace', 'c.pace@youareeverybody.com', '605434778', 'Široká 45', '1', '4224311324421331');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('MC Hammer', 'hammer@hammertime.com', '226884562', 'Ruská 11', '2', '4321123443211234');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Karel Gott', 'gott@karelgott.com', '224517995', 'Kostelní 83', '7', '3311332222444411');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Helena Vondráčková', 'h.vondrackova@seznam.cz', '224517995', 'Letohradská 18', '7', '1111222244443333');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Sawyer Ford', 'sawyer.ford@gmail.com', '204888845', 'Dušní 87', '1', '2222333311114444');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Dalibor Janda', 'dalibor@dalibor.cz', '728331184', 'Krkonošská 9', '3', '3111444222212334');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Richard Genzer', 'r.genzer@nova.cz', '737610775', 'Plzeňská 131', '5', '2244443321123311');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Iveta Bartošová', 'i.bartosova@volny.cz', '734556133', 'Prokopská 60', '1', '3333111144442222');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Jin-Soo Kwon', 'jin.kwon@hotmail.kr', '606338909', 'Ve Střešovičkách 49', '6', '1111222233334444');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Benjamin Linus', 'b.linus@lost.com', '222756448', 'Družstevní 77', '4', '4444222233331111');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Leoš Mareš', 'mares@ferrari.it', '608995383', 'Pařížská 89', '1', '2222444411113333');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('John Locke', 'maninblack@lostpedia.com', '413443727', 'Valečovská 20', '9', '2244331133114422');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Lucie Bílá', 'lucie@jampadampa.cz', '733556813', 'Na hájku 3', '8', '3333444422221111');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Sayid Jarrah', 'sayid@gmail.com', '602680793', 'Kodaňská 78', '10', '5490123456789128');
--INSERT INTO `customer` (`name`, email, phone, address, city_region, cc_number) VALUES ('Hugo Reyes', 'hurley@mrcluck.com', '605449336', 'Žerotínova 64', '3', '4539992043491562');


--
-- Sample data for table `customer_order`
--
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (16.50, '2010-05-14 18:00:11.0', 15, 285434339);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (16.11, '2010-05-14 17:56:23.0', 14, 428278565);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (26.00, '2010-05-14 17:51:37.0', 13, 503113888);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (17.63, '2010-05-14 17:47:46.0', 12, 916407556);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (17.24, '2010-05-14 17:45:21.0', 11, 189191209);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (15.57, '2010-05-14 17:43:12.0', 10, 274027361);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (4.49, '2010-05-14 18:04:09.0', 9, 250764732);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (19.70, '2010-05-14 18:10:09.0', 8, 766244032);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (37.49, '2010-05-14 18:23:08.0', 7, 53395157);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (18.90, '2010-05-14 18:25:56.0', 6, 818358116);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (18.92, '2010-05-14 18:32:03.0', 5, 244956320);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (17.66, '2010-05-14 18:35:07.0', 4, 868642371);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (10.22, '2010-05-14 18:40:38.0', 3, 344549009);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (12.16, '2010-05-14 18:51:58.0', 2, 475644436);
--INSERT INTO `customer_order` (amount, date_created, customer_id, confirmation_number) VALUES (10.75, '2010-05-14 18:56:13.0', 1, 247455344);


--
-- Sample data for table `ordered_product`
--
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (15, 1, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (15, 15, 3);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (15, 3, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (14, 5, 4);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (13, 13, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (13, 4, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (13, 10, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (13, 16, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (12, 1, 3);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (12, 12, 4);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (11, 13, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (11, 2, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (11, 9, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (11, 14, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (11, 16, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (11, 10, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (10, 13, 10);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (9, 8, 5);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (9, 7, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (9, 6, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (9, 5, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (8, 8, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (8, 15, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (8, 11, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (8, 9, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (8, 14, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (8, 16, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (7, 16, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (6, 15, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (6, 9, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (6, 4, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (6, 6, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (6, 3, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (5, 15, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (5, 7, 5);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (4, 8, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (4, 1, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (4, 11, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (4, 14, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (4, 4, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (4, 16, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (3, 1, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (3, 8, 6);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 13, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 5, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 15, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 2, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 11, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 16, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (2, 4, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (1, 12, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (1, 2, 2);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (1, 13, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (1, 10, 1);
--INSERT INTO `ordered_product` (customer_order_id, product_id, quantity) VALUES (1, 8, 1);