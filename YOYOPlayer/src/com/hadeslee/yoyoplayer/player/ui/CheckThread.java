/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hadeslee.yoyoplayer.player.ui;

import com.hadeslee.yoyoplayer.util.Config;
import com.hadeslee.yoyoplayer.util.Util;
import com.hadeslee.yoyoplayer.util.Version;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hadeslee
 */
class CheckThread extends Thread {

    private static Logger log = Logger.getLogger(CheckThread.class.getName());

    private void checkUpdate() {
        String s = Config.getConfig().getCheckUpdateStrategy();
        Date date = Config.getConfig().getLastCheckUpdate();
        if (date == null) {
            date = new Date();
        }
        Calendar last = Calendar.getInstance();
        last.setTime(date);
        if (s.equals(Config.CHECK_DAY)) {
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.YEAR) == last.get(Calendar.YEAR) &&
                    now.get(Calendar.MONTH) == last.get(Calendar.MONTH) &&
                    now.get(Calendar.DAY_OF_MONTH) == last.get(Calendar.DAY_OF_MONTH)) {
            //如果年月日都相等,则不要比较了,今天已经比过了
            } else {
                Version ver = Util.getRemoteVersion();
                if (ver != null) {
                    Config.getConfig().setLastCheckUpdate(new Date());
                    Util.checkUpdate(ver,true);
                }
            }
        } else if (s.equals(Config.CHECK_MONTH)) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.MONTH, 1);
            if (now.before(last)) {
                Version ver = Util.getRemoteVersion();
                if (ver != null) {
                    Config.getConfig().setLastCheckUpdate(new Date());
                    Util.checkUpdate(ver,true);
                }
            }
        } else if (s.equals(Config.CHECK_WEEK)) {
            Calendar now = Calendar.getInstance();
            now.add(Calendar.WEEK_OF_YEAR, 1);
            if (now.before(last)) {
                Version ver = Util.getRemoteVersion();
                if (ver != null) {
                    Config.getConfig().setLastCheckUpdate(new Date());
                    Util.checkUpdate(ver,true);
                }
            }
        } else if (s.equals(Config.CHECK_NONE)) {
        //什么都不做
        }
    }

    public void run() {
        long last = System.currentTimeMillis();
        if (!Util.voteOpen()) {
            Config.getConfig().voteOpenCount++;
        }
        while (true) {
            try {
                Thread.sleep(600000); //
                if (Config.getConfig().voteOpenCount > 0 && Util.voteOpen()) {
                    Config.getConfig().voteOpenCount--;
                }
                if (Config.getConfig().voteOneHourCount > 0 && Util.voteOneHour()) {
                    Config.getConfig().voteOneHourCount--;
                }
                if (System.currentTimeMillis() - last > 3600000) {
                    last = System.currentTimeMillis();
                    if (!Util.voteOneHour()) {
                        Config.getConfig().voteOneHourCount++;
                    }
                }
                checkUpdate();
            } catch (Exception ex) {
                Logger.getLogger(CheckThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
