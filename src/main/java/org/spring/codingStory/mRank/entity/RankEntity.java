package org.spring.codingStory.mRank.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.spring.codingStory.member.entity.MemberEntity;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "mRank")
public class RankEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mRank_id")
    private Long id;

    @Column(nullable = false,unique = true)
    private String rankName;

    @JsonIgnore // ajax시 순환참조 방지
    @OneToMany(mappedBy = "rankEntity"
            , fetch = FetchType.LAZY
            , cascade = CascadeType.REMOVE)
    private List<MemberEntity> memberEntityList;

}
