package uz.pdp.java1.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Video;
import uz.pdp.java1.enums.CodeMessageType;
import uz.pdp.java1.todo.CodeMessage;

import java.util.List;

public class FileInfoService {
    public CodeMessage getFileInfo(Message message) {
        Long chatId = message.getChatId();

        CodeMessage codeMessage = new CodeMessage();
        codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);

        if (message.getPhoto() != null) {
            String photo = this.show_photo_detail(message.getPhoto());
            sendMessage.setText(photo);
        } else if (message.getVideo() != null) {
            String video = this.show_video_detail(message.getVideo());
            sendMessage.setText(video);
        } else if (message.getContact() != null) {
            String contact = this.show_contact_detail(message.getContact());
            sendMessage.setText(contact);
        } else {
            sendMessage.setText("NOT FOUND");
        }
        codeMessage.setSendMessage(sendMessage);
        return codeMessage;
    }

    private String show_photo_detail(List<PhotoSize> photoSizeList) {
        String s = "=============== PHOTO INFO ===============\n";
        for (PhotoSize photoSize : photoSizeList) {
            s += "Size: " + photoSize.getFileSize() + "\nId: " + photoSize.getFileId();
        }
        return s;
    }

    private String show_video_detail(Video video) {
        String s = "=============== VIDEO INFO ===============\n";
        s += video.toString();
        return s;
    }

    private String show_contact_detail(Contact contact) {
        String s = "=============== CONTACT INFO ===============\n";
        s += "First name: " +  contact.getFirstName() + "\nLast name: " + contact.getLastName();
        return s;
    }
}
