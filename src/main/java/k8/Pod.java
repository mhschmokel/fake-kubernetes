package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@EqualsAndHashCode
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
    public String toString() {
        return this.getName() + "     "
        + this.getRequiredCPU() + "     "
        + this.getRequiredMemory() + "     "
        + this.getMaximumLatency() + "     "
        + this.getPodStatus();
    }
}
