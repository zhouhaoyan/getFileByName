package com.infohold;

import java.io.File;
import java.io.IOException;

/**
 * Created by ttzhy on 2017/1/12.
 */
public class CmdExcute {

    public  static void excute(String strcmd) {

        //执行批处理文件
        //判断批处理文件是否存在
        File cmdile = new File(strcmd);
        if (cmdile.exists()) { //文件存在时
            System.out.println("cmd文件存在！");
            Runtime rt = Runtime.getRuntime();
            Process ps = null;
            try {
                ps = rt.exec("cmd.exe /c start "+cmdile);
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
                System.out.println("执行完成.路径:" + strcmd);
            } else {
                System.out.println("执行失败.路径：" + strcmd);
            }
            ps.destroy();
            ps = null;

            //批处理执行完后，根据cmd.exe进程名称 kill掉cmd窗口(这个方法是好不容易才找到了，网上很多介绍的都无效，csdn废我3分才找到这个方法)
           //  new CmdExcute().killProcess();


        }else {


            System.out.println("cmd文件不存在！");
        }

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
