package org.example;

import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.ServerDataProvider;
import com.sap.conn.jco.server.*;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

public class StepByStepServer

{

    static String SERVER_NAME1 = "SERVER";
    static String DESTINATION_NAME1 = "ABAP_AS_WITHOUT_POOL";
    static String DESTINATION_NAME2 = "ABAP_AS_WITH_POOL";
    static MyTIDHandler myTIDHandler = null;


    static

    {

        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.0.3.10");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "610");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "MIS01");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "015987");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "EN");
        createDataFile(DESTINATION_NAME1, "jcoDestination", connectProperties);
        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");
        createDataFile(DESTINATION_NAME2, "jcoDestination", connectProperties);
        
        Properties servertProperties = new Properties();
        servertProperties.setProperty(ServerDataProvider.JCO_GWHOST, "10.0.3.10");
        servertProperties.setProperty(ServerDataProvider.JCO_GWSERV, "sapgw00");
        servertProperties.setProperty(ServerDataProvider.JCO_PROGID, "LDKJCO");
        servertProperties.setProperty(ServerDataProvider.JCO_REP_DEST, "ABAP_AS_WITH_POOL");
        servertProperties.setProperty(ServerDataProvider.JCO_CONNECTION_COUNT, "2");
        createDataFile(SERVER_NAME1, "jcoServer", servertProperties);

    }



    static void createDataFile(String name, String suffix, Properties properties)
    {
        File cfg = new File(name + "." + suffix);
        if(!cfg.exists())
        {
            try
            {
                FileOutputStream fos = new FileOutputStream(cfg, false);
                properties.store(fos, "for tests only !");
                fos.close();
            }
            catch(Exception e)
            {
                throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
            }
        }
    }


    static class StfcConnectionHandler implements JCoServerFunctionHandler

    {

        public void handleRequest(JCoServerContext serverCtx, JCoFunction function)

        {

            System.out.println("—————————————————————-");

            System.out.println("call : " + function.getName());

            System.out.println("ConnectionId : " + serverCtx.getConnectionID());

            System.out.println("SessionId : " + serverCtx.getSessionID());

            System.out.println("TID : " + serverCtx.getTID());

            System.out.println("repository name : " + serverCtx.getRepository().getName());

            System.out.println("is in transaction : " + serverCtx.isInTransaction());

            System.out.println("is stateful : " + serverCtx.isStatefulSession());

            System.out.println("—————————————————————-");

            System.out.println("gwhost: " + serverCtx.getServer().getGatewayHost());

            System.out.println("gwserv: " + serverCtx.getServer().getGatewayService());

            System.out.println("progid: " + serverCtx.getServer().getProgramID());

            System.out.println("—————————————————————-");

            System.out.println("attributes : ");

            System.out.println(serverCtx.getConnectionAttributes().toString());

            System.out.println("—————————————————————-");

            System.out.println("CPIC conversation ID: " + serverCtx.getConnectionAttributes().getCPICConversationID());

            System.out.println("—————————————————————-");

            System.out.println("req text: " + function.getImportParameterList().getString("REQUTEXT"));

            function.getExportParameterList().setValue("ECHOTEXT", function.getImportParameterList().getString("REQUTEXT"));

            function.getExportParameterList().setValue("RESPTEXT", "Hello World");



// In sample 3 (tRFC Server) we also set the status to executed:

            if(myTIDHandler != null)

                myTIDHandler.execute(serverCtx);

        }

    }



    static void step1SimpleServer()

    {

        JCoServer server;

        try

        {

            server = JCoServerFactory.getServer(SERVER_NAME1);

        }

        catch(JCoException ex)

        {

            throw new RuntimeException("Unable to create the server " + SERVER_NAME1 + ", because of " + ex.getMessage(), ex);

        }



        JCoServerFunctionHandler stfcConnectionHandler = new StfcConnectionHandler();

        DefaultServerHandlerFactory.FunctionHandlerFactory factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();

        factory.registerHandler("STFC_CONNECTION", stfcConnectionHandler);

        server.setCallHandlerFactory(factory);



        server.start();

        System.out.println("The program can be stoped using <ctrl>+<c>");

    }



    static class MyThrowableListener implements JCoServerErrorListener, JCoServerExceptionListener

    {



        public void serverErrorOccurred(JCoServer jcoServer, String connectionId, JCoServerContextInfo serverCtx, Error error)

        {

            System.out.println(">>> Error occured on " + jcoServer.getProgramID() + " connection " + connectionId);

            error.printStackTrace();

        }



        public void serverExceptionOccurred(JCoServer jcoServer, String connectionId, JCoServerContextInfo serverCtx, Exception error)

        {

            System.out.println(">>> Error occured on " + jcoServer.getProgramID() + " connection " + connectionId);

            error.printStackTrace();

        }

    }



    static class MyStateChangedListener implements JCoServerStateChangedListener

    {

        public void serverStateChangeOccurred(JCoServer server, JCoServerState oldState, JCoServerState newState)

        {



// Defined states are: STARTED, DEAD, ALIVE, STOPPED;

// see JCoServerState class for details.

// Details for connections managed by a server instance

// are available via JCoServerMonitor

            System.out.println("Server state changed from " + oldState.toString() + " to " + newState.toString() + " on server with program id "

            + server.getProgramID());

        }

    }



    static void step2SimpleServer()

    {

        JCoServer server;

        try

        {

            server = JCoServerFactory.getServer(SERVER_NAME1);

        }

        catch(JCoException ex)

        {

            throw new RuntimeException("Unable to create the server " + SERVER_NAME1 + ", because of " + ex.getMessage(), ex);

        }



        JCoServerFunctionHandler stfcConnectionHandler = new StfcConnectionHandler();

        DefaultServerHandlerFactory.FunctionHandlerFactory factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();

        factory.registerHandler("STFC_CONNECTION", stfcConnectionHandler);

        server.setCallHandlerFactory(factory);



// additionally to step 1

        MyThrowableListener eListener = new MyThrowableListener();

        server.addServerErrorListener(eListener);

        server.addServerExceptionListener(eListener);



        MyStateChangedListener slistener = new MyStateChangedListener();

        server.addServerStateChangedListener(slistener);



        server.start();

        System.out.println("The program can be stoped using <ctrl>+<c>");

    }



    static class MyTIDHandler implements JCoServerTIDHandler
    {

        Map<String, TIDState> availableTIDs = new Hashtable<String, TIDState>();

        public boolean checkTID(JCoServerContext serverCtx, String tid)

        {

// This example uses a Hashtable to store status information. But usually

// you would use a database. If the DB is down, throw a RuntimeException at

// this point. JCo will then abort the tRFC and the R/3 backend will try

// again later.



            System.out.println("TID Handler: checkTID for " + tid);

            TIDState state = availableTIDs.get(tid);

            if(state == null)

            {

                availableTIDs.put(tid, TIDState.CREATED);

                return true;

            }



            if(state == TIDState.CREATED || state == TIDState.ROLLED_BACK)

                return true;



            return false;

// "true" means that JCo will now execute the transaction, "false" means

// that we have already executed this transaction previously, so JCo will

// skip the handleRequest() step and will immediately return an OK code to R/3.

        }



        public void commit(JCoServerContext serverCtx, String tid)

        {

            System.out.println("TID Handler: commit for " + tid);



// react on commit e.g. commit on the database

// if necessary throw a RuntimeException, if the commit was not

// possible

            availableTIDs.put(tid, TIDState.COMMITTED);

        }



        public void rollback(JCoServerContext serverCtx, String tid)

        {

            System.out.println("TID Handler: rollback for " + tid);

            availableTIDs.put(tid, TIDState.ROLLED_BACK);



// react on rollback e.g. rollback on the database

        }



        public void confirmTID(JCoServerContext serverCtx, String tid)

        {

            System.out.println("TID Handler: confirmTID for " + tid);



            try

            {

// clean up the resources

            }

// catch(Throwable t) {} //partner wont react on an exception at

// this point

            finally

            {

                availableTIDs.remove(tid);

            }

        }



        public void execute(JCoServerContext serverCtx)

        {

            String tid = serverCtx.getTID();

            if(tid != null)

            {

                System.out.println("TID Handler: execute for " + tid);

                availableTIDs.put(tid, TIDState.EXECUTED);

            }

        }



        private enum TIDState

        {

            CREATED, EXECUTED, COMMITTED, ROLLED_BACK, CONFIRMED;

        }

    }



    static void step3SimpleTRfcServer()

    {

        JCoServer server;

        try

        {

            server = JCoServerFactory.getServer(SERVER_NAME1);

        }

        catch(JCoException ex)

        {

            throw new RuntimeException("Unable to create the server " + SERVER_NAME1 + ", because of " + ex.getMessage(), ex);

        }



        JCoServerFunctionHandler stfcConnectionHandler = new StfcConnectionHandler();

        DefaultServerHandlerFactory.FunctionHandlerFactory factory = new DefaultServerHandlerFactory.FunctionHandlerFactory();

        factory.registerHandler("STFC_CONNECTION", stfcConnectionHandler);

        server.setCallHandlerFactory(factory);



// additionally to step 1

        myTIDHandler = new MyTIDHandler();

        server.setTIDHandler(myTIDHandler);



        server.start();

        System.out.println("The program can be stoped using <ctrl>+<c>");

    }



    public static void main(String[] a)

    {
        System.out.println("11111111111111111111111111111111111111111111111111");

// step1SimpleServer();

        step2SimpleServer();

// step3SimpleTRfcServer();

    }

}

