/**
 * BSD 3-Clause License
 *
 * Copyright (C) 2017 Steven Atkinson <support@simpleaffablebean.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * * Neither the name of the copyright holder nor the names of its
 *   contributors may be used to endorse or promote products derived from
 *   this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package business.product;

import business.GuavaUtils;
import business.SimpleAffableDbException;
import com.google.common.cache.LoadingCache;
import java.util.List;

/**
 */
public class ProductDaoGuava implements ProductDao {

    private ProductDao origin;

    private LoadingCache<Long, Product> productIdToProductCache;
    private LoadingCache<Long, List<Product>> categoryIdToProductsCache;

    @SuppressWarnings("ConstantConditions")
    public ProductDaoGuava(ProductDao origin) {
        this.origin = origin;
        productIdToProductCache = GuavaUtils.makeCache(origin::findByProductId);
        categoryIdToProductsCache = GuavaUtils.makeCache(origin::findByCategoryId);
    }

    public void clear() {
        // Clear the caches periodically
        productIdToProductCache.invalidateAll();
        categoryIdToProductsCache.invalidateAll();
    }


    @Override
    public Product findByProductId(long productId) {
        try {
            return productIdToProductCache.get(productId);
        } catch (Exception e) {
            throw new SimpleAffableDbException("Encountered problem loading product id "+productId+" into cache", e);
        }
    }

    @Override
    public List<Product> findByCategoryId(long categoryId) {
        try {
            return categoryIdToProductsCache.get(categoryId);
        } catch (Exception e) {
            throw new SimpleAffableDbException("Encountered problem loading products by category id "+categoryId+" into cache", e);
        }
    }

}
