package comp3350.breadtunes.business;

import comp3350.breadtunes.objects.Song;

public class SongQueue {
    private int size;
    private Song[] qArray;
    private int top;
    private int rear;
    private int nItems;

    public SongQueue(int s){
        size = s;
        qArray = new Song[size];
        top = 0;
        rear = -1;
        nItems = 0;

    }//constructor

    public void insert(Song ll){
        if(rear == size-1){//wrap
            rear = -1;
        }
        qArray[++rear] = ll;
        nItems++;

    }//insert


    public Song remove(){
        Song temp = qArray[top++];
        if(top ==size){//wrap
            top = 0;
        }
        nItems--;
    return temp;//incase we want to display the song to the user
    }//remove

    public int size(){
        return nItems;
    }//size

    public boolean isFull(){
        return(nItems == size);
    }//isFull
}//SongQueue
