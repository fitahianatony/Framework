package test;

import etu1929.framework.annotation.Url;

public class Emp {
    @Url(url = "emp-test")
    public String testMethod(){
        return "Success";
    }
}
