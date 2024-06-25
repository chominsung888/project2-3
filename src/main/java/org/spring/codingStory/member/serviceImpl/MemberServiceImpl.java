package org.spring.codingStory.member.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.member.dto.MemberDto;
import org.spring.codingStory.member.dto.MemberFileDto;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.entity.MemberFileEntity;
import org.spring.codingStory.member.repository.MemberFileRepository;
import org.spring.codingStory.member.repository.MemberRepository;
import org.spring.codingStory.member.role.Role;
import org.spring.codingStory.member.serviceImpl.service.MemberService;
import org.spring.codingStory.payment.entity.PaymentEntity;
import org.spring.codingStory.payment.repository.PaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberServiceImpl implements MemberService {


  private final MemberRepository memberRepository;
  private final PasswordEncoder passwordEncoder;
  private final MemberFileRepository fileRepository;
  private final PaymentRepository paymentRepository;


  @Override
  public void memberJoin(MemberDto memberDto) throws IOException {

    if (memberDto.getMemberFile().isEmpty()) {
      MemberEntity memberEntity = MemberEntity.toJoinMember(memberDto, passwordEncoder);
      memberRepository.save(memberEntity);
    } else {
      MultipartFile memberFile = memberDto.getMemberFile();

      String oldFileName = memberFile.getOriginalFilename();
      UUID uuid = UUID.randomUUID();
      String newFileName = uuid + "_" + oldFileName;
      String fileSavePath =  "C:/codingStory_file/" + newFileName;
      memberFile.transferTo(new File(fileSavePath));

      MemberEntity memberEntity = MemberEntity.toJoinFileMember(memberDto, passwordEncoder);
      Long id = memberRepository.save(memberEntity).getId();

      Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
      if (optionalMemberEntity.isPresent()) {
        MemberEntity memberEntity1 = optionalMemberEntity.get();

        MemberFileDto fileDto = MemberFileDto.builder().memberOldFileName(oldFileName)
                .memberNewFileName(newFileName)
                .memberEntity(memberEntity1)
                .build();

        MemberFileEntity fileEntity = MemberFileEntity.toInsertFile(fileDto);
        fileRepository.save(fileEntity);
      } else {
        throw new IllegalArgumentException("xxxx");
      }
    }
  }

  @Override
  public List<MemberDto> memberList() {
    List<MemberEntity> memberEntities = memberRepository.findAll();

    List<MemberDto> memberDtoList = new ArrayList<>();

    for (MemberEntity member : memberEntities) {
      MemberDto memberDto = MemberDto.toSelectMemberDto(member);
      memberDtoList.add(memberDto);
    }
    return memberDtoList;
  }

  @Override
  public MemberDto memberDetail(Long id) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

    if (optionalMemberEntity.isPresent()) {

      MemberEntity memberEntity = optionalMemberEntity.get();

      MemberDto memberDto = MemberDto.toSelectMemberDto(memberEntity);

      return memberDto;
    }
    return null;
  }

//    @Override
//    public void memberUpdate(MemberDto memberDto) throws IOException {
//
//        MemberEntity memberEntity = memberRepository.findById(memberDto.getId()).orElseThrow(IllegalArgumentException::new);
//
//        Optional<MemberFileEntity> optionalMemberFileEntity = fileRepository.findByMemberEntityId(memberDto.getId());
//
//        if (optionalMemberFileEntity.isPresent()) {
//            String newFileName = optionalMemberFileEntity.get().getMemberNewFileName();
//            String filePath = "C:/codingStory_file/" + newFileName;
//            File deleteFile = new File(filePath);
//            if (deleteFile.exists()) {
//                deleteFile.delete();
//            } else {
//                System.out.println("파일이 존재하지 않습니다.");
//            }
//            fileRepository.delete(optionalMemberFileEntity.get());
//        }
//        String oldPw = memberEntity.getUserPw();
//
//        if (memberDto.getMemberFile().isEmpty() && memberDto.getUserPw().equals(oldPw)) {
//            memberEntity = MemberEntity.toUpdateMember(memberDto);
//            memberRepository.save(memberEntity);
//        } else if (!memberDto.getMemberFile().isEmpty() && memberDto.getUserPw().equals(oldPw)) {
//            MultipartFile memberFile = memberDto.getMemberFile();
//            String oldFileName = memberFile.getOriginalFilename();
//            UUID uuid = UUID.randomUUID();
//            String newFileName = uuid + "_" + oldFileName;
//
//            String savePath = "C:/codingStory/" + newFileName;
//            memberFile.transferTo(new File(savePath));
//
//            memberDto.setMemberFileName(newFileName);
//
//            memberEntity = MemberEntity.toUpdateFileMember(memberDto);
//
//            Long memberId = memberRepository.save(memberEntity).getId();
//
//            MemberEntity memberEntity1 =
//                    memberRepository.findById(memberId).orElseThrow(() -> {
//                        throw new IllegalArgumentException("해당 아이디가 존재하지 않습니다.");
//                    });
//
//            MemberFileDto memberFileDto
//                    = MemberFileDto.builder()
//                    .memberOldFileName(oldFileName)
//                    .memberNewFileName(newFileName)
//                    .memberEntity(memberEntity1)
//                    .build();
//
//            MemberFileEntity memberFileEntity = MemberFileEntity
//                    .builder()
//                    .memberEntity(memberFileDto.getMemberEntity())
//                    .memberOldFileName(memberFileDto.getMemberOldFileName())
//                    .memberNewFileName(memberFileDto.getMemberNewFileName())
//                    .build();
//
//            fileRepository.save(memberFileEntity);
//
//        } else if (memberDto.getMemberFile().isEmpty() && !memberDto.getUserPw().equals(oldPw)) {
//
//            String newPw = passwordEncoder.encode(memberDto.getUserPw());
//
//            memberDto.setUserPw(newPw);
//            memberEntity = MemberEntity.toUpdateMember(memberDto);
//            memberRepository.save(memberEntity);
//
//        } else if (!memberDto.getMemberFile().isEmpty() && !memberDto.getUserPw().equals(oldPw)) {
//
//            MultipartFile memberFile = memberDto.getMemberFile();
//            String oldFileName = memberFile.getOriginalFilename();
//            UUID uuid = UUID.randomUUID();
//            String newFileName = uuid + "_" + oldFileName;
//
//            String savePath = "C:/codingStory/" + newFileName;
//            memberFile.transferTo(new File(savePath));
//
//            memberDto.setMemberFileName(newFileName);
//
//            String newPw = passwordEncoder.encode(memberDto.getUserPw());
//
//            memberDto.setUserPw(newPw);
//
//            memberEntity = MemberEntity.toUpdateFileMember(memberDto);
//
//            Long memberId = memberRepository.save(memberEntity).getId();
//
//            MemberEntity memberEntity1 = memberRepository.findById(memberId).orElseThrow(() -> {
//                throw new RuntimeException("해당 아이디가 존재하지 않습니다.");
//            });
//
//            MemberFileDto memberFileDto
//                    = MemberFileDto.builder()
//                    .memberOldFileName(oldFileName)
//                    .memberNewFileName(newFileName)
//                    .memberEntity(memberEntity1)
//                    .build();
//
//            MemberFileEntity memberFileEntity = MemberFileEntity
//                    .builder()
//                    .memberEntity(memberFileDto.getMemberEntity())
//                    .memberOldFileName(memberFileDto.getMemberOldFileName())
//                    .memberNewFileName(memberFileDto.getMemberNewFileName())
//                    .build();
//
//            fileRepository.save(memberFileEntity);
//        }
//    }

  @Override
  public Page<MemberDto> memberPagingList(Pageable pageable, String department) {

    Page<MemberEntity> memberEntities = null;

    if (department == null) {
      memberEntities = memberRepository.findByRole(pageable, Role.MEMBER);

    } else {

      if (department.equals("노원점")) {
        memberEntities = memberRepository.findByRoleAndDepartmentContains(pageable, Role.MEMBER, department);
      } else if (department.equals("야외관")) {
        memberEntities = memberRepository.findByRoleAndDepartmentContains(pageable, Role.MEMBER, department);
      } else if (department.equals("자동차관")) {
        memberEntities = memberRepository.findByRoleAndDepartmentContains(pageable, Role.MEMBER, department);
      } else if (department.equals("커플관")) {
        memberEntities = memberRepository.findByRoleAndDepartmentContains(pageable, Role.MEMBER, department);
      } else {
        memberEntities = memberRepository.findByRole(pageable, Role.MEMBER);
      }

    }


    Page<MemberDto> memberDtos = memberEntities.map(MemberDto::toSelectMemberDto);


    return memberDtos;
  }

  @Override
  public void memberNameUpdate(MemberDto memberDto) {

    MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("xx"));

    memberEntity.setName(memberDto.getName());

    memberRepository.save(memberEntity);
  }

  @Override
  public void memberPhoneNumberUpdate(MemberDto memberDto) {
    MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("xx"));

    memberEntity.setPhoneNumber(memberDto.getPhoneNumber());

    memberRepository.save(memberEntity);
  }

  @Override
  public void memberAddressUpdate(MemberDto memberDto) {

    MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("xx"));

    memberEntity.setAddress(memberDto.getAddress());

    memberRepository.save(memberEntity);

  }


  @Override
  public int findCheck(MemberDto memberDto) {

    MemberEntity memberEntity = memberRepository.findByUserEmail(memberDto.getUserEmail()).orElseThrow(IllegalArgumentException::new);

    String email = memberDto.getUserEmail();
    String name = memberDto.getName();
    String phoneNumber = memberDto.getPhoneNumber();

    if (email.equals(memberEntity.getUserEmail()) &&
            name.equals(memberEntity.getName()) &&
            phoneNumber.equals(memberEntity.getPhoneNumber())) {
      return 1;

    } else {
      return 0;
    }
  }

  @Override
  public void findPasswordFin(MemberDto memberDto) {
    MemberEntity memberEntity = memberRepository.findByUserEmail(memberDto.getUserEmail()).orElseThrow(IllegalArgumentException::new);

    memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));

    memberRepository.save(memberEntity);
  }



  @Override
  public Page<MemberDto> memberAppList(Pageable pageable) {
    Page<MemberEntity> memberEntities = null;

    memberEntities = memberRepository.findByRole(pageable, Role.GEUST);

    Page<MemberDto> memberDtos = memberEntities.map(MemberDto::toSelectMemberDto);

    return memberDtos;
  }

  @Override
  public void memberPasswordUpdate(MemberDto memberDto) {

    MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("xx"));

    memberEntity.setUserPw(passwordEncoder.encode(memberDto.getUserPw()));

    memberRepository.save(memberEntity);

  }

  @Override
  public void profileUpdate(MemberDto memberDto) throws IOException {


    MemberEntity memberEntity = memberRepository.findById(memberDto.getId()).orElseThrow(IllegalArgumentException::new);

    Optional<MemberFileEntity> optionalMemberFileEntity = fileRepository.findByMemberEntityId(memberDto.getId());

    if (optionalMemberFileEntity.isPresent()) {
      String newFileName = optionalMemberFileEntity.get().getMemberNewFileName();
      String filePath = "C:/codingStory_file/" + newFileName;
      File deleteFile = new File(filePath);
      if (deleteFile.exists()) {
        deleteFile.delete();
      } else {
        System.out.println("파일이 존재하지 않습니다.");
      }
      fileRepository.delete(optionalMemberFileEntity.get());
    }

    if (memberDto.getMemberFile().isEmpty()) {
      memberEntity=MemberEntity.toUpdateMember(memberDto,passwordEncoder);
      memberRepository.save(memberEntity);
    } else if (!memberDto.getMemberFile().isEmpty()) {
      MultipartFile memberFile = memberDto.getMemberFile();
      String oldFileName = memberFile.getOriginalFilename();
      UUID uuid = UUID.randomUUID();
      String newFileName = uuid + "_" + oldFileName;

      String savePath = "C:/codingStory_file/" + newFileName;
      memberFile.transferTo(new File(savePath));

      memberDto.setMemberFileName(newFileName);


      memberEntity = MemberEntity.toUpdateFileMember(memberDto, passwordEncoder);

      Long memberId = memberRepository.save(memberEntity).getId();

      MemberEntity memberEntity1 =
              memberRepository.findById(memberId).orElseThrow(() -> {
                throw new IllegalArgumentException("해당 아이디가 존재하지 않습니다.");
              });

      MemberFileDto memberFileDto
              = MemberFileDto.builder()
              .memberOldFileName(oldFileName)
              .memberNewFileName(newFileName)
              .memberEntity(memberEntity1)
              .build();

      MemberFileEntity memberFileEntity = MemberFileEntity
              .builder()
              .memberEntity(memberFileDto.getMemberEntity())
              .memberOldFileName(memberFileDto.getMemberOldFileName())
              .memberNewFileName(memberFileDto.getMemberNewFileName())
              .build();

      fileRepository.save(memberFileEntity);

    }
  }

  @Override
  public int memberJoin2(MemberDto memberDto) throws IOException {
    if (memberDto.getMemberFile().isEmpty()) {
      MemberEntity memberEntity = MemberEntity.toJoinMember(memberDto, passwordEncoder);
      memberRepository.save(memberEntity);
      return 1;
    } else {
      MultipartFile memberFile = memberDto.getMemberFile();

      String oldFileName = memberFile.getOriginalFilename();
      UUID uuid = UUID.randomUUID();
      String newFileName = uuid + "_" + oldFileName;
      String fileSavePath = "C:/codingStory_file/" + newFileName;
      memberFile.transferTo(new File(fileSavePath));

      MemberEntity memberEntity = MemberEntity.toJoinFileMember(memberDto, passwordEncoder);
      Long id = memberRepository.save(memberEntity).getId();

      Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
      if (optionalMemberEntity.isPresent()) {
        MemberEntity memberEntity1 = optionalMemberEntity.get();

        MemberFileDto fileDto = MemberFileDto.builder().memberOldFileName(oldFileName)
                .memberNewFileName(newFileName)
                .memberEntity(memberEntity1)
                .build();

        MemberFileEntity fileEntity = MemberFileEntity.toInsertFile(fileDto);
        fileRepository.save(fileEntity);
        return 1;
      } else {
        throw new IllegalArgumentException("xxxx");
      }
    }
  }


  @Override
  public void memberMDUdate(MemberDto memberDto) {

    MemberEntity memberEntity=memberRepository.findById(memberDto.getId()).orElseThrow(IllegalArgumentException::new);

    memberEntity=MemberEntity.MDUpdate(memberDto);

    memberRepository.save(memberEntity);
  }

  @Override
  public void memberMRankUpdate(MemberDto memberDto) {
    MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("xx"));

    memberEntity.setMRank(memberDto.getMRank());

    memberRepository.save(memberEntity);
  }

  @Override
  public void memberDepartUpdate(MemberDto memberDto) {
    MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("xx"));

    memberEntity.setDepartment(memberDto.getDepartment());

    memberRepository.save(memberEntity);
  }

  @Override
  public MemberDto memberTest(Long id) {
    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

    if (optionalMemberEntity.isPresent()) {

      MemberEntity memberEntity = optionalMemberEntity.get();

      MemberDto memberDto = MemberDto.toSelectMemberTest(memberEntity);

      return memberDto;
    }
    return null;
  }

  @Override
  public List<MemberDto> findByDepartment(String dptName) {

    List<MemberEntity> memberEntities=memberRepository.findByDepartment(dptName);

    return memberEntities.stream().map(MemberDto::toSelectMemberDto).collect(Collectors.toList());
  }


  @Override
  public int memberDelete(Long id) {

    Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);

    if (optionalMemberEntity.isPresent()) {
      memberRepository.delete(optionalMemberEntity.get());
      return 1;
    }

    return 0;
  }

    @Override
    public int memberAppOk(MemberDto memberDto) {
        //멤버 정보를 가져옵니다.

        MemberEntity memberEntity = memberRepository.findById(memberDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("xx"));

        memberEntity.setRole(Role.MEMBER);
        memberEntity.setDepartment(memberDto.getDepartment());
        memberEntity.setMRank(memberDto.getMRank());
        memberRepository.save(memberEntity);

        // PaymentEntity 생성 및 저장
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setMemberEntity(memberEntity);

        // mRank가 "사장"일 때 hourWage를 1000원으로 설정

        if ("사원".equals(memberDto.getMRank())) {
            paymentEntity.setHourlyWage("500");
        }else if ("지점장".equals(memberDto.getMRank())) {
            paymentEntity.setHourlyWage("700");
        }
        else {
            paymentEntity.setHourlyWage("600"); // 기본 값 설정
        }

        paymentRepository.save(paymentEntity);

        return 1;
    }

}
