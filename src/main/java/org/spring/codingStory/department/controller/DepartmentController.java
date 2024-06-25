package org.spring.codingStory.department.controller;


import lombok.RequiredArgsConstructor;
import org.spring.codingStory.department.dto.DepartmentDto;
import org.spring.codingStory.department.entity.DepartmentEntity;
import org.spring.codingStory.department.serviceimpl.service.DepartmentService;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.serviceImpl.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Controller
@RequestMapping("/department")
public class DepartmentController {

  private final DepartmentService departmentService;
  private final MemberService memberService;


  // 새로운 매핑
  @GetMapping("/department")
  public String departmentPage() {
    return "department/department";
  }

 // 부서를 추가하는 컨트롤러
  @GetMapping("/add")
  public String showAddDepartmentForm(Model model){
    model.addAttribute("departmentDto",new DepartmentDto());
    List<DepartmentEntity> parentDepartments= departmentService.getParentDepartments();
    model.addAttribute("parentDepartments",parentDepartments);
    return "department/add_department_form";
  }

  @PostMapping("/add")
  public String addDepartment(@ModelAttribute("departmentDto") DepartmentDto departmentDto){
    DepartmentEntity departmentEntity=departmentService.addDepartment(departmentDto);
    return "redirect:/department/department";
  }







  //부서 추가시 부모 부서를 선택 할 수 있는 컨틀로러
  @GetMapping("/parents")
  @ResponseBody
  public ResponseEntity<List<DepartmentEntity>> getParentDepartments(){
    List<DepartmentEntity> parentDepartments= departmentService.getParentDepartments();
    return ResponseEntity.ok().body(parentDepartments);
  }




@GetMapping("/list")
@ResponseBody
public ResponseEntity<Map<String, Object>>departmentList() {
  Map<String, Object> deptList = new HashMap<>();

  List<DepartmentEntity> departments = departmentService.getAllDepartments();
  List<String> depts = new ArrayList<>();
  List<Integer> mapDept = new ArrayList<>();
  List<String> mRanks = new ArrayList<>(); //mRank 정보를 담을 리스트 추가

  //1.부서를 추출해서 조회
  for (DepartmentEntity departmentEntity : departments) {
    System.out.println(departmentEntity.getDptName() + ".. 부서");
    depts.add(departmentEntity.getDptName());

    //2. 부서별 명수
    List<MemberDto> members = departmentService.getMembersByDepartmentId(departmentEntity.getDptName());
    int memberCount = members.size();
    mapDept.add(memberCount);

    if (!members.isEmpty()) {
      mRanks.add(members.get(0).getMRank());//첫번째 멤버의 mRank 정보를 가져옴
    } else {
      mRanks.add("No Rank"); //멤버가 없는 경우 "No Rank"로 설정
    }
  }

    deptList.put("dept", mapDept);
    deptList.put("deptList", departments);
    deptList.put("mRank", mRanks);
    return ResponseEntity.ok().body(deptList);

}


  //특정 부서의 멤버 목록을 반환하는 컨트롤러
  @GetMapping("/members/{department}")
  @ResponseBody
  public ResponseEntity<List<MemberDto>> getDepartmentMembers(@PathVariable ("department")String department){

    System.out.println(department+"<<department");
    List<MemberDto> members=departmentService.getMembersByDepartmentId(department);
    System.out.println(members.size()+"<<size");



    return ResponseEntity.ok().body(members);

  }

  @PostMapping("/updateMemberCount/{department}")
  public ResponseEntity<String> updateDepartmentMemberCount(@PathVariable("department")String department){
    departmentService.updateDepartmentMemberCount(department);
    return ResponseEntity.ok().body("Member count updated successfully for department:" +department);
  }





}












