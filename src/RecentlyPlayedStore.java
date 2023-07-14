

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

class Node {
    String song;
    String user;
    Node next;
    Node prev;

    Node(String song, String user) {
        this.song = song;
        this.user = user;
        this.next = null;
        this.prev = null;
    }
}

public class RecentlyPlayedStore {
    private int capacity;
    private Map<String, Node> songMap;
    private Node head;
    private Node tail;

    public RecentlyPlayedStore(int capacity) {
        this.capacity = capacity;
        this.songMap = new HashMap<>();
        this.head = null;
        this.tail = null;
    }

    public void addPlayedSong(String song, String user) {
        if (songMap.containsKey(song)) {
            Node node = songMap.get(song);
            moveToFront(node);
        } else {
            if (songMap.size() >= capacity) {
                removeLeastRecentlyPlayed();
            }

            Node newNode = new Node(song, user);

            if (head == null) {
                head = newNode;
                tail = newNode;
            } else {
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }

            songMap.put(song, newNode);
        }
    }

    public String[] getRecentlyPlayedSongs(String user) {
        List<String> recentlyPlayed = new ArrayList<>();
        Node current = head;

        while (current != null) {
            if (current.user.equals(user)) {
                recentlyPlayed.add(current.song);
            }
            current = current.next;
        }

        return recentlyPlayed.toArray(new String[0]);
    }

    private void moveToFront(Node node) {
        if (node == head) {
            return;
        }

        if (node.prev != null) {
            node.prev.next = node.next;
        }
        if (node.next != null) {
            node.next.prev = node.prev;
        }

        if (node == tail) {
            tail = node.prev;
        }

        node.next = head;
        node.prev = null;
        head.prev = node;
        head = node;
    }

    private void removeLeastRecentlyPlayed() {
        songMap.remove(tail.song);

        if (tail.prev != null) {
            tail.prev.next = null;
        } else {
            head = null;
        }

        tail = tail.prev;
    }

    public static void main(String[] args) {
        RecentlyPlayedStore store = new RecentlyPlayedStore(3);
        store.addPlayedSong("S1", "User1");
        store.addPlayedSong("S2", "User1");
        store.addPlayedSong("S3", "User1");
        System.out.println(Arrays.toString(store.getRecentlyPlayedSongs("User1")));  
        store.addPlayedSong("S4", "User1");
        System.out.println(Arrays.toString(store.getRecentlyPlayedSongs("User1"))); 
        store.addPlayedSong("S2", "User1");
        System.out.println(Arrays.toString(store.getRecentlyPlayedSongs("User1")));  
        store.addPlayedSong("S1", "User1");
        System.out.println(Arrays.toString(store.getRecentlyPlayedSongs("User1"))); 
    }
}



