package org.spring.codingStory.fullcalender.serviceImpl.service;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.fullcalender.dto.FullCalenderDto;
import org.spring.codingStory.fullcalender.entity.FullCalenderEntity;
import org.spring.codingStory.fullcalender.reposiory.FullCalenderRepository;
import org.spring.codingStory.fullcalender.serviceImpl.FullCalenderServiceInterface;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.repository.MemberRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class FullCalenderService implements FullCalenderServiceInterface {

  private final FullCalenderRepository fullCalenderRepository;
  private static final Logger logger = Logger.getLogger(FullCalenderService.class.getName());
  private final MemberRepository memberRepository;


  public List<FullCalenderDto> fullCalenderListAll() {

    List<FullCalenderDto> fullCalenderDtoList=new ArrayList<>();
    List<FullCalenderEntity> fullCalenderEntities=fullCalenderRepository.findAll();

    for(FullCalenderEntity entity :fullCalenderEntities){
      FullCalenderDto fullCalenderDto=FullCalenderDto.builder()
          .id(entity.getId())
          .start(entity.getStart())
          .content(entity.getContent())
          .end(entity.getEnd())
          .build();

      fullCalenderDtoList.add(fullCalenderDto);
    }
    return fullCalenderDtoList;
  }


  public void setCalendar(FullCalenderDto dto) {

    //사용자가 인증이 되어있는지 확인
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      String currentUsername= authentication.getName();

      System.out.println(dto.getStart()+" start ...");
      //현재 사용자의 정보를 MemberEntity로 가져옴
      Optional<MemberEntity> memberEntity=memberRepository.findByUserEmail(currentUsername);

      FullCalenderEntity entity = FullCalenderEntity
          .builder()
          .content(dto.getContent())
          .memberEntity(memberEntity.get())
          .start(dto.getStart())
          .end(dto.getEnd())
          .build();
      FullCalenderEntity fullCalenderEntity = fullCalenderRepository.save(entity);

  }






  public List<FullCalenderDto> myFullCallerListAll(Long id) {
    MemberEntity memberEntity=memberRepository.findById(id).orElseThrow(IllegalArgumentException::new);

    List<FullCalenderEntity> fullCalenderDtoList= fullCalenderRepository.findByMemberEntity(memberEntity);


    return fullCalenderDtoList.stream().map(full->FullCalenderDto.builder()
        .start(full.getStart()).id(full.getId())
        .end(full.getEnd())
        .content(full.getContent())
        .build())
        .collect(Collectors.toList());


  }


  @Override
  public void deleteCalendarEvent(Integer eventId) {

    fullCalenderRepository.deleteById(eventId);

  }


  //사용자 개인 일정을 가져오는 매서드
  public List<FullCalenderDto> getUserEvents(Long userId){
    MemberEntity memberEntity= memberRepository.findById(userId).orElseThrow(()->new IllegalArgumentException("User not found"));
    List<FullCalenderEntity> userEvents=fullCalenderRepository.findByMemberEntity(memberEntity);

    return userEvents.stream().map(event->FullCalenderDto.builder()
        .id(event.getId())
        .start(event.getStart())
        .content(event.getContent())
        .end(event.getEnd())
        .build())
        .collect(Collectors.toList());

  }
  //모든 일정을 가져오는 메서드
  public List<FullCalenderDto> getAllEvents(){
    List<FullCalenderEntity> allEvents= fullCalenderRepository.findAll();

    return allEvents.stream().map(event -> FullCalenderDto.builder()
        .id(event.getId())
        .start(event.getStart())
        .content(event.getContent())
        .end(event.getEnd())
        .build())
        .collect(Collectors.toList());
  }



}
