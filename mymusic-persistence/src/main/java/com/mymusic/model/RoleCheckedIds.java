package com.mymusic.model;

import java.util.List;

public class RoleCheckedIds {

  private Long roleId;

  private List<Long> checkedIds;

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public List<Long> getCheckedIds() {
    return checkedIds;
  }

  public void setCheckedIds(List<Long> checkedIds) {
    this.checkedIds = checkedIds;
  }
}
