package com.example.boost.exeutor.node;

import java.util.List;

public class SequenceNode extends AbstractNode {

    public SequenceNode(String id, List<Node> child) {
        super(id, child);
    }

    @Override
    public void execute() {

    }
}
