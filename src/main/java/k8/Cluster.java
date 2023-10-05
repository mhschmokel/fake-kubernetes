package k8;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Getter
public class Cluster {
    final private UUID uuid = UUID.randomUUID();
    final private String name;
    final private Scheduler scheduler;
    final private Set<Node> nodes = new HashSet<Node>();
    final private Set<Pod> pods = new HashSet<>();

    private boolean stopScheduler = false;

    public Cluster(String name, Scheduler scheduler) {
        this.name = name;
        this.scheduler = scheduler;
        nodes.add(new ControlPlane("Control plane", 1, 1024, 0));
    }

    public void runScheduler() {
        while(!stopScheduler) {
            this.schedulePods(getPendingPods());
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (Exception x) {
                System.out.println(x.toString());
            }
        }
    }

    private Set<Pod> getPendingPods() {
        return this.pods.stream()
                .filter(p -> p.getPodStatus() == PodStatus.PENDING)
                .collect(Collectors.toSet());
    }

    public void schedulePods(Set<Pod> pods) {
        for (Pod p : pods) {
            this.scheduler.schedule(getWorkerNodes(), p);
        }
    }

    public void schedulePod(Pod p) {
        pods.add(p);
        p.setPodStatus(PodStatus.PENDING);
        //scheduler.schedule(getWorkerNodes(), p);
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

    public void deletePodByName(String name) {
        this.deletePod(getPodByName(name));
    }

    public Pod getPodByName(String name) {
        return this.pods.stream()
                    .filter(p -> p.getName().equals(name))
                    .findFirst()
                    .get();
    }

    public void deleteNodeByName(String name) {
        this.deleteNode(getNodeByName(name));
    }

    public Node getNodeByName(String name) {
        return this.nodes.stream()
                .filter(n -> n.getName().equals(name))
                .findFirst()
                .get();
    }

    public void stopScheduler() {
        this.stopScheduler = true;
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

    public Node getWorkerByPod(Pod p) {
        Node node = null;
        for (Node n : this.nodes) {
            if (n.hasPod(p)) {
                node = n;
                break;
            }
        }
        return node;
    }

    public void deleteWorkerNodes() {
        for (Node n : nodes) {
            if (n.getNodeType() == NodeType.WORKER) {
                deleteNode(n);
            }
        }
    }
}
