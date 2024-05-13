package org.spring.codingStory.attendance.dto;

import lombok.*;
import org.spring.codingStory.member.entity.MemberEntity;

import java.time.LocalDateTime;

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

}