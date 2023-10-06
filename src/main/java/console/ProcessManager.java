package console;

import k8.Container;
import k8.CustomScheduler;
import k8.K8;
import k8.Node;
import k8.Pod;
import main.Main;
import util.ArgumentParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ProcessManager {
    public void create(String[] args) {
        Map<String, String> argMap = ArgumentParser.parse(args, 2);
        switch (ArgumentParser.parseCommand(args)) {
            case "cluster": // Create cluster -n name
                String name = argMap.get("-n");
                String scheduler = argMap.get("-s");
                if (!this.hasMissingArgs(name)) {
                    if (Main.currentK8.hasCluster()) {
                        System.out.println("A cluster already exists");
                        return;
                    }
                    if (scheduler != null) {
                        Main.currentK8.createCluster(name, new CustomScheduler());
                        System.out.println("Cluster " + name + " created.");
                    } else {
                        Main.currentK8.createCluster(name);
                        System.out.println("Cluster " + name + " created.");
                    }
                }
                break;
            default:
                System.out.println("Invalid create command format");
        }
    }

    public void add(String[] args) {
        Map<String, String> argMap = ArgumentParser.parse(args, 2);
        String cpu = argMap.get("-cpu");
        String mem = argMap.get("-mem");
        String lat = argMap.get("-lat");
        String name = argMap.get("-n");

        double maximumLatency = Double.parseDouble(lat != null ? lat : "0");
        switch (ArgumentParser.parseCommand(args)) {
            case "worker": //add worker -n worker1 -cpu 2 -mem 2048 -lat 5
                if (!this.hasMissingArgs(name, cpu, mem)) {
                    Main.currentK8.createWorkerNode(
                            name,
                            Double.parseDouble(cpu),
                            Double.parseDouble(mem),
                            maximumLatency);
                    System.out.println("Worker " + name + " added.");
                }
                break;
            case "pod": //add pod -n pod1 -cpu 2 -mem 2048 -lat 5
                if (!this.hasMissingArgs(name, cpu, mem)) {
                    Pod p = new Pod(
                            name,
                            Double.parseDouble(cpu),
                            Double.parseDouble(mem),
                            maximumLatency,
                            new Container());
                    Main.currentK8.schedulePod(p);
                    System.out.println("Pod " + name + " added.");
                }
                break;
            default:
                System.out.println("Invalid add command format");
        }
    }

    public void delete(String[] args) {
        Map<String, String> argMap = ArgumentParser.parse(args, 2);
        String name = argMap.get("-n");

        switch (ArgumentParser.parseCommand(args)) {
            case "pod": //delete pod -n pod1
                if (!this.hasMissingArgs(name)) {
                    Main.currentK8.deletePodByName(name);
                    System.out.println("Pod " + name + " deleted.");
                }
                break;
            case "node": //delete node -n node1
                if (!this.hasMissingArgs(name)) {
                    Main.currentK8.deleteNodeByName(name);
                    System.out.println("Node " + name + " deleted.");
                }
                break;
            case "cluster":
                Main.currentK8.deleteCluster();
                System.out.println("Cluster deleted");
                break;
            default:
                System.out.println("Invalid delete command format");
        }
    }

    public void get(String[] args) {
        switch (ArgumentParser.parseCommand(args)) {
            case "nodes": //get nodes
                displayNodes(Main.currentK8.getNodes());
                break;
            case "pods": //get pods
                displayPods(Main.currentK8.getPods());
                break;
            default:
                System.out.println("Invalid add command format");
        }
    }

    public void run(String[] args) {
        Map<String, String> argMap = ArgumentParser.parse(args, 2);
        switch (ArgumentParser.parseCommand(args)) {
            case "scenario1":
                if(!Main.currentK8.hasCluster()) {
                    System.out.println("No cluster available");
                    return;
                }
                addWorkersScenario1();
                addPodsScenario1();
                break;
            case "scenario2":
                addWorkersScenario2();
                addPodsScenario2();
                break;
            default:
                System.out.println("Invalid run command format");
                break;
        }
    }

    public void addWorkersScenario1() {
        Main.currentK8.createWorkerNode("worker1", 16, 7650, 5);
        Main.currentK8.createWorkerNode("worker2", 16, 7650, 10);
        System.out.println("Added Workers");
    }

    public void addWorkersScenario2() {
        Main.currentK8.createWorkerNode("worker1", 16, 4000, 5);
        Main.currentK8.createWorkerNode("worker2", 16, 4000, 10);
        System.out.println("Added Workers");
    }

    public void addPodsScenario2() {
        List<Pod> pods = new ArrayList<>();
        Container c = new Container();

        pods.add(new Pod("pod1", 1, 1000, 2, c));
        pods.add(new Pod("pod2", 1, 1000, 5, c));
        pods.add(new Pod("pod3", 1, 1000, 6, c));
        pods.add(new Pod("pod4", 1, 1000, 7, c));
        pods.add(new Pod("pod5", 1, 1000, 8, c));
        pods.add(new Pod("pod6", 1, 1000, 10, c));
        pods.add(new Pod("pod7", 1, 1000, 11, c));
        pods.add(new Pod("pod8", 1, 1000, 12, c));
        pods.add(new Pod("pod9", 1, 1000, 13, c));

        Main.currentK8.schedulePods(pods);

        System.out.println("Added Pods");
    }

    public void addPodsScenario1() {
        List<Pod> pods = new ArrayList<>();
        Container c = new Container();

        pods.add(new Pod("pod1", 1, 50, 20, c));
        pods.add(new Pod("pod2", 1, 100, 20, c));
        pods.add(new Pod("pod3", 1, 150, 20, c));
        pods.add(new Pod("pod4", 1, 200, 20, c));
        pods.add(new Pod("pod5", 1, 250, 20, c));
        pods.add(new Pod("pod6", 1, 300, 20, c));
        pods.add(new Pod("pod7", 1, 350, 20, c));
        pods.add(new Pod("pod8", 1, 400, 20, c));
        pods.add(new Pod("pod9", 1, 450, 20, c));
        pods.add(new Pod("pod10", 1, 500, 20, c));
        pods.add(new Pod("pod11", 1, 550, 20, c));
        pods.add(new Pod("pod12", 1, 600, 20, c));
        pods.add(new Pod("pod13", 1, 650, 20, c));
        pods.add(new Pod("pod14", 1, 700, 20, c));
        pods.add(new Pod("pod15", 1, 750, 20, c));
        pods.add(new Pod("pod16", 1, 800, 20, c));
        pods.add(new Pod("pod17", 1, 850, 20, c));
        pods.add(new Pod("pod18", 1, 900, 20, c));
        pods.add(new Pod("pod19", 1, 950, 20, c));
        pods.add(new Pod("pod20", 1, 1000, 20, c));
        pods.add(new Pod("pod21", 1, 1000, 20, c));
        pods.add(new Pod("pod22", 1, 1000, 20, c));
        pods.add(new Pod("pod23", 1, 1000, 20, c));
        pods.add(new Pod("pod24", 1, 1000, 20, c));
        pods.add(new Pod("pod25", 1, 1000, 20, c));
        pods.add(new Pod("pod26", 1, 1000, 20, c));
        pods.add(new Pod("pod27", 1, 1000, 20, c));

        Main.currentK8.schedulePods(pods);

        System.out.println("Added Pods");
    }

    private void displayNodes(Set<Node> nodes) {
        int emptySize = 20 - "NODE".length();
        String empty = " ".repeat(Math.max(0, emptySize));
        System.out.println("NODE" + empty + "TYPE     CPU   MEMORY   AV. CPU   AV. MEM   LATENCY   NUM. PODS");
        for (Node n : nodes) {
            System.out.println(n.toString());
        }
    }

    private void displayPods(List<Pod> pods) {
        int emptySize = 20 - "POD".length();
        String empty = " ".repeat(Math.max(0, emptySize));
        System.out.println("POD" + empty + "CPU = MEMORY = LATENCY = STATUS    = WORKER");

        List<Pod> podsList = new ArrayList<Pod>(pods);
        podsList.sort(Comparator.comparing(Pod::getPodStatus));

        for (Pod p : podsList) {
            System.out.println(p.toString() + "     " + Main.currentK8.getWorkerByPodName(p));
        }
    }

    private boolean hasMissingArgs(String... strings) {
        for (String s : strings) {
            if (s == null) {
                System.out.println("Some arguments are missing");
                return true;
            }
        }
        return false;
    }
}
