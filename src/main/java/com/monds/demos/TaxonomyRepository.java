package com.monds.demos;

import com.monds.demos.entity.TaxonomyCategory;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TaxonomyRepository extends PagingAndSortingRepository<TaxonomyCategory, String> {
}
