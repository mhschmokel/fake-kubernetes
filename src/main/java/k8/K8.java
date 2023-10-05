package k8;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class K8 extends Thread {
    @Getter
    final private UUID uuid = UUID.randomUUID();
    final private String name;
    private Cluster cluster;

    private boolean initScheduler = false;

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
        this.initScheduler = true;
    }

    @Override
    public void run() {
        while(!initScheduler){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception exception) {
                System.out.println(exception.toString());
            }
        }
        this.cluster.runScheduler();
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
        return this.cluster.getPods();
    }

    public void deletePodByName(String name) {
        this.cluster.deletePodByName(name);
    }

    public void deleteNodeByName(String name) {
        this.cluster.deleteNodeByName(name);
    }

    public String getWorkerByPodName(Pod p) {
        Node node = this.cluster.getWorkerByPod(p);
        return node != null ? node.getName() : "-";
    }

    public void exit() {
        this.cluster.stopScheduler();
    }

    public void deleteCluster() {
        this.cluster.deleteWorkerNodes();
        this.cluster = null;
    }
}
