package etu1929.framework;

public class ModelView {
    String view;
    HashMap<String,Object> data=new HashMap<>();

    public String getView(){
        return view;
    }

    public void setView(String view){
        this.view = view;
    }

    public HashMap<String,Object> getData(){
        return data;
    }

    public void setData(HashMap<String,Object> data){
        this.data=data;
    } 
    
    public void addItem(String key,Object value){
        data.put(key,value);
    }
}
