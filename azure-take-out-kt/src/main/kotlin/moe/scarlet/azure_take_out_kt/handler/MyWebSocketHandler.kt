package moe.scarlet.azure_take_out_kt.handler

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import moe.scarlet.azure_take_out_kt.exception.ExceptionType
import moe.scarlet.azure_take_out_kt.extension.logger
import moe.scarlet.azure_take_out_kt.pojo.Orders
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.ConcurrentHashMap

@Component
class MyWebSocketHandler : TextWebSocketHandler() {

    private val sessions = ConcurrentHashMap<String, WebSocketSession>()

    private val json = Json { ignoreUnknownKeys = true }

    private val WebSocketSession.sid: String
        get() = uri?.path?.split("/")?.last() ?: throw ExceptionType.WEBSOCKET_ERROR.asException()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        val sid = session.sid
        sessions[sid] = session
        logger.info("WebSocket新连接来自: $sid")
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        logger.info("WebSocket新消息: ${message.payload} 来自 ${session.sid}")
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val sid = session.sid
        sessions.remove(sid)
        logger.info("WebSocket断开连接: $sid")
    }

    fun broadcast(message: String) =
        sessions.values.filter { it.isOpen }.forEach { it.sendMessage(TextMessage(message)) }

    fun reminder(orders: Orders) =
        broadcast(json.encodeToString(mapOf(
            "type" to 2,
            "orderId" to orders.id,
            "content" to "订单号: ${orders.number}",
        )))

}
