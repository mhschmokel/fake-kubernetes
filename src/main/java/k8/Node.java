package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
public abstract class Node {
    final private UUID uuid = UUID.randomUUID();
    final private String name;
    final private NodeType nodeType;
    final private double totalCPU;
    final private double totalMemory;
    private double cpuInUse;
    private double memoryInUse;
    final private double latency;
    final private List<Pod> pods = new ArrayList<>();

    Node(String name, NodeType nodeType, double totalCPU, double totalMemory){
        this.name = name;
        this.nodeType = nodeType;
        this.totalCPU = totalCPU;
        this.totalMemory = totalMemory;
        this.cpuInUse = 0;
        this.memoryInUse = 0;
        this.latency = 0;
    }

    Node(String name, NodeType nodeType, double totalCPU, double totalMemory, double latency){
        this.name = name;
        this.nodeType = nodeType;
        this.totalCPU = totalCPU;
        this.totalMemory = totalMemory;
        this.cpuInUse = 0;
        this.memoryInUse = 0;
        this.latency = latency;
    }

    public void addPod(Pod p) {
        if(pods.add(p)) {
            this.cpuInUse += p.getRequiredCPU();
            this.memoryInUse += p.getRequiredMemory();
            p.setPodStatus(PodStatus.RUNNING);
        }
    }

    public void deletePod(Pod p) {
        if(pods.remove(p)) {
            this.cpuInUse -= p.getRequiredCPU();
            this.memoryInUse -= p.getRequiredMemory();
            p.setPodStatus(PodStatus.STOPPED);
        }
    }

    public void deleteAllPods() {
        pods.forEach(this::deletePod);
        pods.clear();
    }

    public boolean hasPod(Pod pod) {
        for (Pod p : this.pods) {
            if (p.equals(pod)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnoughMemory(Pod p) {
        return p.getRequiredMemory() <= this.totalMemory - this.memoryInUse;
    }

    public boolean hasEnoughCPU(Pod p) {
        return p.getRequiredCPU() <= this.totalCPU - this.cpuInUse;
    }

    public boolean isLatencyAcceptable(Pod p) {
        return p.getMaximumLatency() >= this.latency;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Node node = (Node) object;
        return Objects.equals(uuid, node.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        long numOfPods = pods.size();
        int emptySize = 20 - this.getName().length();
        return this.getName() + " ".repeat(Math.max(0, emptySize))
                + this.getNodeType().toString() + " ".repeat(3)
                + this.getTotalCPU() + " ".repeat(3)
                + this.getTotalMemory() + " ".repeat(5)
                + (this.getTotalCPU() - this.getCpuInUse()) + " ".repeat(5)
                + (this.getTotalMemory() - this.getMemoryInUse()) + " ".repeat(5)
                + this.getLatency() + " ".repeat(8)
                + numOfPods;
    }
}
