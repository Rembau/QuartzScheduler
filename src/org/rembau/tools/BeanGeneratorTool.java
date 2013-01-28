package org.rembau.tools;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.ArrayList;

public class BeanGeneratorTool
{
    //存放Bean的类名
    private String className;
    //接收生成Bean的文件
    private File file;
    //Bean属性和其属性对应的类型的元数据List
    private List<BeanInfo> bList = new ArrayList<BeanInfo>();
    //写入流
    private RandomAccessFile raf = null;
    //构造方法的参数字符串
    private StringBuffer paramsBuffer = new StringBuffer();
    //用来标识构造器参数何时加上","
    private boolean bFirst = true;

    //解析Bean的属性和其属性对应的类型
    private void parse(File file) throws Exception
    {
        //保存解析的Bean文件
        this.file = file;
        //解析出类名
        this.className = file.getName().substring(0, file.getName().indexOf("."));
        //读取Bean文件
        BufferedReader br = new BufferedReader(new FileReader(file));
        String strLine = null;
        //逐行读取文件内容
        while ((strLine = br.readLine()) != null) 
        {
            //当行内容同时包含private关键字和";"号时则解析该行字符串
            if (strLine.contains("private") && strLine.contains(";")) 
            {
                //获取类型字符串第一个字符的起始位置
                Integer leftLoc = strLine.indexOf("private ") + "private ".length();
                //获取属性名最后一个字符的位置
                Integer rightLoc = strLine.indexOf(";");
                //解析出属性类型和属性名，数组[0]为属性类型，数组[1]为属性名
                String[] tmpStr = strLine.substring(leftLoc, rightLoc).split(" ");
                //装Bean元数据封装并放入集合中
                bList.add(new BeanInfo(tmpStr[0], tmpStr[1]));
            }
        }
        br.close();
    }

    //生成Bean的get、set方法和构造方法
    private void generateSetGetConstructor() throws Exception
    {     
        raf = new RandomAccessFile(file, "rw");
        raf.seek(file.length() - 3);
        raf.writeBytes("\r\n");

        //--------------------生成Bean的构造方法-------------------
        for (BeanInfo beanInfo : bList) 
        {
            if (bFirst) 
            {
                //第一次构造方法的参数后不加","
                paramsBuffer.append(beanInfo.getType() + " " 
                    + beanInfo.getAttr());
                bFirst = false;
            }
            else 
            {
                //当参数列表过长时自动换行
                if (paramsBuffer.length() >= 40) 
                {
                    paramsBuffer.append("\r\n\t\t" + ", " + beanInfo.getType() 
                        + " " + beanInfo.getAttr());
                }
                else 
                {
                    paramsBuffer.append(", " + beanInfo.getType() 
                        + " " + beanInfo.getAttr());
                }   
            }
        }

        //解决中文问题输出时是乱码
        raf.write("\t//无参数构造器\r\n".getBytes());
        //无参数的构造器
        raf.writeBytes("\tpublic " + className + "()\r\n\t{\r\n\t}\r\n");

        //增加注释
        raf.write("\t//初始化全部属性的构造器\r\n".getBytes());
        raf.writeBytes("\tpublic " + className + "(" 
            + paramsBuffer.toString() + ")" + "\r\n\t{\r\n");
        for (BeanInfo beanInfo : bList) 
        {
            raf.writeBytes("\t\t");
            raf.writeBytes("this." + beanInfo.getAttr() 
                + " = " + beanInfo.getAttr() + ";");
            raf.writeBytes("\r\n");
        }
        raf.writeBytes("\t}\r\n");
        //----------------------------------------------------------

        for (BeanInfo beanInfo : bList) 
        {   
            //增加注释
            raf.writeBytes("\t//" + beanInfo.getAttr());
            //解决中文问题输出时是乱码
            raf.write("属性的setter和getter方法".getBytes());
            raf.writeBytes("\r\n");
            //--------------------生成Bean的set方法---------------------
            raf.writeBytes("\tpublic void set" 
                + beanInfo.getAttr().substring(0, 1).toUpperCase() 
                + beanInfo.getAttr().substring(1) 
                + "(" + beanInfo.getType() + " " + beanInfo.getAttr() 
                + ")" + "\r\n");
            raf.writeBytes("\t{" + "\r\n");
            raf.writeBytes("\t\tthis." + beanInfo.getAttr() 
                + " = " + beanInfo.getAttr() + ";" + "\r\n");
            raf.writeBytes("\t}" + "\r\n");
            //----------------------------------------------------------

            //--------------------生成Bean的get方法---------------------
            raf.writeBytes("\tpublic " 
                + beanInfo.getType() + " get" 
                + beanInfo.getAttr().substring(0, 1).toUpperCase()
                + beanInfo.getAttr().substring(1) + "()" + "\r\n");
            raf.writeBytes("\t{" + "\r\n");
            raf.writeBytes("\t\treturn " + beanInfo.getAttr() 
                + ";" + "\r\n\t}\r\n");
            //----------------------------------------------------------
        }
        raf.writeBytes("}\r\n");
        //关闭写入流
        raf.close();
    }
  //定义Bean属性和其属性类型的元数据类
    class BeanInfo 
    {
      private String type;
      private String attr;

      public BeanInfo(String type, String attr)
      {
          this.type = type;
          this.attr = attr;
      }
      public void setType(String type)
      {
          this.type = type;
      }

      public String getType()
      {
          return type;
      }

      public void setAttr(String attr)
      {
          this.attr = attr;
      }

      public String getAttr()
      {
          return attr;
      }
    }
    public static void main(String[] args) throws Exception 
    {
        BeanGeneratorTool beanGen = new BeanGeneratorTool();
        beanGen.parse(new File("src//beanGenerator//test.java"));
        beanGen.generateSetGetConstructor();
    }
}