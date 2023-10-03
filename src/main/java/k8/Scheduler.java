package k8;

import java.util.Set;

public interface Scheduler {
    public void schedule(Set<Node> nodes, Pod p);
}
