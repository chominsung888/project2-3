package org.spring.codingStory.department.serviceimpl;

import org.spring.codingStory.department.dto.DepartmentDto;
import org.spring.codingStory.department.entity.DepartmentEntity;
import org.spring.codingStory.member.dto.MemberDto;

import java.util.List;

public interface DepartmentServiceInterface {
  DepartmentEntity addDepartment(DepartmentDto departmentDto);

  List<DepartmentEntity> getAllDepartments();

  DepartmentDto getDepartmentByIdWithMembers(Long deptId);




  List<DepartmentEntity> getParentDepartments();




  List<DepartmentDto> findDepart();



  List<MemberDto> getMembersByDepartmentId(String dept);

  void updateDepartmentMemberCount(String department);



}
