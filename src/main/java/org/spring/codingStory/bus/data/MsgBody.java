package org.spring.codingStory.bus.data;

import lombok.Data;

import java.util.List;

@Data
public class MsgBody{

  private List<ItemList> itemList;
}
