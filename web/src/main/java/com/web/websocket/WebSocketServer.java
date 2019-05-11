package com.web.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint(value = "/websocket/{id}")//���ʷ���˵�url��ַ
public class WebSocketServer {
    private static int onlineCount = 0;
    private static ConcurrentHashMap<String, WebSocketServer> webSocketSet = new ConcurrentHashMap<>();

    //��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
    private Session session;
    private static Logger log = LogManager.getLogger(WebSocketServer.class);
    private String id = "";

    /**
     * ���ӽ����ɹ����õķ���
     * */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        this.session = session;
        this.id = id;//���յ�������Ϣ����Ա���
        webSocketSet.put(id, this);     //����set��
        addOnlineCount();           //��������1
        log.info("�û�"+id+"���룡��ǰ��������Ϊ" + getOnlineCount());
        try {
            sendMessage("���ӳɹ�");
        } catch (IOException e) {
            log.error("websocket IO�쳣");
        }
    }

    /**
     * ���ӹرյ��õķ���
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //��set��ɾ��
        subOnlineCount();           //��������1
        log.info("��һ���ӹرգ���ǰ��������Ϊ" + getOnlineCount());
    }

    /**
     * �յ��ͻ�����Ϣ����õķ���
     *
     * @param message �ͻ��˷��͹�������Ϣ
     * */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.err.println("���Կͻ��˵���Ϣ:" + message);
        log.info("���Կͻ��˵���Ϣ:" + message);
        //�����Լ�Լ���ַ������ݣ����� ����|0 ��ʾ��ϢȺ��������|X ��ʾ��Ϣ����idΪX���û�
        String sendMessage = message.split("[|]")[0];
        String sendUserId = message.split("[|]")[1];
        try {
            if(sendUserId.equals("0")){
                sendtoAll(sendMessage);
            } else{
                sendtoUser(sendMessage,sendUserId);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *�Ự����������õķ���
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("��������");
        error.printStackTrace();
    }

    /**
     * ������Ϣ��ָ��ID�û�������û��������򷵻ز�������Ϣ���Լ�
     * @param message
     * @param sendUserId
     * @throws IOException
     */
    public void sendtoUser(String message,String sendUserId) throws IOException {
        if (webSocketSet.get(sendUserId) != null) {
            if(!id.equals(sendUserId))
                webSocketSet.get(sendUserId).sendMessage( "�û�" + id + "������Ϣ��" + " <br/> " + message);
            else
                webSocketSet.get(sendUserId).sendMessage(message);
        } else {
            //����û��������򷵻ز�������Ϣ���Լ�
            sendtoUser("��ǰ�û�������",id);
        }
    }

    /**
     * ������Ϣ��������
     * @param message
     * @throws IOException
     */
    public void sendtoAll(String message) throws IOException {
        for (String key : webSocketSet.keySet()) {
            try {
                webSocketSet.get(key).sendMessage(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() { WebSocketServer.onlineCount++; }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static ConcurrentHashMap<String, WebSocketServer> getWebSocketSet() {
        return webSocketSet;
    }

    public static void setWebSocketSet(ConcurrentHashMap<String, WebSocketServer> webSocketSet) {
        WebSocketServer.webSocketSet = webSocketSet;
    }
}