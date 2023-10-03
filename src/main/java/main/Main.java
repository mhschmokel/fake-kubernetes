package main;

import console.Console;
import k8.DefaultScheduler;
import k8.K8;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static Set<K8> k8Instances = new HashSet<>();
    public static K8 currentK8;
    public static void main(String[] args) {
        Console console = new Console();
        console.start();

        Main.currentK8 = new K8("c1");
        currentK8.createCluster("c1", new DefaultScheduler());
        currentK8.createWorkerNode("worker1", 2, 2048, 30);


//        Console console = new Console();
//        Scheduler roundRobinScheduler = new RoundRobinScheduler();
//        ControlPlane controlPlane = new ControlPlane(roundRobinScheduler);
//        Cluster c1 = new Cluster("C1", controlPlane);
//
//        c1.addNewWorkerNode("Worker 1", 1, 1024);
//        c1.addNewWorkerNode("Worker 2", 1, 1024);
//
//        Set<Pod> pods = new HashSet<>();
//        Pod p1 = new Pod("Pod 1", 0.1, 124.0, 30, new Container(), PodStatus.READY);
//        pods.add(p1);
//        c1.schedulePods(pods);
//
//        console.displayAllNodes(c1);
//        console.displayAllPods(c1);
    }

    public K8 getK8ByName(String name) {
        K8 k8 = null;
        for (K8 k : k8Instances) {
            if (k.getName().equals(name)) {
                k8 = k;
                break;
            }
        }
        return k8;
    }
}