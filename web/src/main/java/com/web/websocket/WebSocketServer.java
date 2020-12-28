package com.web.websocket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ServerEndpoint(value = "/websocket/{id}")
public class WebSocketServer {
    private static AtomicInteger onlineCount = new AtomicInteger();
    private static ConcurrentHashMap<String, Session> sessionPools = new ConcurrentHashMap<>();
    private static Logger log = LogManager.getLogger(WebSocketServer.class);

    /**
     * 连接建立成功调用的方法
     * */
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) throws IOException {
        sessionPools.put(id, session);
        log.info("用户"+id+"加入！当前在线人数为" + addOnlineCount());
        sendMessage(session,"连接成功");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(@PathParam(value = "id") String id) {
        sessionPools.remove(id);
        log.info("有一连接关闭！当前在线人数为" + subOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     * */
    @OnMessage
    public void onMessage(@PathParam(value = "id") String id,String message,Session session) throws IOException {
        System.err.println("来自客户端的消息:" + message);
        log.info("来自客户端的消息:" + message);
        //可以自己约定字符串内容，比如 内容|0 表示信息群发，内容|X 表示信息发给id为X的用户
        String sendMessage = message.split("[|]")[0];
        String sendUserId = message.split("[|]")[1];
        if(sendUserId.equals("0")){
            sendtoAll(sendMessage);
        } else{
            sendtoUser(sendMessage,sendUserId,id);
        }
    }

    /**
     *会话发生错误调用的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }

    /**
     * 发送信息给指定ID用户，如果用户不在线则返回不在线信息给自己
     */
    private void sendtoUser(String message,String sendUserId,String id) throws IOException {
        Session session = sessionPools.get(sendUserId);
        if (session != null) {
            if(!id.equals(sendUserId)){
                sendMessage(session, "用户" + id + "发来消息：" + " <br/> " + message);
            }else{
                sendMessage(session,message);
            }
        } else {
            //如果用户不在线则返回不在线信息给自己
            sendtoUser("当前用户不在线",id,id);
        }
    }

    /**
     * 发送信息给所有人
     */
    private void sendtoAll(String message) throws IOException {
        for (String key : sessionPools.keySet()) {
            try {
                sendMessage(sessionPools.get(key),message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送信息
     */
    private void sendMessage(Session session, String message) throws IOException {
        if(session != null){
            synchronized (session) {
                session.getBasicRemote().sendText(message);
            }
        }
    }

    private static synchronized int getOnlineCount() {
        return onlineCount.get();
    }
    private static synchronized int addOnlineCount() { return onlineCount.incrementAndGet(); }
    private static synchronized int subOnlineCount() {
        return onlineCount.decrementAndGet();
    }

}