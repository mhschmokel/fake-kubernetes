package k8;

import lombok.Getter;

@Getter
public abstract class Node {
    NodeType nodeType;

    Node() {}
    Node(NodeType nodeType){
        this.nodeType = nodeType;
    }
}
