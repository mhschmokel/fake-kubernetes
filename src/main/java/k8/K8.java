package k8;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class K8 {
    @Getter
    final private UUID uuid = UUID.randomUUID();

    @Getter
    final private String name;
    private Cluster cluster;

    public K8(String name) {
        this.name = name;
    }

    public void createCluster(String name) {
        this.createCluster(name, new DefaultScheduler());
    }

    public void createCluster(String name, Scheduler scheduler) {
        if (this.cluster != null) {
            System.out.println("A Cluster already exists");
            return;
        }

        cluster = new Cluster(name, scheduler);
    }

    public void schedulePods(Set<Pod> pods) {
        this.cluster.schedulePods(pods);
    }

    public void schedulePod(Pod p) {
        this.cluster.schedulePod(p);
    }

    public void createWorkerNode(String name, double totalCPU, double totalMemory) {
        cluster.addNewWorkerNode(name, totalCPU, totalMemory);
    }

    public void createWorkerNode(String name, double totalCPU, double totalMemory, double latency) {
        cluster.addNewWorkerNode(name, totalCPU, totalMemory, latency);
    }

    public Set<Worker> getWorkerNodes() {
        return this.cluster.getWorkerNodes();
    }

    public Set<Node> getNodes() {
        return this.cluster.getNodes();
    }

    public Set<Pod> getPods() {
        Set<Pod> pods = new HashSet<>();
        for (Node n : cluster.getWorkerNodes()) {
            pods.addAll(n.getPods());
        }
        return pods;
    }

    public void deleteCluster() {
        this.cluster.deleteWorkerNodes();
        this.cluster = null;
    }
}
