package main;

import console.Console;
import k8.K8;

public class Main {
    public static K8 currentK8 = new K8("FakeKubernetes");
    public static void main(String[] args) {
        Console console = new Console();
        console.start();

        currentK8.start();
    }
}