package org.spring.codingStory.bus.data;

import lombok.Data;

@Data
public class MsgHeader {

  private String headerMsg;
  private String headerCd;
  private String itemCount;
}
