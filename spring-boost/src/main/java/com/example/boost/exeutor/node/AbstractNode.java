package com.example.boost.exeutor.node;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractNode implements Node {
    // 节点标识
    protected String id;
    // 子节点
    protected List<Node> child;

    public AbstractNode(String id) {
        this.id = id;
        this.child = new ArrayList<>(0);
    }

    public AbstractNode(String id, List<Node> child) {
        this.id = id;
        this.child = child;
    }

}
