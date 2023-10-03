package br.unisinos;

import console.Console;

public class Main {

    public static void main(String[] args) {
        Console console = new Console();
        console.start();



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
}