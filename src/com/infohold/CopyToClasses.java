package com.infohold;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


/**
 * Created by zhouhaoyan on 2017/1/12.
 */
public class CopyToClasses {


    final String COPY_BAT_PATH=LoadProperties.loadProperties().getProperty("copy_bat_path");
   final String EXCUTE_BAT_PATH=LoadProperties.loadProperties().getProperty("excute_bat_path");
    final String BUILDFILEPATH=LoadProperties.loadProperties().getProperty("buildPath");

          // 获取文件名称列表
        List<String> fileList=ReadFile.readTxtFile(BUILDFILEPATH);



        public  void  run(){
               //获取 bat 命令行 字符串
                 String cmd=  getCopyBat(fileList);
                //生成.bat 文件
                writeCmdBat(cmd);
                //执行命令行文件
                 CmdExcute.excute(EXCUTE_BAT_PATH);


        }


    /**
     * 获取bat脚本 操作本地文件 执行 copy 动作:将文件名称列表文件copy 至 增量目录
     *
     * copy dir demo: xcopy /yis Pictures  .\out\test
     * copy file demo: xcopy /y QQ图片20161015095225.jpg  .\out\test\
     */
    public String getCopyBat(List<String> fileStrArray){
         StringBuffer sf=new StringBuffer();

            //从当前系统中获取换行符，默认是"\n"  
        String lineSeparator=System.getProperty("line.separator","\n");

           //判断当前路径是文件夹 还是文件
        for (String filePath:fileStrArray ) {
            //获取路径
            filePath= filePath.replace("/","\\");
            filePath= filePath.replace("front","");
            int i= filePath.lastIndexOf("\\");
            String path=filePath.substring(0,i);

            //获取文件名称
            String name=filePath.substring(i+1,filePath.length());


            //判断是文件夹还是文件
            String [] str=name.split("\\.");
            //str.length 长度大于1 说明该 目标 是文件(这里规定文件名不能 带有 “.”)

            if (str.length>1 ) {
                //获取文件名称
                //如果是*.java 文件 替换为.class
                name=str[1].equals("java")?name.replace("java","class"):name;
                        //拼接 copy 文件的 命令行
                        sf.append("xcopy /y ..\\app1");
                        sf.append(path);//源目录
                        sf.append("\\");
                        sf.append(name);//文件名称
                        sf.append("  ..\\app3");
                        sf.append(path); //目标路径
                        sf.append("\\");
                        sf.append(lineSeparator); //换行符
            }else {
                //拼接 copy 文件的 命令行
                sf.append("xcopy /yis ..\\app1");
                sf.append(filePath);//源目录
                sf.append("  ..\\app3");
                sf.append(filePath); //目标路径
                sf.append(lineSeparator); //换行符


            }

            }
            int i=sf.lastIndexOf(lineSeparator);
            return  sf.subSequence(0,i).toString();


    }

    /**
     * 生成 bat 文件
     * @param cmdStr
     */
    public  void writeCmdBat(String cmdStr){
        try {
            int bytesum = 0;
            int byteread = 0;

            File cmdfile = new File(COPY_BAT_PATH);


                FileOutputStream fs = new FileOutputStream(cmdfile);
                if (!cmdfile.exists()) {
                        cmdfile.createNewFile();
                    }
                byte[] cmdIntBytes = cmdStr.getBytes();

                    fs.write(cmdIntBytes);
                    fs.flush();
                    fs.close();
            System.out.println("生成copy.bat成功！");


        }
        catch (Exception e) {
            System.out.println("生成copy.bat文件操作出错");
            e.printStackTrace();

        }


    }

    public static void main(String[] args) {
 /*       String filePath="test";
        StringBuffer sf=new StringBuffer("xcopy /y ");
        sf.append(filePath);//源目录
        sf.append("  .\\target\\");
        sf.append(filePath);

        System.out.println(sf);*/
       CopyToClasses entity=new CopyToClasses();
        entity.run();
    }



}
