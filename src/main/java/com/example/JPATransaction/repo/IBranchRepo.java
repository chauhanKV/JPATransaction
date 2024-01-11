package com.example.JPATransaction.repo;


import com.example.JPATransaction.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBranchRepo extends JpaRepository<Branch, Long> {
}
