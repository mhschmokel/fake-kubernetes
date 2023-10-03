package k8;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
@Getter
public class Cluster {
    @NonNull
    private String name;

    @NonNull
    private ControlPlane controlPlane;

    public void schedulePods(Set<Pod> pods) {
        for (Pod p : pods) {
            controlPlane.schedulePod(p);
        }
    }

    public void addNewWorkerNode(String nodeName, double totalCPU, double totalMemory) {
        Worker worker = new Worker(nodeName, totalCPU, totalMemory);
        controlPlane.addWorker(worker);
    }

    public void addNewWorkerNode(String nodeName, double totalCPU, double totalMemory, double latency) {
        Worker worker = new Worker(nodeName, totalCPU, totalMemory, latency);
        controlPlane.addWorker(worker);
    }

    public void deletePod(Pod pod) {
        controlPlane.deletePod(pod);
    }

    public void deleteAllPods() {
        controlPlane.deleteAllPods();
    }
}
