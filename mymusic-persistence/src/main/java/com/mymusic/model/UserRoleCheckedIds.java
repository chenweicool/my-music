package com.mymusic.model;


import java.util.List;

public class UserRoleCheckedIds {

  private Long userId;

  private List<Long> checkedIds;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public List<Long> getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(List<Long> checkedIds) {
    this.checkedIds = checkedIds;
  }
}
