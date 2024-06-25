package org.spring.codingStory.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.member.dto.MemberFileDto;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "member_file")
public class MemberFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_file_id")
    private Long id;

    @Column(nullable = false)
    private String memberNewFileName;

    @Column(nullable = false)
    private String memberOldFileName;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


  public static MemberFileEntity toInsertFile(MemberFileDto fileDto) {
      MemberFileEntity fileEntity=new MemberFileEntity();

      fileEntity.setMemberEntity(fileDto.getMemberEntity());
      fileEntity.setMemberNewFileName(fileDto.getMemberNewFileName());
      fileEntity.setMemberOldFileName(fileDto.getMemberOldFileName());

      return fileEntity;
  }
}
