package com.example.JPATransaction.repo;

import com.example.JPATransaction.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmployeeRepo extends JpaRepository<Employee, Long> {
    Employee findByEmail(String email);
}
