package com.monds.demos;

import com.monds.demos.entity.TaxonomyCategory;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @Log
public class HelloController {

    @Autowired
    private TaxonomyRepository taxonomyRepository;

    @GetMapping("/api/taxonomy/category/{code}")
    public TaxonomyCategory findTaxonomyCategoryByCode(@PathVariable String code) {
        return taxonomyRepository.findOne(code);
    }

//    @GetMapping("/api/taxonomy/category")
//    public Page getTaxonomyCategories(Pageable p) {
//        return taxonomyRepository.findAll(p);
//    }

    @PostMapping("/api/taxonomy/category")
    public void addTaxonomyCategory(@RequestBody TaxonomyCategory taxonomyCategory) {
        System.out.println("************" + taxonomyCategory.getName() + "************");
    }



}
