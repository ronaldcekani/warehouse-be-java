package com.example.warehousemanagement.core.graphql.persistence;

import com.example.warehousemanagement.core.enums.SortDirectionEnum;
import com.example.warehousemanagement.core.graphql.ColExp;
import com.example.warehousemanagement.core.graphql.SortExp;
import com.example.warehousemanagement.core.graphql.Where;
import com.example.warehousemanagement.core.graphql.domain.GraphqlQueryRequest;
import com.example.warehousemanagement.core.http.PageableResponse;
import com.example.warehousemanagement.product.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GraphqlGenericRepoImpl<E> implements GraphqlGenericRepo<E> {
    @PersistenceContext
    private EntityManager entityManager;

    private TypedQuery<Long> getCountBuilder(CriteriaQuery<E> criteriaQuery) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = criteriaBuilder.createQuery(Long.class);
        final Root<E> countRoot = cq.from(criteriaQuery.getResultType());

        cq.select(criteriaBuilder.count(countRoot));
        cq.where(criteriaQuery.getRestriction());

        return entityManager.createQuery(cq.distinct(criteriaQuery.isDistinct()));
    }

    @Override
    public PageableResponse<E, E> filter(GraphqlQueryRequest filters, Class<E> cls) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<E> query = builder.createQuery(cls);
        Predicate predicate = builder.conjunction();
        Root<E> root = query.from(cls);

        for (ColExp colExp : filters.getWhere()) {
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

        // handle sort
        List<Order> orderList = new ArrayList<>();
        if (filters.getSort() != null && !filters.getSort().isEmpty()) {
            for (SortExp sortExp : filters.getSort()) {
                if (sortExp.getDirection() == SortDirectionEnum._asc) {
                    orderList.add(builder.asc(root.get(sortExp.getField())));
                } else {
                    orderList.add(builder.desc(root.get(sortExp.getField())));
                }
            }
        }

        int pageNumber = 0;

        if (filters.getPage() != null && filters.getPage().isPresent()) {
            pageNumber = filters.getPage().get() <= 0 ? 0 : (filters.getPage().get() - 1);
        }

        int limit = 10;

        if (filters.getLimit() != null && filters.getLimit().isPresent()) {
            limit = filters.getLimit().get();
        }

        int firstResult = pageNumber * Math.abs(limit);

        query.select(root).where(predicate);

        if (!orderList.isEmpty()) {
            query.orderBy(orderList);
        }

        TypedQuery<E> typedQuery = entityManager.createQuery(query);

        typedQuery.setFirstResult(firstResult);

        if (limit >= 0) {
            typedQuery.setMaxResults(limit);
        }

        Long total = getCountBuilder(query).getSingleResult();
        Pageable pageable = PageRequest.of((pageNumber + 1), limit);
        List<E> result = typedQuery.getResultList();
        Page<E> page = new PageImpl<E>(new ArrayList<>(), pageable, total);
        return new PageableResponse<E, E>(result, page);
    }

    @Override
    public E find(Long id, Class<E> cls) throws EntityNotFoundException {
        E result = entityManager.find(cls, id);

        if (result == null) {
            throw new EntityNotFoundException("Can't find entity with ID" + id);
        }

        return result;
    }


}
