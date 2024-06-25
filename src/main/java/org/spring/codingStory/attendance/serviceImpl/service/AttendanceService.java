package org.spring.codingStory.attendance.serviceImpl.service;

import org.spring.codingStory.attendance.dto.AttendanceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AttendanceService {

//    List<AttendanceDto> attList();

    Integer insertCheckInAttendance(AttendanceDto attendanceDto);
    Integer insertCheckInAttendance2(AttendanceDto attendanceDto);


    int attendanceDelete(Long id);

    int updateCheckOutAttendance(Long id, AttendanceDto attendanceDto);

    boolean hasAttendanceToday(Long memberId);

    ////////////////////////////////////////////

    AttendanceDto getAttendanceById(Long id);

    ////////////////////////////////////////////
    Page<AttendanceDto> attSearchPagingList(Pageable pageable);
}