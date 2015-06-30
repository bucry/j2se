package com.bfchuan.mini.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TimeStamp {

	private static TimeStamp ts;
    private SimpleDateFormat sdf;

    private TimeStamp() {
    }
    
    public static TimeStamp getInstance() {
    	if (ts == null) {
    		ts = new TimeStamp();
    	}
    	return ts;
    }
    
    public String getSequence() {
        StringBuilder buf = new StringBuilder();
        Random r = new Random();
        for (int i = 0; i < 4; i++) {
            buf.append(r.nextInt(10));
        }
        return buf.toString();
    }

    public String getTimeStamp() {
        sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }
}
