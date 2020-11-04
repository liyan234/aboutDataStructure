package myHash;

import java.util.Objects;

class Person {
    public String sex;
    public String name;

    public Person(String sex, String name) {
        this.sex = sex;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(sex, person.sex) &&
                Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sex, name);
    }
}

class Node{
    public Person person;
    public Node next;

    public Node(Person person) {
        this.person = person;
    }

    public Node(String key, String val) {

    }
}

public class MyHashSet {

    public Node[] array = new Node[101];
    public int size = 0;
    // hash表的四步走
    // 将不是整形的类用hashcode函数转化成整形值  这个类都要重写equals 方法和 hashCode方法
    // 用hash函数查找这个数 应该放置的位置
    // 将其放入hash表中

    //查找
    public boolean contains(Person person) {
        int haveValues = person.hashCode();
        int index = haveValues / array.length;

        Node cur = array[index];
        if (cur == null) {
            return false;
        }

        while (cur != null) {
            if (cur.person.equals(person)) {
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // 插入
    public boolean put(Person person) {
        int haveValues = person.hashCode();
        int index = haveValues / array.length;

        Node head = array[index];

        if (head == null) {
            array[index] = new Node(person);
            size++;
        }

        //先判断在这个hash表中是否有相同的
        while (head != null) {
            if (head.person.equals(person)) {
                return false;
            }
            head = head.next;
        }

        //头插法插入
        Node cur = array[index];

        Node newNode = new Node(person);
        newNode.next = cur;
        array[index] = newNode;
        size++;

        if (size == array.length) {
            changeBig();
        }

        return true;
    }

    //移除
    public boolean remove(Person person) {
        int haveValues = person.hashCode();
        int index = haveValues / array.length;

        Node prev = null;
        Node cur = array[index];

        while (cur != null) {

            if (cur.person.equals(person)) {
                if (prev == null) {
                    //头节点就是要被移除的节点
                    array[index] = array[index].next;
                } else {
                    //移除当找那个的节点
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

    //扩容
    private void changeBig(){
        Node[] newArray = new Node[array.length * 2];

        // 将原来的的元素搬过来
        //遍历数组和链表 然后头插元素

        for (int i = 0; i < array.length; i++) {
            Node cur = array[i];
            while (cur != null) {
                //头插新的元素
                Person person = cur.person;
                int hasValues = person.hashCode();
                int index = hasValues / newArray.length;

                Node node = new Node(person);
                node.next = newArray[index];
                newArray[index] = node;

                cur = cur.next;
            }
        }

    }

}
