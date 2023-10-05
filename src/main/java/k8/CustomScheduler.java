package k8;

import java.util.HashSet;
import java.util.Set;

public class CustomScheduler implements Scheduler {

    @Override
    public void schedule(Set<? extends Node> nodes, Pod p) {
        Set<Node> availableNodes = new HashSet<Node>(nodes);
        Worker worker;

        while(!availableNodes.isEmpty()) {
            worker = getWorkerNode(availableNodes);
            if (hasEnoughResources(worker, p)) {
                worker.addPod(p);
                break;
            } else {
                availableNodes.remove(worker);
            }
        }
    }

    private Worker getWorkerNode(Set<Node> nodes) {
        return (Worker) nodes.toArray()[0];
    }

    private boolean hasEnoughResources(Node node, Pod p) {
        Worker worker = (Worker) node;

        return worker.hasEnoughCPU(p) && worker.hasEnoughMemory(p) && worker.isLatencyAcceptable(p);
    }
}
