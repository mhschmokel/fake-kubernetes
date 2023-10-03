package k8;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
public class Cluster {
    final private UUID uuid = UUID.randomUUID();
    final private String name;
    final private Scheduler scheduler;
    final private Set<Node> nodes = new HashSet<Node>();
    final private Set<Pod> pods = new HashSet<>();

    public Cluster(String name, Scheduler scheduler) {
        this.name = name;
        this.scheduler = scheduler;
        nodes.add(new ControlPlane("Control plane", 1, 1024, 0));
    }

    public void schedulePods(Set<Pod> pods) {
        for (Pod p : pods) {
            schedulePod(p);
        }
    }

    public void schedulePod(Pod p) {
        pods.add(p);
        scheduler.schedule(getWorkerNodes(), p);
    }

    public Set<Worker> getWorkerNodes() {
        Set<Worker> list = new HashSet<Worker>();
        for (Node n : nodes) {
            if (n.getNodeType() == NodeType.WORKER) {
                list.add((Worker)n);
            }
        }
        return list;
    }



    public void addNewWorkerNode(String nodeName, double totalCPU, double totalMemory) {
        nodes.add(new Worker(nodeName, totalCPU, totalMemory));
    }

    public void addNewWorkerNode(String nodeName, double totalCPU, double totalMemory, double latency) {
        nodes.add(new Worker(nodeName, totalCPU, totalMemory, latency));
    }

    public void deletePod(Pod p) {
        for(Node n : nodes) {
            n.deletePod(p);
        }
    }

    public void deleteAllPods() {
        for(Node n : nodes) {
            n.deleteAllPods();
        }
    }

    public void deleteNode(Node n) {
        n.deleteAllPods();
        nodes.remove(n);
    }

    public void deleteWorkerNodes() {
        for (Node n : nodes) {
            if (n.getNodeType() == NodeType.WORKER) {
                deleteNode(n);
            }
        }
    }
}
