package org.spring.codingStory.bus.data;

import lombok.Data;

@Data
public class BusResponse{

  private ComMsgHeader comMsgHeader;

  private MsgHeader msgHeader;

  private MsgBody msgBody;

}
