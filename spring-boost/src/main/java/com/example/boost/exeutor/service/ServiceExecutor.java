package com.example.boost.exeutor.service;

import com.example.boost.exeutor.entity.Parameter;
import com.example.boost.exeutor.node.Node;

import java.util.ArrayList;
import java.util.List;

public class ServiceExecutor {
    // 服务编码
    private String serviceId;

    // 入参
    private Parameter request;

    // 最后节点
    private Node lastNode;

    // 执行链
    List<Node> nodes = new ArrayList<Node>(1);

    public ServiceExecutor() {

    }

    public Object execute() {
        if (nodes.isEmpty()) {
            // 报错
        }
        return null;
    }
}
