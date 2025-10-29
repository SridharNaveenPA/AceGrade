package com.acegrade.repository;

import com.acegrade.entity.CgpaSemester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CgpaSemesterRepository extends JpaRepository<CgpaSemester, Long> {
    List<CgpaSemester> findByUserIdOrderBySemesterNumberAsc(Long userId);
    void deleteByUserId(Long userId);
}


