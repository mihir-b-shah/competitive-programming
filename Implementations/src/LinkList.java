
import java.util.Iterator;
import java.util.ListIterator;

class Node<T> {
    Node next;
    Node prev;
    T data;

    Node() {  
    }

    Node(T data) {
        this.data = data;
    }
}

public class LinkList<T> implements Iterable<T> {
    Node head;
    Node tail;

    LinkList() {
        head = new Node();
        tail = head;
    }

    Node add(T data) {
        Node aux = new Node();
        head.data = data;
        head.next = aux;
        aux.prev = head;
        Node ret = head;
        head = aux;
        return ret;
    }

    void remove(Node data) {
        if(data == tail) {
            tail = tail.next;
            tail.prev = null;
        } else {
            Node pr = data.prev;
            Node nx = data.next;
            pr.next = nx;
            nx.prev = pr;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ListIterator<T>() {
            Node<T> curr = tail;
            int index = 0;
            Node<T> last;

            @Override
            public boolean hasNext() {
                return curr.next != null;
            }

            @Override
            public T next() {
                T ret = curr.data;
                last = curr;
                curr = curr.next;
                ++index;
                return ret;
            }

            @Override
            public boolean hasPrevious() {
                return curr != tail;
            }

            @Override
            public T previous() {
                curr = curr.prev;
                last = curr;
                --index;
                return curr.data;
            }

            @Override
            public int nextIndex() {
                return index;
            }

            @Override
            public int previousIndex() {
                return index-1;
            }

            @Override
            public void remove() {
                LinkList.this.remove(last);
            }

            @Override
            public void set(T e) {
                last.data = e;
            }

            @Override
            public void add(T e) {
                ++index;
                if(curr == tail) {
                    Node aux = new Node(e);
                    curr.prev = aux;
                    aux.next = curr;
                    tail = aux;
                    curr = curr.prev;
                } else {
                    Node aux = new Node(e);
                    Node pr = curr.prev;
                    pr.next = aux;
                    aux.prev = curr.prev;
                    curr.prev = aux;
                    aux.next = curr;
                    curr = curr.prev;
                }
            }
        };
    }

    public ListIterator<T> listIterator() {
        return (ListIterator<T>) iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node aux = tail;
        sb.append('[');
        while(aux.next != null) {
            sb.append(aux.data.toString());
            sb.append(' ');
            aux = aux.next;
        }
        if(sb.length() > 1) {
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(']');
        return sb.toString();
    }
}