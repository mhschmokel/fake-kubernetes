package k8;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ControlPlane extends Node {

    ControlPlane(String name, double totalCPU, double totalMemory) {
        super(name, NodeType.MASTER, totalCPU, totalMemory);
    }

    ControlPlane(String name, double totalCPU, double totalMemory, double latency) {
        super(name, NodeType.MASTER, totalCPU, totalMemory, latency);
    }
}