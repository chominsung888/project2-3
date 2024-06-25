package org.spring.codingStory.department.serviceimpl.service;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.department.dto.DepartmentDto;
import org.spring.codingStory.department.entity.DepartmentEntity;
import org.spring.codingStory.department.repository.DepartmentRepository;
import org.spring.codingStory.department.serviceimpl.DepartmentServiceInterface;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class DepartmentService implements DepartmentServiceInterface {

  private final DepartmentRepository departmentRepository;
  private final MemberRepository memberRepository;


  //새로운 부서 추가
  @Override
  public DepartmentEntity addDepartment(DepartmentDto departmentDto) {
    //DepartmentDto로부터 DepartmentEntity 생성
    DepartmentEntity departmentEntity=DepartmentEntity.builder()
        .dptName(departmentDto.getDptName())
        .location(departmentDto.getLocation())
        .build();

    //DepartmentRepository를 사용하여 부서를 저장하고 반환
    return departmentRepository.save(departmentEntity);
  }

  //모든 부서 조회
  @Override
  public List<DepartmentEntity> getAllDepartments() {
    return departmentRepository.findAll();
  }


  //특정 부서 ID를 사용하여 해당 부서에 속한 멤버 목록을 포함한 부서목록 조회
  @Override
  public DepartmentDto getDepartmentByIdWithMembers(Long deptId) {
   /* DepartmentEntity department = departmentRepository.findById(deptId)
        .orElseThrow(() -> new RuntimeException("Department not found"));*/
    //부서를 찾지 못하면 예외를 던지지 않고 null로 반환하도록 수정해라
    DepartmentEntity department=departmentRepository.findById(deptId).orElse(null);
    //부서를 찾지 못한 경우 null로 반환해라
    if(department==null){
      return null;
    }

    DepartmentDto dto = new DepartmentDto();
    dto.setId(department.getId());
    dto.setDptName(department.getDptName());
    dto.setLocation(department.getLocation());
    dto.setMemberCount(department.getMemberCount());

    // 멤버 리스트 설정
   List<MemberEntity> memberEntityList=department.getMemberEntityList();
   List<MemberDto> memberDtoList= memberEntityList.stream()
           .map(memberEntity -> {
             MemberDto memberDto =new MemberDto();
             memberDto.setId(memberEntity.getId());
             memberDto.setRole(memberEntity.getRole());
             memberDto.setName(memberEntity.getName());
             return memberDto;
           })
               .collect(Collectors.toList());

   dto.setMemberDtoList(memberDtoList);

   return dto;
  }



  //모든 부서조회
  @Override
  public List<DepartmentEntity> getParentDepartments() {
    List<DepartmentEntity> parentDepartments=new ArrayList<>();
    List<DepartmentEntity> allDepartments= departmentRepository.findAll();

    //각 부서를 순회하면서 상위 부서를 찾습니다.
    for(DepartmentEntity department: allDepartments){
      DepartmentEntity parentDepartment= department.getParentDepartment();

      //부모 부서가 null이 아니라면, 상위 부서로 간주합니다.
      if(parentDepartment !=null && !parentDepartments.contains(parentDepartment)){
        parentDepartments.add(parentDepartment);

      }
    }

    return parentDepartments;
  }




  //모든 부서를 조회하여 선택 목록에 표시할 수 있는 형태로 반환.
  @Override
  public List<DepartmentDto> findDepart() {

    List<DepartmentEntity> departmentEntities = departmentRepository.findAll();

    List<DepartmentDto> departmentDtoList = new ArrayList<>();

    for (DepartmentEntity department : departmentEntities) {
      DepartmentDto memberDto = DepartmentDto.toSelectDepart(department);
      departmentDtoList.add(memberDto);
    }
    return departmentDtoList;
  }



  //특정 부서에 속한 멤버 목록을 조회
  @Override
  public List<MemberDto> getMembersByDepartmentId(String dept) {
    List<MemberEntity> memberEntities=memberRepository.findByDepartment(dept);
    //MemberDto 리스트를 생성함
    List<MemberDto> memberDtoList= new ArrayList<>();
    //각 멤버에 대한 MemberDto를 생성하여 리스트에 추가합니다.
    for(MemberEntity memberEntity: memberEntities){
      MemberDto memberDto= MemberDto.toSelectMemberDto(memberEntity);
      memberDtoList.add(memberDto);
    }
      return memberDtoList;
  }

  //멤버수 업데이트
  @Override
  public void updateDepartmentMemberCount(String departmentName) {
  int memberCount =memberRepository.countMembersByDepartment(departmentName);
    System.out.println("Member count for department" + departmentName + ":"+memberCount);

    DepartmentEntity departmentEntity =departmentRepository.findByDptName(departmentName);

    if(departmentEntity !=null){
      departmentEntity.setMemberCount(memberCount);
      departmentRepository.save(departmentEntity);
      System.out.println("Update department:" + departmentEntity.getDptName()+",member count:" +departmentEntity.getMemberCount());
    }else{
      System.out.println("Department not found:" +departmentName);
    }
  }


}



