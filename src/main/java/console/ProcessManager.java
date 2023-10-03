package console;

import util.ArgumentParser;

import java.util.Map;

public class ProcessManager {

    public void create(String[] args) {
        // Assumed format: create cluster -n name
        if (args.length >= 4) {
            Map<String, String> argMap = ArgumentParser.parse(args, 2);
            String name = argMap.get("-n");
            if (name != null) {
                // Create a cluster with the specified name
                // ...
                System.out.println("Cluster " + name + " created.");
            } else {
                System.out.println("Missing -n argument for cluster name.");
            }
        } else {
            System.out.println("Invalid create command format.");
        }
    }

    public void add(String[] args) {
        if (args.length >= 10) {
            Map<String, String> argMap = ArgumentParser.parse(args, 2);
            String cpu = argMap.get("-cpu");
            String mem = argMap.get("-mem");
            String lat = argMap.get("-lat");
            String name = argMap.get("-n");
            if (cpu != null && mem != null && lat != null && name != null) {
                // Add a worker with the specified arguments
                // ...
                System.out.println("Worker " + name + " added.");
            } else {
                System.out.println("Missing some arguments.");
            }
        } else {
            System.out.println("Invalid add command format.");
        }
    }

    // Continue with delete and get methods...
}
