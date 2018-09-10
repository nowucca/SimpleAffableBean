--
-- BSD 3-Clause License
--
-- Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
-- All rights reserved.
--
-- Redistribution and use in source and binary forms, with or without
-- modification, are permitted provided that the following conditions are met:
--
-- * Redistributions of source code must retain the above copyright notice, this
--   list of conditions and the following disclaimer.
--
-- * Redistributions in binary form must reproduce the above copyright notice,
--   this list of conditions and the following disclaimer in the documentation
--   and/or other materials provided with the distribution.
--
-- * Neither the name of the copyright holder nor the names of its
--   contributors may be used to endorse or promote products derived from
--   this software without specific prior written permission.
--
-- THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
-- AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
-- IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
-- DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
-- FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
-- DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
-- SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
-- CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
-- OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
-- OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
--

--
-- Database: `simpleaffablebean`
--

-- --------------------------------------------------------

--
-- Sample data for table `category`
--
INSERT INTO `category` (category_id, name)
VALUES (1, 'sample-category-1');

INSERT INTO `category` (category_id, name)
VALUES (2, 'sample-category-2');

INSERT INTO `category` (category_id, name)
VALUES (3, 'sample-category-3');

INSERT INTO `category` (category_id, name)
VALUES (4, 'sample-category-4');


--
-- Sample data for table `product`
--
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (1, 'product-1-1', 110, 'product-1-1', 1);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (2, 'product-1-2', 120, 'product-1-2', 1);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (3, 'product-1-3', 130, 'product-1-3', 1);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (4, 'product-1-4', 140, 'product-1-4', 1);

INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (5, 'product-20-10', 210, 'product-20-10', 2);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (6, 'product-2-2', 220, 'product-2-2', 2);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (7, 'product-2-3', 230, 'product-2-3', 2);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (8, 'product-2-4', 240, 'product-2-4', 2);


INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (9, 'product-3-1', 310, 'product-3-1', 3);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (10, 'product-3-2', 320, 'product-3-3', 3);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (11, 'product-3-3', 330, 'product-3-3', 3);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (12, 'product-3-4', 340, 'product-3-4', 3);


INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (13, 'product-4-1', 410, 'product-4-1', 4);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (14, 'product-4-2', 420, 'product-4-2', 4);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (15, 'product-4-3', 430, 'product-4-3', 4);
INSERT INTO `product` (product_id, `name`, price, description, category_id)
VALUES (16, 'product-4-4', 440, 'product-4-4', 4);

