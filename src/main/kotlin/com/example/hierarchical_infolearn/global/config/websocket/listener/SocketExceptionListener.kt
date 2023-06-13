package com.example.hierarchical_infolearn.global.config.websocket.listener

import com.corundumstudio.socketio.SocketIOClient
import com.corundumstudio.socketio.listener.ExceptionListener
import com.example.hierarchical_infolearn.global.config.websocket.property.SocketProperties
import com.example.hierarchical_infolearn.global.error.ErrorCode
import com.example.hierarchical_infolearn.global.error.data.ErrorResponse
import com.example.hierarchical_infolearn.global.error.data.GlobalException
import io.netty.channel.ChannelHandlerContext
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException


class SocketExceptionListener: ExceptionListener {

    override fun onEventException(e: Exception, args: MutableList<Any>, client: SocketIOClient) {
        runExceptionHandling(e, client)
    }

    override fun onDisconnectException(e: Exception, client: SocketIOClient) {
        runExceptionHandling(e, client)
    }

    override fun onConnectException(e: Exception, client: SocketIOClient) {
        runExceptionHandling(e, client)
        client.disconnect()
    }

    override fun onPingException(e: Exception, client: SocketIOClient) {
        runExceptionHandling(e, client)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, e: Throwable) = false

    private fun runExceptionHandling(e: Exception, client: SocketIOClient) {

        val response = when(e.cause) {
            is GlobalException -> ErrorResponse.of((e.cause as GlobalException).errorCode)
            is BindException -> ErrorResponse.of(e.cause as BindException)
            is HttpMessageNotReadableException -> ErrorResponse.of(e.cause as HttpMessageNotReadableException)
            else -> ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR)
        }

        e.cause?.printStackTrace()

        client.sendEvent(SocketProperties.ERROR, response)
    }

}
