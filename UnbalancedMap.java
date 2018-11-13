/*
    Neel Kumar & ALyssa Gonzales
    Program 2
 */
package edu.sdsu.cs;

import java.util.LinkedList;

public class UnbalancedMap <K extends Comparable<K>,V> implements IMap<K,V> {

    Node root = null;
    public int currSize = 0;
    LinkedList<K> all = new LinkedList<>();

    public UnbalancedMap() {
    }

    public UnbalancedMap(IMap<K, V> source) {
        for (K key : source.keyset())
            add(key, source.getValue(key));
    }

    private class Node {

        LinkedList<K> allKeys = new LinkedList<>();
        LinkedList<V> allValues = new LinkedList<>();



        public Node right, left;
        public K key;
        public V val;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K smallest() {
            if (left == null) {
                return left.smallest();
            } else {
                return key;
            }

        }

        public K largest() {
            if (right == null) {
                return right.largest();
            } else {
                return key;
            }
        }


        public boolean add(K k, V v) {
            int comparison = key.compareTo(k);
            if ( comparison ==  0) {
                return false;
            }
            else if( comparison > 0) {
                if(left == null){
                    left = new Node(k,v);
                    currSize++;
                    return true;
                }
                else{
                    return left.add(k, v);
                }
            }
            else if(comparison < 0) {
                if(right == null){
                    currSize++;
                    right = new Node(k, v);
                    return true;
                }
                else{
                    return right.add(k, v);
                }
            }
            return false;
        }

        public V getValue(K key) {
            int comparison = key.compareTo(this.key);
            if (comparison < 0) {
                if (left == null) {
                    return null;
                } else return left.getValue(key);
            } else if (comparison > 0) {
                if (right == null) {
                    return null;
                } else return right.getValue(key);
            } else return val;
        }


        public K getKey(V value) {
            K k = null;
            if (root != null) {
                if ( value ==(getValue(key))){
                    k = key;
                } else if (left != null) {
                    return left.getKey(value);
                } else if (right != null) {
                    return right.getKey(value);
                }

            }
            return k;
        }

        public Iterable<K> getKeys(Node current, V value){
            if(current!= null){
                if(getValue(current.key) == value)
                    all.add(current.key);
                getKeys(current.left,value);
                getKeys(current.right,value);
            }
            return all;
        }



        public boolean contains(K k){
            if(key == null){
                return false;
            }
            int compare = k.compareTo(key);
            if(compare == 0){
                return true;
            }else if(compare < 0 && left != null){
                return left.contains(k);
            }else if(compare > 0 && right != null){
                return right.contains(k);
            }
            return false;
        }
        public Iterable<K> keyset(Node current){

            if(current!= null){
                keyset(current.left);
                allKeys.add(current.key);
                keyset(current.right);
            }

            return allKeys;
        }
        public Iterable<V> values(Node current){
            if(current!= null){
                values(current.left);
                allValues.add(current.val);
                values(current.right);
            }
            return allValues;
        }

        public Node findSuccessor(Node current, K k) {
            Node pre = null;
            if (current != null) {
                if (current.key == k) {
                    if (current.left != null) {
                        Node temp = current.left;
                        while (temp.right != null) {
                            temp = temp.right;
                        }
                        pre = temp;
                        return pre;
                    }
                    if (current.right != null) {

                        Node temp = current.right;
                        while (temp.left != null) {
                            temp = temp.left;
                        }
                        pre = temp;
                        return pre;
                    }
                } else if (current.key.compareTo(k) > 0) {
                    pre = current;
                    return findSuccessor(current.left, k);
                } else if (current.key.compareTo(k) < 0) {
                    pre = current;
                    return findSuccessor(current.right, k);
                }
            }
            return pre;
        }

        public Node findNode(Node current, K k){

            while(current != null) {
                if (current.key.compareTo(k) == 0) {
                    return current;
                }
                if (current.key.compareTo(k) > 0) {
                    return findNode(current.left, k);
                }
                if (current.key.compareTo(k) < 0) {
                    return findNode(current.right, k);
                }
            }
            return current;
        }

        public V delete( K k) {
            K childKey;
            V childV;
            V value = getValue(k);

            if ((root.left == null) && (root.right == null)){
                currSize--;
                root = null;
                return value;
            }

            else if( root.key != k) {
                Node current = findNode(root, k);

                Node successor = findSuccessor(current, k);
                current.key = successor.key;
                current.val = successor.val;
                successor = null;
                currSize--;
                return value;
            }

            //one child root
            else if (root.left != null) {
                if(root.left.right != null) {
                    Node successor = findSuccessor(root, k);
                    root.key = successor.key;
                    root.val = successor.val;
                    successor = null;
                    currSize--;
                    return value;
                }
                else{
                    Node current = findNode(root, k);
                    Node leftChild = current.left.left;
                    root.left = null;
                    root.left = leftChild;
                    currSize--;
                    return value;
                }
            }
            else if(root.right != null){
                if(root.right.left != null) {
                    Node successor = findSuccessor(root, k);
                    root.key = successor.key;
                    root.val = successor.val;
                    successor = null;
                    currSize--;
                    return value;
                }
                else{
                    Node current = findNode(root, k);
                    Node rightChild = current.right.right;
                    root.right = null;
                    root.right = rightChild;
                    currSize--;
                    return value;
                }
            }
            return value;
        }
    }

    @Override
    public boolean contains(K key) {
        return root.contains(key);
    }


    @Override
    public boolean add(K key, V value) {
        if (root == null) {
            root = new Node(key, value);
            currSize++;
            return true;
        }else return root.add(key,value);
    }

    @Override
    public V delete(K key) {
        if( size() == 0) return null;
        return root.delete(key);
    }

    @Override
    public V getValue (K key){
        return root.getValue(key);
    }

    @Override
    public K getKey(V value) {
        K k = null;
        if(root == null)
        {
            return null;
        }
        k = root.getKey(value);
        if(k == null){
            k = root.right.getKey(value);
        }
        return k;
    }

    @Override
    public Iterable<K> getKeys( V value) {
        LinkedList<K> allKeys = new LinkedList<>();
        if (root == null) {
            return allKeys;
        } else {
            for (K key : root.getKeys(root,value)) {
                allKeys.add(key);
            }
            all.clear();
            return allKeys;
        }

    }

    @Override
    public int size() {
        return currSize;
    }

    @Override
    public boolean isEmpty() {
        return size()==0;
    }

    @Override
    public void clear () {
        root = null;
        currSize = 0;
    }

    @Override
    public Iterable<K> keyset() {
        LinkedList<K> allKeys = new LinkedList<>();
        if (root == null) {
            return allKeys;
        } else {
            for (K key : root.keyset(root)) {
                allKeys.add(key);
            }
            return allKeys;
        }
    }

    @Override
    public Iterable<V> values() {
        LinkedList<V> allKeys = new LinkedList<>();
        if (root == null) {
            return allKeys;
        } else {
            for (V value : root.values(root)) {
                allKeys.add(value);
            }
            return allKeys;
        }
    }

}






