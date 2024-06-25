package org.spring.codingStory.attendance.controller.api;

import lombok.RequiredArgsConstructor;
import org.spring.codingStory.attendance.dto.AttendanceDto;
import org.spring.codingStory.attendance.serviceImpl.AttendanceServiceImpl;
import org.spring.codingStory.member.serviceImpl.MemberServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
@Transactional
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AttendanceController {

    private final AttendanceServiceImpl attendanceServiceImpl;
    private final MemberServiceImpl memberServiceImpl;

    @PostMapping("/attendance/checkInTime")
    public ResponseEntity<Integer> checkInTimeInsert(@RequestBody AttendanceDto attendanceDto){

        if (attendanceServiceImpl.hasAttendanceToday(attendanceDto.getMemberId())) {
            throw new IllegalArgumentException("already checked");
        }
        int checkInTime= attendanceServiceImpl.insertCheckInAttendance(attendanceDto);
        return new ResponseEntity<>(checkInTime,HttpStatus.OK);
    }

    @PutMapping("/attendance/{id}")
    public ResponseEntity<?> checkOutTimeInsert(@PathVariable("id") Long id, @RequestBody AttendanceDto attendanceDto) {
        int result = attendanceServiceImpl.updateCheckOutAttendance(id, attendanceDto);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/attendance/attList")
    public ResponseEntity<Page<AttendanceDto>> attList(
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<AttendanceDto> pagingList = attendanceServiceImpl.attSearchPagingList(pageable);
        return new ResponseEntity<>(pagingList, HttpStatus.OK);
    }

    @PostMapping("/attendance/attendanceDelete/{id}")
    public ResponseEntity<?> attendanceDelete(@PathVariable("id")Long id) {
        int result= attendanceServiceImpl.attendanceDelete(id);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/attendance/test/checkInTime")
    public ResponseEntity<?> checkInTimeInsert2(@RequestBody AttendanceDto attendanceDto) {
        attendanceServiceImpl.insertCheckInAttendance2(attendanceDto);
        return new ResponseEntity<>(attendanceDto, HttpStatus.OK);
    }

    @PutMapping("/attendance/test/{id}")
    public ResponseEntity<AttendanceDto> checkOutTimeInsert2(@PathVariable("id") Long id, @RequestBody AttendanceDto attendanceDto) {
        attendanceServiceImpl.updateCheckOutAttendance(id, attendanceDto);
        AttendanceDto updatedAttendance = attendanceServiceImpl.getAttendanceById(id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedAttendance);
    }
}