package k8;

import java.util.Set;

public interface Scheduler {
    public void schedule(Set<? extends Node> nodes, Pod p);
}
