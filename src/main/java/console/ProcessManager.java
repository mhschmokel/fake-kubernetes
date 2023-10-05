package console;

import k8.Container;
import k8.CustomScheduler;
import k8.K8;
import k8.Node;
import k8.Pod;
import main.Main;
import util.ArgumentParser;

import java.util.Map;
import java.util.Set;

public class ProcessManager {
    public void create(String[] args) {
        Map<String, String> argMap = ArgumentParser.parse(args, 2);
        switch (ArgumentParser.parseCommand(args)) {
            case "cluster": // Create cluster -n name
                String name = argMap.get("-n");
                String scheduler = argMap.get("-s");
                if (!this.hasMissingArgs(name)) {
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

    private void displayNodes(Set<Node> nodes) {
        int emptySize = 20 - "NODE".length();
        String empty = " ".repeat(Math.max(0, emptySize));
        System.out.println("NODE" + empty + "TYPE     CPU   MEMORY   AV. CPU   AV. MEM   LATENCY   NUM. PODS");
        for (Node n : nodes) {
            System.out.println(n.toString());
        }
    }

    private void displayPods(Set<Pod> pods) {
        int emptySize = 20 - "POD".length();
        String empty = " ".repeat(Math.max(0, emptySize));
        System.out.println("POD" + empty + "CPU = MEMORY = LATENCY = STATUS    = WORKER");
        for (Pod p : pods) {
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
