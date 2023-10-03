package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = true)
public class Worker extends Node {

    public Worker(String name, double totalCPU, double totalMemory) {
        super(name, NodeType.WORKER, totalCPU, totalMemory);
    }

    public Worker(String name, double totalCPU, double totalMemory, double latency) {
        super(name, NodeType.WORKER, totalCPU, totalMemory, latency);
    }
}
