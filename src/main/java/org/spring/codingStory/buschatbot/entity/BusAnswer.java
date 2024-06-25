package org.spring.codingStory.buschatbot.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.spring.codingStory.buschatbot.dto.BusAnswerDto;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bus_answer")
@Entity
public class BusAnswer {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long no;

  private String content;

  private String keyword;    //name

  public BusAnswer keyword(String keyword) {
    this.keyword=keyword;
    return this;
  }

  public BusAnswerDto toAnswerDTO() {
    return BusAnswerDto.builder()
            .no(no).content(content).keyword(keyword)
            .build();
  }




}
