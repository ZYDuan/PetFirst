package socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


public class ClientManager {

    private static Map<String,Socket> clientList = new HashMap<>();
    private static ServerThread serverThread = null;
    private static MyBeans mybean=new MyBeans();
    private static class ServerThread implements Runnable {

        private int port = 10086;
        private boolean isExit = false;
        private ServerSocket server;

        public ServerThread() {
            try {
                server = new ServerSocket(port);
                System.out.println("��������ɹ�" + "port:" + port);
            } catch (IOException e) {
                System.out.println("����serverʧ�ܣ�����ԭ��" + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                while (!isExit) {
                    
                    System.out.println("�ȴ��ֻ�������... ... ");
                    final Socket socket = server.accept();
                    
                    final String address = socket.getRemoteSocketAddress().toString();
                    System.out.println("���ӳɹ������ӵ��ֻ�Ϊ��" + address);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                               
                                synchronized (this){
                                    
                                    clientList.put(address,socket);
                                }
                                
                                InputStream inputStream = socket.getInputStream();
                                
                                byte[] buffer = new byte[102400];
                                int len;
                                while ((len = inputStream.read(buffer)) != -1){
                                    String text = new String(buffer,0,len);
                                    System.out.println("�յ�������Ϊ��" + text);
                                    String []split=text.split("//");
                                    
                                    insertMysql(split);
                                   
                                    sendMsgAll(text);
                                }
                                
                                
                            }catch (Exception e){
                                System.out.println("������ϢΪ��" + e.getMessage());
                            }finally {
                                synchronized (this){
                                    System.out.println("�ر����ӣ�" + address);
                                    clientList.remove(address);
                                }
                            }
                        }
                    }).start();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void Stop(){
            isExit = true;
            if (server != null){
                try {
                    server.close();
                    System.out.println("�ѹر�server");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static ServerThread startServer(){
        System.out.println("��������");
        if (serverThread != null){
            showDown();
        }
        serverThread = new ServerThread();
        new Thread(serverThread).start();
        System.out.println("��������ɹ�");
        return serverThread;
    }

    
    public static void showDown(){
        for (Socket socket : clientList.values()) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        serverThread.Stop();
        clientList.clear();
    }

    public static int insertMysql(String []str) throws SQLException{
    	
    	 mybean.setUserId(str[0]);
         mybean.setChatContent(str[1]);
         mybean.setTime(str[2]);
         mybean.setToUserId("444");
         
         String insertSql="insert into "+DBUtil.Table_Chat+" (userId,chatTime,toUserId,chatContent)" +
         		" values('"+mybean.getUserId()+"','"+mybean.getTime()+"','"+mybean.getToUserId()+
         		"','"+mybean.getChatContent()+"')";
         
         System.out.println(insertSql);
         return DBUtil.update(insertSql);
    }
    
    public static boolean sendMsgAll(String msg){
        try {
            for (Socket socket : clientList.values()) {
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(msg.getBytes("utf-8"));
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

}