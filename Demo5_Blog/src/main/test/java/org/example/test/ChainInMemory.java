package org.example.test;

public class ChainInMemory {

    private static ChainNode FIRST = new ChainNode("1");
    private static ChainNode SECOND = new ChainNode("2");

    private static class ChainNode{
        private ChainNode next;
        private String name;

        public ChainNode(String name) {
            this.name = name;
        }
    }

    public static void change(ChainNode node){
        node.next = FIRST;
        node = SECOND;
    }

    public static void main(String[] args) {
        ChainNode node = new ChainNode("3");
        change(node);
        System.out.println(node.name);//3, 不是2
    }
}
