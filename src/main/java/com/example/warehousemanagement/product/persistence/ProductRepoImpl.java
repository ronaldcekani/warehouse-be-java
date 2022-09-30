package com.example.warehousemanagement.product.persistence;

import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.core.graphql.Where;
import com.example.warehousemanagement.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class ProductRepoImpl implements ProductRepoCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> filter(List<ColExp> where) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);
        Predicate predicate = builder.conjunction();
        Root<Product> root = query.from(Product.class);

        for (ColExp colExp : where) {
            Predicate condition;
            switch (colExp.getCondition()) {
                case _gt -> {
                    condition = builder.greaterThan(root.get(colExp.getField()), colExp.getValue());
                }
                case _lt -> {
                    condition = builder.lessThan(root.get(colExp.getField()), colExp.getValue());
                }
                case _like -> {
                    condition = builder.like(
                            root.get(colExp.getField()),
                            "%" + colExp.getValue() + "%"
                    );
                }
                default -> {
                    condition = builder.equal(root.get(colExp.getField()), colExp.getValue());
                }
            }

            if (colExp.getWhere() == Where._or) {
                predicate = builder.or(predicate, condition);
            } else {
                predicate = builder.and(predicate, condition);
            }
        }

        query.select(root).where(predicate);
        return entityManager.createQuery(query).getResultList();
    }

}
