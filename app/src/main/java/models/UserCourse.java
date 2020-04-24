package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserCourse {
    private Map<Integer,CourseThrows> throwList = new HashMap<>();
    private ArrayList<Integer> parResults = new ArrayList<>();
    private String name;

    public UserCourse(int pars,String name){
        for(int i = 1; i <= pars; i++){
            parResults.add(0);
            throwList.put(i,new CourseThrows());
        }
        this.name = name;
    }

    public UserCourse(ArrayList<Integer> strokes, Map<Integer,CourseThrows> courseThrows, String name){
        parResults = strokes;
        this.name = name;
    }


    public CourseThrows getUserThrows(int pos){
        return throwList.get(pos);
    }


    public void addThrow(Throw newThrow, int par){
        if(throwList.get(par) == null){
            throwList.put(par,new CourseThrows());
        }
        throwList.get(par).addThrowEnd(newThrow);
    }

    public void resetPar(int par){
        if (throwList.get(par) != null) {
            throwList.get(par).removeAll(throwList.get(par).getThrows());
        }
    }

    public Map<Integer, CourseThrows> getThrowList() {
        return throwList;
    }

    public void setThrowList(Map<Integer, CourseThrows> throwList) {
        this.throwList = throwList;
    }

    public ArrayList<Integer> getParResults() {
        return parResults;
    }

    public void setParResults(ArrayList<Integer> parResults) {
        this.parResults = parResults;
    }

    public void setPar(int pos, int par){
        parResults.set(pos,par);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
