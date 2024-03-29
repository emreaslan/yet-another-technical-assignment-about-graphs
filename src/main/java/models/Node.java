package models;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    private int id;
    private Set<Node> neighbours = new HashSet<>();;
    private Set<Integer> mergedNodes = new HashSet<>();

    public Node(int id){
        this.id = id;
    }

    public void merge(Node node){
        this.mergedNodes.add(node.id);
        this.neighbours.remove(node.id);
        node.neighbours.stream()
                .filter(n-> n.id != this.id)
                .forEach(n->{
                    this.neighbours.add(n);
                    n.neighbours.add(this);
                });
        node.mergedNodes.forEach(n-> this.mergedNodes.add(n));
        node.clearAllNeighbours();
    }

    public int getDegree(){
        return neighbours.size();
    }

    public Set<Integer> getMergedNodes() {
        return mergedNodes;
    }

    public void clearAllNeighbours(){
        neighbours.stream().forEach(n -> n.neighbours.remove(this));
        neighbours.clear();
    }

    public void addNeighbour(Node neighbour){
        if (!neighbour.equals(this))
            neighbours.add(neighbour);
    }

    public int getId() {
        return id;
    }

    public Set<Node> getNeighbours() {
        return neighbours;
    }

    @Override
    public String toString() {
        return "{id = " + id + ", merged = "
                + Arrays.toString(mergedNodes.stream().mapToInt(Integer::intValue).sorted().toArray())
                + ", neighbours = "
                + Arrays.toString(neighbours.stream().mapToInt(node -> node.getId()).sorted().toArray())
                + ", degree = "+ getDegree() + " }";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return id == node.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
