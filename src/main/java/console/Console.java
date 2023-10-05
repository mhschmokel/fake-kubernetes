package console;

import k8.Cluster;
import k8.Node;
import k8.Pod;
import k8.Worker;
import main.Main;

import java.util.HashSet;
import java.util.Set;

import java.util.Scanner;

public class Console extends Thread {
    private final ProcessManager processManager;

    public Console() {
        processManager = new ProcessManager();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                Main.currentK8.exit();
                break;
            }
            processCommand(command);
        }
        scanner.close();
    }

    private void processCommand(String command) {
        String[] tokens = command.split(" ");
        switch (tokens[0]) {
            case "create":
                processManager.create(tokens);
                break;
            case "add":
                processManager.add(tokens);
                break;
            case "delete":
                processManager.delete(tokens);
                break;
            case "get":
                processManager.get(tokens);
                break;
            default:
                System.out.println("Invalid command.");
        }
    }
}


//public class Console {
//    public void displayAllNodes(Cluster cluster) {
//        Set<Node> nodes = cluster.getControlPlane().getNodes();
//
//        System.out.println("===== NODES =====");
//        System.out.println("NODE ===== TOTAL CPU = TOTAL MEMORY = AVAILABLE CPU = AVAILABLE MEMORY = LATENCY = RUNNING PODS");
//        for (Node n : nodes) {
//            Worker w = (Worker)n;
//            System.out.println(w.toString());
//        }
//    }
//
//    public void displayAllPods(Cluster cluster) {
//        Set<Node> nodes = cluster.getControlPlane().getNodes();
//        Set<Pod> pods = new HashSet<>();
//
//        System.out.println("===== PODS =====");
//        System.out.println("PODS ===== REQUIRED CPU = REQUIRED MEMORY = LATENCY = STATUS");
//
//        for (Node n : nodes) {
//            Worker w = (Worker)n;
//
//            for (Pod p : w.getPods()) {
//                System.out.println(p.toString());
//            }
//        }
//
//
//    }
//}
