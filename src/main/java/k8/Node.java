package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode
public abstract class Node {
    final private UUID uuid = UUID.randomUUID();
    final private String name;
    final private NodeType nodeType;
    final private double totalCPU;
    final private double totalMemory;
    private double availableCPU;
    private double availableMemory;
    final private double latency;
    final private Set<Pod> pods = new HashSet<Pod>();

    Node(String name, NodeType nodeType, double totalCPU, double totalMemory){
        this.name = name;
        this.nodeType = nodeType;
        this.totalCPU = totalCPU;
        this.totalMemory = totalMemory;
        this.availableCPU = totalCPU;
        this.availableMemory = totalMemory;
        this.latency = 0;
    }

    Node(String name, NodeType nodeType, double totalCPU, double totalMemory, double latency){
        this.name = name;
        this.nodeType = nodeType;
        this.totalCPU = totalCPU;
        this.totalMemory = totalMemory;
        this.availableCPU = totalCPU;
        this.availableMemory = totalMemory;
        this.latency = latency;
    }

    public void addPod(Pod p) {
        if(pods.add(p)) {
            this.availableCPU -= p.getRequiredCPU();
            this.availableMemory -= p.getRequiredMemory();
            p.setPodStatus(PodStatus.RUNNING);
        }
    }

    public void deletePod(Pod p) {
        if(pods.remove(p)) {
            this.availableCPU += p.getRequiredCPU();
            this.availableMemory += p.getRequiredMemory();
            p.setPodStatus(PodStatus.STOPPED);
        }
    }

    public void deleteAllPods() {
        pods.forEach(this::deletePod);
        pods.clear();
    }

    public boolean hasEnoughMemory(Pod p) {
        return p.getRequiredMemory() <= this.totalMemory - this.availableMemory;
    }

    public boolean hasEnoughCPU(Pod p) {
        return p.getRequiredCPU() <= this.totalCPU - this.availableCPU;
    }

    public boolean isLatencyAcceptable(Pod p) {
        return p.getMaximumLatency() <= this.latency;
    }

    @Override
    public String toString() {
        long numOfPods = pods.size();
        int emptySize = 20 - this.getName().length();
        return this.getName() + " ".repeat(Math.max(0, emptySize))
                + this.getNodeType().toString() + " ".repeat(3)
                + this.getTotalCPU() + " ".repeat(3)
                + this.getTotalMemory() + " ".repeat(5)
                + this.getAvailableCPU() + " ".repeat(5)
                + this.getAvailableMemory() + " ".repeat(5)
                + this.getLatency() + " ".repeat(8)
                + numOfPods;
    }
}
