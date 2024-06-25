package org.spring.codingStory.fullcalender.entity;

import lombok.*;
import org.spring.codingStory.member.entity.MemberEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name= "fullCalender")
public class FullCalenderEntity {

  @Id
  @Column(nullable = false)
  @GeneratedValue(strategy =GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = true)
  private String username;

  @Column(nullable = false)
  private String content;

  @Column(nullable = false)
  private Date start;

  @Column(nullable = false)
  private Date end;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="member_id")
  private MemberEntity memberEntity;

}
