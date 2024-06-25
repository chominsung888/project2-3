package org.spring.codingStory.board.employee.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.board.employee.dto.EmployeeReplyDto;
import org.spring.codingStory.board.employee.entity.EmployeeEntity;
import org.spring.codingStory.board.employee.entity.EmployeeReplyEntity;
import org.spring.codingStory.board.employee.repository.EmpReplyRepository;
import org.spring.codingStory.board.employee.repository.EmployeeRepository;
import org.spring.codingStory.board.employee.serviceImpl.service.EmployeeReplyService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeReplyServiceImpl implements EmployeeReplyService {

    private final EmployeeRepository employeeRepository;
    private final EmpReplyRepository empReplyRepository;


    @Override
    public void insertEmployeeReply(EmployeeReplyDto employeeReplyDto) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeReplyDto.getEmployeeId()).orElseThrow(() -> {
            throw new IllegalArgumentException("아이디가 없습니다.");
        });
        //해당 글이 null이 아니라면
        if (employeeEntity != null) {
            EmployeeReplyEntity employeeReplyEntity = EmployeeReplyEntity.builder()
                    .employeeEntity(EmployeeEntity.builder()
                            .id(employeeReplyDto.getEmployeeId()).build()) // 글의 아이디
                    .empReplyWriter(employeeReplyDto.getEmpReplyWriter())
                    .empReplyContent(employeeReplyDto.getEmpReplyContent())
                    .build(); // 찾아서 값 넣고
            empReplyRepository.save(employeeReplyEntity);
        }
    }

    @Override
    public List<EmployeeReplyDto> employeeReplyList(Long id) {
        EmployeeEntity shopEntity = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당글을 찾을 수 없습니다.:" + id));
        List<EmployeeReplyEntity> shopReplyEntityList = empReplyRepository.findAllByEmployeeEntity(shopEntity);

        List<EmployeeReplyDto> shopReplyDtoList = shopReplyEntityList.stream()
                .map(EmployeeReplyDto::toSelectReplyDto)
                .collect(Collectors.toList());
        return shopReplyDtoList;
    }

    @Override
    public Long employeeReplyDeleteById(Long id) {
        Long employeeId = empReplyRepository.findById(id).get().getEmployeeEntity().getId(); //댓글 id를 찾아라
        if (employeeId != null) {
            empReplyRepository.deleteById(id);
        } else {
            System.out.println("댓글삭제 실패");
        }
        return employeeId;
    }

}
