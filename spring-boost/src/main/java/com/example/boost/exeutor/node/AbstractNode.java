package com.example.boost.exeutor.node;

import java.util.ArrayList;
import java.util.List;

public class AbstractNode implements Node {

    protected List<Node> child = new ArrayList<Node>(0);

    @Override
    public void execute() {

    }
}
