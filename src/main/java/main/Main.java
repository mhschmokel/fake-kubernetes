package main;

import console.Console;
import k8.Container;
import k8.DefaultScheduler;
import k8.K8;
import k8.Pod;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static K8 currentK8 = new K8("FakeKubernetes");
    public static void main(String[] args) {
        Console console = new Console();
        console.start();

        currentK8.start();
//        currentK8.createCluster("c1", new DefaultScheduler());
//        currentK8.createWorkerNode("worker1", 2, 2048, 30);
//
//        Pod p1 = new Pod("pod1", 0.5, 1548, new Container());
//        Pod p2 = new Pod("pod2", 1.0, 500, new Container());
//        Pod p3 = new Pod("pod3", 1.0, 1000, new Container());
//
//        currentK8.schedulePod(p1);
//        currentK8.schedulePod(p2);
//        currentK8.schedulePod(p3);
    }
}