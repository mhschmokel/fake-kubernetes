package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.UUID;

@Getter
//@EqualsAndHashCode
public class Pod {
    final private UUID uuid = UUID.randomUUID();
    final private String name;
    final private double requiredCPU;
    final private double requiredMemory;
    final private double maximumLatency;
    final private Container container;

    @Setter
    private PodStatus podStatus;

    public Pod(String name, double requiredCPU, double requiredMemory, Container container) {
        this.name = name;
        this.requiredCPU = requiredCPU;
        this.requiredMemory = requiredMemory;
        this.maximumLatency = 0;
        this.container = container;
        this.podStatus = PodStatus.READY;
    }

    public Pod(String name, double requiredCPU, double requiredMemory, double maximumLatency, Container container) {
        this.name = name;
        this.requiredCPU = requiredCPU;
        this.requiredMemory = requiredMemory;
        this.maximumLatency = maximumLatency;
        this.container = container;
        this.podStatus = PodStatus.READY;
    }

    public void run() {
        container.runContainer();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Pod pod = (Pod) object;
        return Objects.equals(uuid, pod.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        int emptySize = 20 - this.getName().length();
        return this.getName() + " ".repeat(Math.max(0, emptySize))
        + this.getRequiredCPU() + " ".repeat(3)
        + this.getRequiredMemory() + "     "
        + this.getMaximumLatency() + " ".repeat(6)
        + this.getPodStatus();
    }
}
