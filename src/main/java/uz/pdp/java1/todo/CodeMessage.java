package uz.pdp.java1.todo;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import uz.pdp.java1.enums.CodeMessageType;

public class CodeMessage {
    private CodeMessageType codeMessageType;
    private SendMessage sendMessage;
    private EditMessageText editMessageText;
    private SendVideo sendVideo;
    private SendPhoto sendPhoto;

    public CodeMessageType getCodeMessageType() {
        return codeMessageType;
    }

    public void setCodeMessageType(CodeMessageType codeMessageType) {
        this.codeMessageType = codeMessageType;
    }

    public SendMessage getSendMessage() {
        return sendMessage;
    }

    public void setSendMessage(SendMessage sendMessage) {
        this.sendMessage = sendMessage;
    }

    public EditMessageText getEditMessageText() {
        return editMessageText;
    }

    public void setEditMessageText(EditMessageText editMessageText) {
        this.editMessageText = editMessageText;
    }

    public SendVideo getSendVideo() {
        return sendVideo;
    }

    public void setSendVideo(SendVideo sendVideo) {
        this.sendVideo = sendVideo;
    }

    public SendPhoto getSendPhoto() {
        return sendPhoto;
    }

    public void setSendPhoto(SendPhoto sendPhoto) {
        this.sendPhoto = sendPhoto;
    }
}
