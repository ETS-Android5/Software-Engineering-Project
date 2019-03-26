package comp3350.breadtunes.business;


import comp3350.breadtunes.business.interfaces.sQueue;
import comp3350.breadtunes.objects.Song;

public class SongQueue implements sQueue {
    private int size;
    private Song[] qArray;
    private int top;
    private int rear;
    private int nItems;


    public SongQueue(int qSize){
        size = qSize;
        qArray = new Song[size];
        top = 0;
        rear = -1;
        nItems = 0;
    }

    public void insert(Song insertSong){
        // Wrap updateQueue
        if(!isFull()) {
            if (rear == size - 1) {
                rear = -1;
            }
            qArray[++rear] = insertSong;
            nItems++;
        }
        else{
            //unable to add to a full updateQueue

            //do nothing
        }
            for(int j = 0;j<nItems-1;j++){
                if(qArray[nItems-1] == qArray[j]){
                    rear--;
                    nItems--;
                }
        }
    }

    public Song remove(){
        Song temp;
        if(!isEmpty() && top != rear) {
            temp = qArray[top++];
            // Wrap updateQueue if updateQueue cannot fit another item
            if (top == size) {
                top = 0;
            }
            nItems--;
            return temp;
        }
        else if(!isEmpty() && top == rear){
            temp = qArray[top];
            nItems--;
            return temp;
        }
        return null;
    }

    public void addSongToPlayNext(Song nextSong)
    {
        if(!isFull())
        {
            if(!isEmpty())
            {
                if (rear == size - 1)
                    rear = -1;

                Song[] newArray = new Song[size];
                newArray[0] = nextSong;

                for (int i = 1; i < nItems; i++)
                { newArray[i] = qArray[i - 1]; }
                newArray[rear+1] = qArray[rear];

                rear++;
                nItems++;
                qArray = newArray;
            }

            else if(isEmpty())
            { this.insert(nextSong); nItems++;}
        }
        // else if full do nothing
    }

    public Song getSong(int index)
    { return qArray[index];}

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
