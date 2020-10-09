package springexample;

public class HelloFromSpring implements IPersistence {
    private int id;
    private String message;
    private int stuff;

    public void setId(int idd){
        this.id = idd;
    }

    public int getId(){
        return this.id;
    }
    
    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setStuff(int st){
        this.stuff = st;
    }

    public int getStuff(){
        return this.stuff;
    }
}
