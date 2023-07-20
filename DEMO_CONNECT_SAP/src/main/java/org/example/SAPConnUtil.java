package org.example;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.*;
import com.sap.conn.jco.ext.DestinationDataProvider;


/**
 * @Author: Wriprin
 * @Date: 2022/11/25 17:01
 * @Version 1.0
 */
public class SAPConnUtil {
    private static final String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";

    /**
     * Establish SAP interface
     * @param name  ABAP name
     * @param suffix    file suffix
     * @param properties    file content
     */
    private static void createDataFile(String name, String suffix, Properties properties){
        File cfg = new File(name+"."+suffix);
        if(cfg.exists()){
            cfg.deleteOnExit();
        }
        try{
            FileOutputStream fos = new FileOutputStream(cfg, false);
            properties.store(fos, "for tests only !");
            fos.close();
        }catch (Exception e){
            System.out.println("Create Data file fault, error msg: " + e.toString());
            throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
        }
    }

    /**
     * Init SAP connection
     */
    private static void initProperties(SapConn sapConn) {
        Properties connectProperties = new Properties();
        // SAP server location
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, sapConn.getJCO_ASHOST());
        // SAP system number
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  sapConn.getJCO_SYSNR());
        // SAP client
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, sapConn.getJCO_CLIENT());
        // SAP user ID
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   sapConn.getJCO_USER());
        // SAP user PW
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, sapConn.getJCO_PASSWD());
        // SAP language
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   sapConn.getJCO_LANG());
        // MAX connection
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, sapConn.getJCO_POOL_CAPACITY());
        // MAX connection threads
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, sapConn.getJCO_PEAK_LIMIT());
        // SAP ROUTER
        connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER, sapConn.getJCO_SAPROUTER());

        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);
    }

    /**
     * Get SAP connection
     * @return  SAP connection object
     */
    public static JCoDestination connect(SapConn sapConn){
        System.out.println("Connecting to SAP...");
        JCoDestination destination = null;
        initProperties(sapConn);
        try {
            destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
            destination.ping();
            System.out.println("Connection established.");
        } catch (JCoException e) {
            System.out.println("Connect SAP fault, error msg: " + e.toString());
        }
        return destination;
    }
}
