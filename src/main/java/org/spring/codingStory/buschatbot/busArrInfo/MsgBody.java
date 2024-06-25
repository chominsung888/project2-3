package org.spring.codingStory.buschatbot.busArrInfo;

import lombok.Data;

import java.util.List;

@Data
public class MsgBody {
  private List<ItemArrList> itemList;
}
