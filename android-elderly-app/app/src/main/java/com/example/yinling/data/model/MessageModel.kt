package com.example.yinling.data.model

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("id")
    val id: String,

    @SerializedName("senderId")
    val senderId: String,

    @SerializedName("senderName")
    val senderName: String,

    @SerializedName("senderAvatar")
    val senderAvatar: String? = null,

    @SerializedName("receiverId")
    val receiverId: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("messageType")
    val messageType: String, // text, image, audio, video

    @SerializedName("timestamp")
    val timestamp: String,

    @SerializedName("isRead")
    val isRead: Boolean,
)

data class MessageResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<Message>? = null,
)

data class SendMessageRequest(
    @SerializedName("receiverId")
    val receiverId: String,

    @SerializedName("content")
    val content: String,

    @SerializedName("messageType")
    val messageType: String = "text",

    @SerializedName("timestamp")
    val timestamp: String,
)

data class SendMessageResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: Message? = null,
)

data class MessageSession(
    @SerializedName("id")
    val id: String,

    @SerializedName("userId")
    val userId: String,

    @SerializedName("contactId")
    val contactId: String,

    @SerializedName("contactName")
    val contactName: String,

    @SerializedName("contactAvatar")
    val contactAvatar: String? = null,

    @SerializedName("lastMessage")
    val lastMessage: String,

    @SerializedName("lastMessageTime")
    val lastMessageTime: String,

    @SerializedName("unreadCount")
    val unreadCount: Int,
)

data class MessageSessionResponse(
    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("data")
    val data: List<MessageSession>? = null,
)
