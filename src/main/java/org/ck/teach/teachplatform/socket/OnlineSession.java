package org.ck.teach.teachplatform.socket;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chen.chao
 * @version 1.0
 * @date 2019/12/30 16:20
 * @description
 */
@Slf4j
@ServerEndpoint(value = "/websocket/open/{uid}")
public class OnlineSession {


    private static final AtomicInteger OnlineCount = new AtomicInteger(0);

    private static CopyOnWriteArraySet<OnlineSession> webSocketSet = new CopyOnWriteArraySet<OnlineSession>();

    private Session session;


    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("userId") String userId, Session session) {
        log.info("新客户端连入，用户id：" + userId);
        this.session = session;
        webSocketSet.add(this);
        if (userId != null) {
            List<String> totalPushMsgs = new ArrayList<String>();
            if (totalPushMsgs != null && !totalPushMsgs.isEmpty()) {
                totalPushMsgs.forEach(e -> sendMessage(e));
            }
        }

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("一个客户端关闭连接");
        webSocketSet.remove(this);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户发送过来的消息为：" + message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket出现错误");
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        try {
            this.session.getBasicRemote().sendText(message);
            log.info("推送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发自定义消息
     */
    public static void sendInfo(String message) throws IOException {
        for (OnlineSession productWebSocket : webSocketSet) {
            productWebSocket.sendMessage(message);
        }
    }


}
