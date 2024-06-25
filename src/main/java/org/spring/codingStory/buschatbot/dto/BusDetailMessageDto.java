package org.spring.codingStory.buschatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusDetailMessageDto {

  private String today;

  private String time;

  private BusDetailAnswerDto answer;

  public BusDetailMessageDto today(String today) {
    this.today=today;
    return this;
  }
  public BusDetailMessageDto answer(BusDetailAnswerDto answer) {
    this.answer=answer;
    return this;
  }
}
