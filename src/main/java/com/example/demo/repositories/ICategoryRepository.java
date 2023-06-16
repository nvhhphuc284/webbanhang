package com.example.demo.repositories;
import com.example.demo.entity.Category;
import com.example.demo.entity.Xe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryRepository extends
        JpaRepository<Category, Long> {
    default List<Category> findAllCategory(Integer pageNo,
                                Integer pageSize,
                                String sortBy)
    {
        return findAll(PageRequest.of(pageNo,
                pageSize,
                Sort.by(sortBy)))
                .getContent();
    }
}
