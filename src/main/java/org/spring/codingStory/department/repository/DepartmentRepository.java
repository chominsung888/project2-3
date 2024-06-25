package org.spring.codingStory.department.repository;

import org.spring.codingStory.department.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity,Long> {


  DepartmentEntity save(DepartmentEntity departmentEntity);

  DepartmentEntity findByDptName(String department);
}
