package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@EqualsAndHashCode(callSuper = false)
public class Worker extends Node {
    final private UUID uuid = UUID.randomUUID();
    private String name;
    private double totalCPU;
    private double totalMemory;
    private double availableCPU;
    private double availableMemory;
    private double latency;
    final private Set<Pod> pods = new HashSet<Pod>();

    public Worker() {
        super(NodeType.WORKER);
    }

    public Worker(String name, double totalCPU, double totalMemory) {
        super(NodeType.WORKER);
        this.name = name;
        this.totalCPU = totalCPU;
        this.totalMemory = totalMemory;
        this.availableCPU = this.totalCPU;
        this.availableMemory = this.totalMemory;
        this.latency = 0;
    }

    public Worker(String name, double totalCPU, double totalMemory, double latency) {
        super(NodeType.WORKER);
        this.name = name;
        this.totalCPU = totalCPU;
        this.totalMemory = totalMemory;
        this.availableCPU = this.totalCPU;
        this.availableMemory = this.totalMemory;
        this.latency = latency;
    }

    public void addPod(Pod p) {
        pods.add(p);
        p.setPodStatus(PodStatus.RUNNING);
        this.availableCPU -= p.getRequiredCPU();
        this.availableMemory -= p.getRequiredMemory();
    }

    public void deletePod(Pod p) {
        if (pods.remove(p)) {
            p.setPodStatus(PodStatus.STOPPED);
            this.availableCPU += p.getRequiredCPU();
            this.availableMemory += p.getRequiredMemory();
        }
    }

    public void deleteAllPods() {
        pods.forEach(this::deletePod);
        pods.clear();
    }

    public Pod getPodByUuid(UUID uuid) {
        return pods.stream()
                .filter((Pod p) -> p.getUuid() == uuid)
                .findFirst().get();
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

        return this.getName() + "       "
                + this.getTotalCPU() + "       "
                + this.getTotalMemory() + "            "
                + this.getAvailableCPU() + "              "
                + this.getAvailableMemory() + "          "
                + this.getLatency() + "         "
                + numOfPods;
    }
}
