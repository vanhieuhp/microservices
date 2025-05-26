package hieunv.dev.openfeign.product.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hieunv.dev.openfeign.product.entity.Product;
import hieunv.dev.openfeign.product.entity.QCategory;
import hieunv.dev.openfeign.product.entity.QProduct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> getProducts(Double minPrice, Double maxPrice) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProduct product = QProduct.product;

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (minPrice != null) {
            booleanBuilder.and(product.price.goe(minPrice));
        }

        if (maxPrice != null) {
            booleanBuilder.and(product.price.loe(maxPrice));
        }

        return queryFactory.selectFrom(product)
                .where(booleanBuilder)
                .fetch();
    }

    public List<Product> getProductsWithCategoryName(String categoryName) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProduct product = QProduct.product;
        QCategory category = QCategory.category;

        return queryFactory.selectFrom(product)
                .join(product.category, category)
                .where(category.name.eq(categoryName))
                .fetch();
    }

    public List<Product> getProductsAboveAveragePrice() {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProduct product = QProduct.product;

        JPAQuery<Double> avgPrice = queryFactory.select(product.price.avg())
                .from(product);

        return queryFactory.selectFrom(product)
                .where(product.price.gt(avgPrice))
                .fetch();
    }

    public Page<Product> getPaginatedProducts(String categoryName, int page, int size) {
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        QProduct product = QProduct.product;

        Pageable pageable = PageRequest.of(page, size);

        List<Product> products = queryFactory.selectFrom(product)
                .where(product.category.name.eq(categoryName))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(product)
                .where(product.category.name.eq(categoryName))
                .fetch()
                .size();

        return new PageImpl<>(products, pageable, total);
    }
}
