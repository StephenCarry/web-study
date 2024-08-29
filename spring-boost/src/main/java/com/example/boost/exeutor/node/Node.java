package com.example.boost.exeutor.node;

import java.util.ArrayList;
import java.util.List;

public interface Node {
    List<Node> child = new ArrayList<Node>(0);

    void execute();
}
