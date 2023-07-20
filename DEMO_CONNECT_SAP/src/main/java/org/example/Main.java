package org.example;


import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

import java.nio.channels.WritableByteChannel;

public class Main {
    public static void main(String[] args) {

        // Configuration of SAP connection
        String JCO_HOST   = "10.0.3.10";           //SAP连接地址
        String JCO_SYNSNR = "00";                  //SAP连接标识
        String JCO_CLIENT = "610";                 //SAP连接客户端
        String JCO_USER   = "RFCUSER";             //登录账号，diolog会话账号也可以使用（Diolog账号可以启用调试），系统等其它账号不可以
        String JCO_PASSWD = "QAZwsx741852";        //登录密码
        String JCO_LANG   = "ZH";
        String JCO_POOL_CAPACITY = "30";
        String JCO_PEAK_LIMIT    = "100";
        String JCO_SAPROUTER     = "";              //VPN连接需要设置，内网连接只需要设置JCO_HOST即可

        SapConn con = new SapConn(JCO_HOST, JCO_SYNSNR, JCO_CLIENT, JCO_USER, JCO_PASSWD, JCO_LANG, JCO_POOL_CAPACITY, JCO_PEAK_LIMIT, JCO_SAPROUTER);
        JCoDestination jCoDestination = SAPConnUtil.connect(con);

        try {
            // 获取调用 RFC 函数对象
            JCoFunction func = jCoDestination.getRepository().getFunction("ZFM_RFC_TEST");  //调研RFC函数名称
            // 配置传入参数
            JCoParameterList importParameterList = func.getImportParameterList();
           // importParameterList.setValue("IV_FLAG", 1);

            // 调用并获取返回值
            func.execute(jCoDestination);
            // 获取 内表 - ET_MARA
        //   JCoTable maraTable = func.getTableParameterList().getTable("EV_FALG");
            String evFalg = func.getExportParameterList().getString("EV_FALG");

            System.out.println(evFalg);

            // 循环输出 Table 数据
      /*      for (int i = 0; i < maraTable.getNumRows(); i++) {
                maraTable.setRow(i);

                String matnr = maraTable.getString("MATNR");
                String esdra = maraTable.getString("ERSDA");
                String ernam = maraTable.getString("ERNAM");
                String matkl = maraTable.getString("MATKL");
                String meins = maraTable.getString("MEINS");

                System.out.println("物料编号：" + matnr + " - 创建日期：" + esdra + " - 创建人：" + ernam + " - 物料组：" + matkl + " - 单位：" + meins);
            }
*/


        } catch (Exception e) {
            e.printStackTrace();
        }






    }
}