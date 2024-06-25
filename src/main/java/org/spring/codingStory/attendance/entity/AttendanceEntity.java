package org.spring.codingStory.attendance.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.attendance.dto.AttendanceDto;
import org.spring.codingStory.member.entity.MemberEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "attendanceEntity")
public class AttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attendance_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @Column
    private LocalDateTime checkInTime;

    @Column
    private LocalDateTime checkOutTime;

    @Column
    private String attendanceType; // 정상 출근, 지각 등

    @Column
    private Time workTime;


    public Time calculationSetWorkTime(LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        Duration duration = Duration.between(checkInTime, checkOutTime);

        // Duration 객체를 통해 시간과 분 계산
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        LocalTime totalTime = LocalTime.of((int) hours, (int) minutes);

        // LocalTime 객체를 Time 타입으로 변환
        return Time.valueOf(totalTime);
    }

    ///////////////////////////////////////////////////////////////////////////

    public Duration calculationWorkTime(LocalDateTime checkInTime, LocalDateTime checkOutTime) {

        return Duration.between(checkInTime, checkOutTime);
    }
    ////////////////////////////////////////////////////////////////////////////

    public BigDecimal calculattionDailyWage(Duration workTime, BigDecimal hourWage) {
        BigDecimal dailyWage = BigDecimal.ZERO;
//        int overtimeThreshold = 8 * 60; // 8시간,480분 (8 시간 * 60 분)
        int overtimeThreshold = 2; // 8시간,480분 (8 시간 * 60 분)

        // 원래 시간에 대한 계산
        int regularTime = 0;
        if (workTime.toMinutes() >= overtimeThreshold) {
            regularTime = overtimeThreshold;
        } else {
            regularTime = (int) workTime.toMinutes();
        }

//        dailyWage = dailyWage.add(hourWage.multiply(BigDecimal.valueOf(regularTime)).divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP));
        dailyWage = dailyWage.add(hourWage.multiply(BigDecimal.valueOf(100)));

        // 초과 근무에 대한 계산
        if (workTime.toMinutes() > overtimeThreshold) {
            int extraTime = (int) workTime.toMinutes() - overtimeThreshold;
            BigDecimal extraWage = hourWage.multiply(new BigDecimal("1.5"));
            dailyWage = dailyWage.add(extraWage.multiply(BigDecimal.valueOf(extraTime)).divide(BigDecimal.valueOf(60), RoundingMode.HALF_UP));
        }

        return dailyWage;
    }


    public static AttendanceEntity toInsertCheckInAttendanceEntity(AttendanceDto attendanceDto) {

//        LocalDateTime checkInTime = LocalDateTime.now();
        LocalDateTime checkInTime = LocalDateTime.now().withNano(0);
        LocalDateTime checkOutTime = LocalDateTime.now().withNano(0);

        AttendanceEntity attendanceEntity=new AttendanceEntity();
        attendanceEntity.setMemberEntity(attendanceDto.getMemberEntity());
        attendanceEntity.setCheckInTime(checkInTime);
        attendanceEntity.setAttendanceType("출근");
        return attendanceEntity;
    }


}