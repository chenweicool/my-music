package com.mymusic.model;




import com.mymusic.common.domain.tree.DataTree;
import com.mymusic.domain.SysOrg;

import java.util.List;

public class SysOrgNode extends SysOrg implements DataTree<SysOrgNode,Long> {

    private List<SysOrgNode> children;

    @Override
    public Long getParentId() {
        return super.getOrgPid();
    }

    @Override
    public void setChildren(List<SysOrgNode> children) {
        this.children = children;
    }

    @Override
    public List<SysOrgNode> getChildren() {
        return this.children;
    }
}
