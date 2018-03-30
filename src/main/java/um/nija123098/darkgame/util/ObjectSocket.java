package um.nija123098.darkgame.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * A object for wrapping a socket
 * and object input and and output streams
 */
public class ObjectSocket {
    private boolean close;
    private List<Object> object;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public ObjectSocket(Socket socket, boolean server) {
        this.socket = socket;
        try {
            if (server){
                this.out = new ObjectOutputStream(this.socket.getOutputStream());
                this.out.flush();
                this.in = new ObjectInputStream(this.socket.getInputStream());
            }else{
                this.in = new ObjectInputStream(this.socket.getInputStream());
                this.out = new ObjectOutputStream(this.socket.getOutputStream());
                this.out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.object = new ArrayList<Object>();
        new Thread(() -> {
            while (true){
                try {
                    this.object.add(this.in.readObject());
                    this.recived();
                } catch (IOException | ClassNotFoundException e) {
                    if (e instanceof EOFException){
                        this.close = true;
                    }
                    if (!this.close){
                        this.thrown(e);
                    }else{
                        return;
                    }
                }
            }
        }, "ObjectSocketThread" + this.hashCode()).start();
    }
    public boolean hasNext(){
        return this.object.size() > 0;
    }
    public Object next(){
        return this.object.remove(0);
    }
    public void send(Object o){
        try {
            this.out.writeObject(o);
            this.out.flush();
        } catch (IOException e) {
            this.thrown(e);
        }
    }
    public void close(){
        if (!this.close){
            this.close = true;
            try {
                this.socket.close();
                this.in.close();
                this.out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void thrown(Throwable throwable){
        throwable.printStackTrace();
    }
    public void recived(){
    }
}
