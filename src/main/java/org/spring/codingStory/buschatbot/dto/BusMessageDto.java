package org.spring.codingStory.buschatbot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BusMessageDto {

  private String today;

  private String time;

  private BusAnswerDto answer;

  public BusMessageDto today(String today) {
    this.today=today;
    return this;
  }
  public BusMessageDto answer(BusAnswerDto answer) {
    this.answer=answer;
    return this;
  }

}
