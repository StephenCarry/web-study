package com.example.boost.exeutor.node.script;

import com.example.boost.exeutor.node.AbstractNode;
import groovy.lang.GroovyShell;

import java.util.HashMap;

public class ScriptNode extends AbstractNode {

    protected String scriptContext;

    public ScriptNode(String id) {
        super(id);
    }

    @Override
    public void execute() {
        GroovyShell groovyShell = new GroovyShell();
        groovyShell.setVariable("queryResults", new HashMap<>());
        groovyShell.evaluate(scriptContext);
    }
}
