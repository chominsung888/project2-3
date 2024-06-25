package org.spring.codingStory.board.employee.serviceImpl.service;

import org.spring.codingStory.board.employee.dto.EmployeeReplyDto;

import java.util.List;

public interface EmployeeReplyService {
    void insertEmployeeReply(EmployeeReplyDto employeeReplyDto);


    List<EmployeeReplyDto> employeeReplyList(Long id);

    Long employeeReplyDeleteById(Long id);
}
