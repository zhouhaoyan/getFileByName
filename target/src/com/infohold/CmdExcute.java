package com.infohold;

import java.io.IOException;

/**
 * Created by ttzhy on 2017/1/12.
 */
public class CmdExcute {

    public  static void excute(String strcmd){

        //执行批处理文件

        Runtime rt = Runtime.getRuntime();
        Process ps = null;
        try {
            ps = rt.exec(strcmd);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            ps.waitFor();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        int i = ps.exitValue();
        if (i == 0) {
            System.out.println("执行完成.") ;
        } else {
            System.out.println("执行失败.") ;
        }
        ps.destroy();
        ps = null;

        //批处理执行完后，根据cmd.exe进程名称 kill掉cmd窗口(这个方法是好不容易才找到了，网上很多介绍的都无效，csdn废我3分才找到这个方法)
        new CmdExcute().killProcess();
        return;

    }


    public void killProcess(){
        Runtime rt = Runtime.getRuntime();
        Process p = null;
        try {
            rt.exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
