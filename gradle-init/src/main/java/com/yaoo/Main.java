package com.yaoo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        String s = "122";
        String regex = "^([5-9]\\d([.]\\d+){0,1}|[1-9]\\d{2,}([.]\\d+){0,1})$";
        System.out.println(Pattern.matches(regex, s));

        s = "param1=[\"appId\"]";
    }
}
