package org.spring.codingStory.buschatbot.busArrInfo;

import lombok.Data;

@Data
public class BusArrInfoResponse {

  private ComMsgHeader comMsgHeader;
  private MsgBody msgBody;
  private MsgHeader msgHeader;


}
