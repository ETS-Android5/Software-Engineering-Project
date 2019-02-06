package comp3350.breadtunes.business;

import comp3350.breadtunes.objects.Song;

public class SongQueue {
    private int size;
    private Song[] qArray;
    private int top;
    private int rear;
    private int nItems;

    public SongQueue(int queueSize){
        size = queueSize;
        qArray = new Song[size];
        top = 0;
        rear = -1;
        nItems = 0;
    }

    public void insert(Song insertSong){
        // Wrap queue
        if (rear == size - 1){
            rear = -1;
        }
        qArray[++rear] = insertSong;
        nItems++;
    }


    public Song remove(){
        Song temp = qArray[top++];

        // Wrap queue if queue cannot fit another item
        if (top == size){
            top = 0;
        }
        nItems--;

        return temp;
    }

    public int size(){
        return nItems;
    }

    public boolean isEmpty() {
        return (nItems == 0);
    }

    public boolean isFull(){
        return (nItems == size);
    }
}
