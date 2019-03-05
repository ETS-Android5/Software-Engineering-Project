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
        if(!isFull()) {
            if (rear == size - 1) {
                rear = -1;
            }
            qArray[++rear] = insertSong;
            nItems++;
        }
        else{
            //unable to add to a full queue

            //do nothing
        }
    }


    public Song remove(){
        Song temp;
        if(!isEmpty() && top != rear) {
            temp = qArray[top++];
            // Wrap queue if queue cannot fit another item
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

    // adds song next to currently playing
    // makes a new queue
    public void addSongToPlayNext(Song nextSong, int currentlyPlayingIndex)
    {
        int index = currentlyPlayingIndex;

        if(!isFull())
        {
            int j = 0;
            Song[] newArray = new Song[size];

            if(index == 0)
            { newArray[0] = qArray[0]; }

            for (int i = 0; i < index; i++)
            {
                newArray[i] = qArray[i];
                j = i;
            }

            j++;
            newArray[j] = nextSong;
            j++;
            this.nItems++;

            if(j != this.nItems)
            {
                for (int i = j; i < this.nItems; i++)
                {
                    newArray[i] = qArray[i - 1];
                }
            }
            qArray = newArray;
        }

        else if(isEmpty())
        {
            this.insert(nextSong);
            this.nItems++;
        }
        // else do nothing
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
