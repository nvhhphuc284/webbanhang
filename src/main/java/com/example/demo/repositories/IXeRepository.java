package com.example.demo.repositories;
import com.example.demo.entity.Xe;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

@Repository
public interface IXeRepository extends
        PagingAndSortingRepository<Xe, Long>, JpaRepository<Xe, Long> {
    default List<Xe> findAllXes(Integer pageNo,
                              Integer pageSize,
                              String sortBy)
    {
        return findAll(PageRequest.of(pageNo,
                pageSize,
                Sort.by(sortBy)))
                .getContent();
    }
    @Query("""
    SELECT b FROM Xe b
    WHERE b.title LIKE %?1%
      OR b.author LIKE %?1%
    OR b.category.name LIKE %?1%
    """)
    List<Xe> searchXe(String keyword);

}