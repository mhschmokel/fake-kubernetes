package k8;

import java.util.Set;

public class CustomScheduler implements Scheduler {

    @Override
    public void schedule(Set<? extends Node> nodes, Pod p) {
//        Worker worker = null;
//
//        for (Node n : nodes) {
//            worker = (Worker) n;
//            break;
//        }
//
//        worker.addPod(p);
    }
}
