package myHash;

import java.util.Objects;

class TNode{
    public String key;
    public String val;
    public TNode next;


    public TNode(String key, String val) {
        this.key = key;
        this.val = val;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TNode tNode = (TNode) o;
        return Objects.equals(key, tNode.key) &&
                Objects.equals(val, tNode.val) &&
                Objects.equals(next, tNode.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, val, next);
    }
}


public class MyHashMap {
    public TNode[] array = new TNode[101];
    public int size;

    public boolean put(String key, String val) {
        int hasValues = key.hashCode();
        int index = hasValues/array.length;

        TNode tnode = array[index];
        if (tnode == null) {
            array[index] = new TNode(key, val);
            size++;
            return true;
        }

        TNode cur = new TNode(key, val);
        if (contains(cur)) {
            return false;
        }

        cur.next = array[index];
        array[index] = cur;
        return true;

    }

    public boolean contains(TNode tNode) {
        int hasValues = tNode.key.hashCode();
        int index = hasValues/array.length;

        TNode cur = array[index];
        if (cur == null) {
            return false;
        }

        while (cur != null) {
            if (cur.key.equals(tNode.key)) {
                return true;
            }
            cur = cur.next;
        }

        return false;
    }

    public boolean remove(TNode tNode) {
        int hasValues = tNode.key.hashCode();
        int index = hasValues/array.length;

        TNode cur = array[index];
        if (cur == null) {
            return false;
        }

        TNode prev = null;//设置一个前驱节点
        while (cur != null) {
            if (cur.key.equals(tNode.key)) {
                if (prev == null) {
                    //头节点是要移除的节点
                    array[index] = array[index].next;
                } else {
                    prev.next = cur.next;
                }
                size--;
                return true;
            }
            prev = cur;
            cur = cur.next;
        }
        return false;
    }
    
}
