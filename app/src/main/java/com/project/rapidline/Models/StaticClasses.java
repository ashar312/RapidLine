package com.project.rapidline.Models;


import java.util.ArrayList;
import java.util.List;

public class StaticClasses {



    public static ArrayList<String> cities = new ArrayList<String>() {{
        add("Karachi");
        add("Islamabad");
        add("Lahore");
        add("Multan");
    }};

    public static ArrayList<String> sortOrder = new ArrayList<String>() {{
        add("Sort By");
        add("Date");
    }};

}
