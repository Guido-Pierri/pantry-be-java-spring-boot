package com.guidopierri.pantrybe.services.search;

import com.guidopierri.pantrybe.models.DabasItem;
import com.guidopierri.pantrybe.models.SearchParams;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@Getter
@NoArgsConstructor
public class DabasItemSearchSpecification implements Specification<DabasItem> {
    public static final String NAME = "name";
    public static final String BRAND = "brand";
    public static final String CATEGORY = "category";
    private SearchParams params;

    public DabasItemSearchSpecification(SearchParams params) {
        this.params = params;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<DabasItem> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        if (params.getFreeTextSearch() == null || params.getFreeTextSearch().isEmpty()) {
            return criteriaBuilder.conjunction();
        }

        return constructNameQuery(root, criteriaBuilder);
    }

    private Predicate constructNameQuery(Root<DabasItem> root, CriteriaBuilder criteriaBuilder) {
        assert params.getFreeTextSearch() != null;
        if (params.getFreeTextSearch().isEmpty()) {
            return criteriaBuilder.conjunction();
        }
        String text = params.getFreeTextSearch().toLowerCase();
        String[] parts = text.split("[\\s?!;<>()\\-_\"'´`]+");

        Predicate[] wordPredicates = new Predicate[parts.length];
        int i = 0;
        for (String part : parts) {
            wordPredicates[i] = criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(NAME)), "%" + part + "%")//,
                    //criteriaBuilder.like(criteriaBuilder.lower(root.get(CATEGORY)), "%" + part + "%")
            );
            i++;
        }
        return criteriaBuilder.and(wordPredicates);

    }


}



