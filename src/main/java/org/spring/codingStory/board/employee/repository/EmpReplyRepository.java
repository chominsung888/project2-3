package org.spring.codingStory.board.employee.repository;

import org.spring.codingStory.board.employee.entity.EmployeeEntity;
import org.spring.codingStory.board.employee.entity.EmployeeReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpReplyRepository extends JpaRepository<EmployeeReplyEntity,Long> {
    List<EmployeeReplyEntity> findAllByEmployeeEntity(EmployeeEntity employeeEntity);

}
