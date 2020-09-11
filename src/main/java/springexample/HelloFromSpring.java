package springexample;

public class HelloFromSpring {
    private String message;
    private int stuff;
    
    public void setMessage(String message){
        this.message = message;
    }

    public void setStuff(int st){
        this.stuff = st;
    }

    public String getMessage(){
        return this.message;
    }

    public int getStuff(){
        return this.stuff;
    }
}
