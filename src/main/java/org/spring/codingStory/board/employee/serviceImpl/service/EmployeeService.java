package org.spring.codingStory.board.employee.serviceImpl.service;

import org.spring.codingStory.board.employee.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface EmployeeService {

    void empInsertFile(EmployeeDto employeeDto) throws IOException;
    void updateEmpHit(Long id);

    Page<EmployeeDto> empList(Pageable pageable, String subject1, String subject2, String search);

    EmployeeDto detail(Long Id);

    void empUpdateOk(EmployeeDto employeeDto);

    void empDelete(Long id);

    List<EmployeeDto> empHit();

}
