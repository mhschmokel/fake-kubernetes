package k8;

import java.util.HashSet;
import java.util.Set;

public class DefaultScheduler implements Scheduler{
    @Override
    public void schedule(Set<Node> nodes, Pod p) {
        Set<Node> availableNodes = new HashSet<Node>(nodes);
        Worker worker = getMostAvailableWorker(availableNodes);

        while(!availableNodes.isEmpty()) {
            if (hasEnoughResources(worker, p)) {
                worker.addPod(p);
                break;
            } else {
                availableNodes.remove(worker);
            }
        }
    }

    private boolean hasEnoughResources(Node node, Pod p) {
        Worker worker = (Worker) node;

        return worker.hasEnoughCPU(p) && worker.hasEnoughMemory(p);
    }

    private Worker getMostAvailableWorker(Set<Node> nodes) {
        double maxFreeCPUPercentage = Double.MAX_VALUE;
        double maxFreeMemoryPercentage = Double.MAX_VALUE;
        Worker selectedWorker = null;

        for (Node node : nodes) {
            Worker w = (Worker)node;
            double cpuPercentage;
            double memoryPercentage;
            cpuPercentage = w.getAvailableCPU() / w.getTotalCPU();
            memoryPercentage = w.getAvailableMemory() / w.getTotalMemory();

            if (cpuPercentage < maxFreeCPUPercentage || memoryPercentage < maxFreeMemoryPercentage) {
                maxFreeCPUPercentage = cpuPercentage;
                maxFreeMemoryPercentage = memoryPercentage;
                selectedWorker = w;
            }
        }

        return selectedWorker;
    }
}
