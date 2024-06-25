package org.spring.codingStory.attendance.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.attendance.dto.AttendanceDto;
import org.spring.codingStory.attendance.entity.AttendanceEntity;
import org.spring.codingStory.attendance.repository.AttendanceRepository;
import org.spring.codingStory.attendance.serviceImpl.service.AttendanceService;
import org.spring.codingStory.member.entity.MemberEntity;
import org.spring.codingStory.member.repository.MemberRepository;
import org.spring.codingStory.pay.repository.PayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;

    @Autowired
    private PayRepository payRepository;

    @Override
    public Page<AttendanceDto> attSearchPagingList(Pageable pageable) {
        Page<AttendanceEntity> attendanceEntities = attendanceRepository.findAll(pageable);
        return attendanceEntities.map(AttendanceDto::toSelectAllAttendanceDto);
    }



    public boolean canCheckIn(Long memberId) {
        Optional<AttendanceEntity> latestAttendance = attendanceRepository.findTopByMemberEntityIdOrderByCheckInTimeDesc(memberId);
        return latestAttendance.map(attendance -> !"출근".equals(attendance.getAttendanceType())).orElse(true);
    }



    @Override
    public Integer insertCheckInAttendance(AttendanceDto attendanceDto) {

        if (!canCheckIn(attendanceDto.getMemberId())) {
            throw new IllegalStateException("이미 출근 기록이 있습니다.");
        }

        attendanceDto.setMemberEntity(MemberEntity.builder().id(attendanceDto.getMemberId()).build());
        AttendanceEntity attendanceEntity = AttendanceEntity.toInsertCheckInAttendanceEntity(attendanceDto);
        attendanceRepository.save(attendanceEntity);
        return 1;
    }

    @Override
    public Integer insertCheckInAttendance2(AttendanceDto attendanceDto) {

        if (!canCheckIn(attendanceDto.getMemberId())) {
            throw new IllegalStateException("이미 출근 기록이 있습니다.");
        }

        attendanceDto.setMemberEntity(MemberEntity.builder().id(attendanceDto.getMemberId()).build());
        AttendanceEntity attendanceEntity = AttendanceEntity.toInsertCheckInAttendanceEntity(attendanceDto);
        AttendanceEntity savedEntity = attendanceRepository.save(attendanceEntity);
        attendanceDto.setId(savedEntity.getId());
        attendanceDto.setAttendanceType(savedEntity.getAttendanceType());
        attendanceDto.setCheckInTime(savedEntity.getCheckInTime());
        attendanceDto.setCheckOutTime(savedEntity.getCheckOutTime());
        return 1;
    }

    @Override
    public int attendanceDelete(Long id) {
        Optional<AttendanceEntity> optionalAttendanceEntity = attendanceRepository.findById(id);
        if (optionalAttendanceEntity.isPresent()) {
            attendanceRepository.delete(optionalAttendanceEntity.get());
            return 1;
        }
        return 0;
    }

    @Override
    @Transactional
    public int updateCheckOutAttendance(Long id, AttendanceDto attendanceDto) {
        Optional<AttendanceEntity> optionalAttendanceEntity = attendanceRepository.findById(id);
        if (optionalAttendanceEntity.isPresent()) {
            AttendanceEntity attendanceEntity = optionalAttendanceEntity.get();
            LocalDateTime checkOutTime = LocalDateTime.now().withNano(0);
            attendanceEntity.setCheckOutTime(checkOutTime);
            attendanceEntity.setAttendanceType("퇴근");
            attendanceEntity.setWorkTime(
                    attendanceEntity.calculationSetWorkTime(attendanceEntity.getCheckInTime(), checkOutTime));
            attendanceRepository.save(attendanceEntity);
            return 1;
        }
        return 0;
    }

    @Override
    public AttendanceDto getAttendanceById(Long id) {
        AttendanceEntity attendanceEntity = attendanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid attendance ID"));
        return AttendanceDto.builder()
                .id(attendanceEntity.getId())
                .memberId(attendanceEntity.getMemberEntity().getId())
                .attendanceType(attendanceEntity.getAttendanceType())
                .checkInTime(attendanceEntity.getCheckInTime())
                .checkOutTime(attendanceEntity.getCheckOutTime())
                .workTime(attendanceEntity.getWorkTime())
                .build();
    }

    @Override
    public boolean hasAttendanceToday(Long memberId) {
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = today.atStartOfDay();
//        LocalDateTime todayEnd = today.atTime(23, 59, 59);
        LocalDateTime todayEnd = today.atStartOfDay();
        return attendanceRepository.existsByEmployeeIdAndStartTimeBetween(memberId, todayStart, todayEnd);
    }
}