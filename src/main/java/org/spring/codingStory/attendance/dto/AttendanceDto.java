package org.spring.codingStory.attendance.dto;

import lombok.*;
import org.spring.codingStory.attendance.entity.AttendanceEntity;
import org.spring.codingStory.member.entity.MemberEntity;

import java.sql.Time;
import java.time.LocalDateTime;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AttendanceDto {

    private Long id;

    private MemberEntity memberEntity;

    private LocalDateTime checkInTime;

    private LocalDateTime checkOutTime;

    private String attendanceType;

    private Long memberId;

    private Time workTime;


    //entity -> dto
    public static AttendanceDto toSelectAllAttendanceDto(AttendanceEntity attendanceEntity) {
        AttendanceDto attendanceDto=new AttendanceDto();

        attendanceDto.setId(attendanceEntity.getId());
        attendanceDto.setMemberEntity(attendanceEntity.getMemberEntity());
        attendanceDto.setCheckInTime(attendanceEntity.getCheckInTime());
        attendanceDto.setCheckOutTime(attendanceEntity.getCheckOutTime());
        attendanceDto.setAttendanceType(attendanceEntity.getAttendanceType());
        attendanceDto.setWorkTime(attendanceEntity.getWorkTime());

        return attendanceDto;
    }

    //entity -> dto
    public static AttendanceDto toSelectWorkTimeAttendanceDto(Time workTime) {
        AttendanceDto dto = new AttendanceDto();
        dto.setWorkTime(workTime);
        return dto;
    }

}
