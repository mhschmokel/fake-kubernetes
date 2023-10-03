package k8;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class Pod {
    final private UUID uuid = UUID.randomUUID();

    @NonNull
    private String name;

    @NonNull
    private double requiredCPU;

    @NonNull
    private double requiredMemory;

    @NonNull
    private double maximumLatency;

    @NonNull
    private Container container;

    @Setter
    private PodStatus podStatus;

    public Pod(String name, double requiredCPU, double requiredMemory, double maximumLatency, Container container, PodStatus podStatus) {
        this.name = name;
        this.requiredCPU = requiredCPU;
        this.requiredMemory = requiredMemory;
        this.maximumLatency = maximumLatency;
        this.container = container;
        this.podStatus = podStatus;
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
