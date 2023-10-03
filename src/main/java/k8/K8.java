package k8;

import java.util.HashSet;
import java.util.Set;

public class K8 {
    private Cluster cluster;

    public void createCluster(String name) {
        this.createCluster(name, new DefaultScheduler());
    }

    public void createCluster(String name, Scheduler scheduler) {
        if (cluster != null) {
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

    public Set<Worker> getWorkerNodes() {
        return this.cluster.getWorkerNodes();
    }

    public Set<Pod> getPods() {
        Set<Pod> pods = new HashSet<Pod>();
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
