package com.mymusic.model;





import com.mymusic.common.domain.tree.DataTree;
import com.mymusic.domain.SysMenu;

import java.util.List;

public class SysMenuNode extends SysMenu implements DataTree<SysMenuNode,Long> {

    private List<SysMenuNode> children;

    private String path;
    private String name;

    public String getPath() {
      return this.getUrl();
    }

    public String getName() {
      return this.getMenuName();
    }

    @Override
    public Long getParentId() {
        return super.getMenuPid();
    }

    @Override
    public void setChildren(List<SysMenuNode> children) {
        this.children = children;
    }

    @Override
    public List<SysMenuNode> getChildren() {
        return this.children;
    }
}
