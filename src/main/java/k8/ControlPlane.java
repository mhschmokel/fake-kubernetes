package k8;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public class ControlPlane extends Node {
    @Getter
    Set<Node> nodes = new HashSet<Node>();
    final Scheduler scheduler;
    public ControlPlane(Scheduler scheduler){
        super(NodeType.MASTER);
        this.scheduler = scheduler;
    }

    public void addWorker(Node node) {
        Worker worker = (Worker) node;
        nodes.add(worker);
    }

    public void schedulePod(Pod p) {
        scheduler.schedule(nodes, p);
    }

    public void deleteAllNodes() {
        deleteAllPods();
        nodes.clear();
    }

    public void deletePod(Pod pod) {
        nodes.forEach(node -> {
            Worker worker = (Worker) node;
            worker.deletePod(pod);
        });
    }

    public void deleteAllPods() {
        nodes.forEach((Node node) -> {
            Worker worker = (Worker)node;
            worker.deleteAllPods();
        });
    }
}