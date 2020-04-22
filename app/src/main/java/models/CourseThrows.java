package models;

import java.util.ArrayList;

public class CourseThrows {
    private ArrayList<Throw> throwList;

    public CourseThrows(){
        throwList = new ArrayList<>();
    }

    public void addThrowEnd(Throw t){
        throwList.add(t);
    }

    public void addThrowEnd(Throw t, int pos){
        throwList.add(pos,t);
    }

    public void removeLast(){
        throwList.remove(throwList.size()-1);
    }

    public void removeAll(ArrayList<Throw> thrs){
        throwList.removeAll(thrs);
    }

    public void setThrow(Throw t, int pos){
        throwList.set(pos,t);
    }

    public int numberOfThrows(){
        return throwList.size();
    }

    public ArrayList<Throw> getThrows(){
        return throwList;
    }

    public Throw getThrow(int pos){
        return throwList.get(pos);
    }
}
