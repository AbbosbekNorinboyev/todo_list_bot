package uz.pdp.java1.controller;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import uz.pdp.java1.enums.CodeMessageType;
import uz.pdp.java1.enums.TodoItemType;
import uz.pdp.java1.repository.TodoRepository;
import uz.pdp.java1.todo.CodeMessage;
import uz.pdp.java1.todo.TodoItem;

import java.text.SimpleDateFormat;
import java.util.*;

public class TodoController {
    private Map<Long, TodoItem> todoItemStep = new HashMap<>();
    private TodoRepository todoRepository = new TodoRepository();
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("'Sana: 'dd.MM.yyyy\n'Vaqt: 'HH:mm:ss.SSSS");

    public CodeMessage handle(String text, Long chatId, Integer messageId) {
        CodeMessage codeMessage = new CodeMessage();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        codeMessage.setSendMessage(sendMessage);

        if (text.startsWith("/todo/")) {
            String[] commandList = text.split("/");
            String command = commandList[2];
            if (command.equalsIgnoreCase("list")) {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(chatId);
                editMessageText.setMessageId(messageId);
                editMessageText.setText("List");
                editMessageText.setParseMode("HTML");

                List<TodoItem> todoItemList = this.todoRepository.getTodoList(chatId);
                StringBuilder stringBuilder = new StringBuilder("");
                if (todoItemList == null || todoItemList.isEmpty()) {
                    stringBuilder.append("You do not have any todo list");
                } else {
                    int counter = 1;
                    for (TodoItem todoItem : todoItemList) {
                        stringBuilder.append("<b>" + counter + "</b>\n");
                        stringBuilder.append("Title: " + todoItem.getTitle() + "\n");
                        stringBuilder.append("Content: " + todoItem.getContent() + "\n");
                        stringBuilder.append(simpleDateFormat.format(todoItem.getCreatedDate()) + "\n");
                        stringBuilder.append("/todo_edit_" + todoItem.getId() + "\n\n");
                        counter++;
                    }
                }

                InlineKeyboardButton keyboardButton = new InlineKeyboardButton();
                keyboardButton.setText("Go to Menu");
                keyboardButton.setCallbackData("menu");
                List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
                keyboardRow.add(keyboardButton);
                List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
                keyboardRowList.add(keyboardRow);
                InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                markup.setKeyboard(keyboardRowList);
                editMessageText.setReplyMarkup(markup);

                editMessageText.setText(stringBuilder.toString());

                codeMessage.setEditMessageText(editMessageText);
                codeMessage.setCodeMessageType(CodeMessageType.EDIT);

            } else if (command.equalsIgnoreCase("create")) {
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(chatId);
                editMessageText.setMessageId(messageId);
                editMessageText.setText("<b>Send title</b>");
                editMessageText.setParseMode("HTML");

                TodoItem todoItem = new TodoItem();
                todoItem.setId(String.valueOf(messageId));
                todoItem.setUserId(chatId);
                todoItem.setType(TodoItemType.TITLE);

                this.todoItemStep.put(chatId, todoItem);

                codeMessage.setEditMessageText(editMessageText);
                codeMessage.setCodeMessageType(CodeMessageType.EDIT);
            } else if (command.equalsIgnoreCase("update")) {
                command = commandList[3];
                String id = commandList[4];

                TodoItem todoItem = this.todoRepository.getItem(chatId, id);
                EditMessageText editMessageText = new EditMessageText();
                editMessageText.setChatId(chatId);
                editMessageText.setMessageId(messageId);
                editMessageText.setParseMode("Markdown");

                if (todoItem == null) {
                    editMessageText.setText("Not found");
                    codeMessage.setEditMessageText(editMessageText);
                    codeMessage.setCodeMessageType(CodeMessageType.EDIT);
                } else {
                    if (command.equalsIgnoreCase("title")) {
                        editMessageText.setText("*Current title:* " + todoItem.getTitle() +
                                "\nPlease send new Title");

                        InlineKeyboardButton cancel = new InlineKeyboardButton();
                        cancel.setText("Cancel");
                        cancel.setCallbackData("/todo/cancel");
                        List<InlineKeyboardButton> row = new ArrayList<>();
                        row.add(cancel);
                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                        rowList.add(row);
                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                        markup.setKeyboard(rowList);
                        editMessageText.setReplyMarkup(markup);

                        codeMessage.setEditMessageText(editMessageText);
                        codeMessage.setCodeMessageType(CodeMessageType.EDIT);
                        todoItem.setType(TodoItemType.UPDATE_TITLE);
                        todoItemStep.put(chatId, todoItem);

                    } else if (command.equalsIgnoreCase("content")) {
                        editMessageText.setText("*Current content:* " + todoItem.getContent() +
                                "\nPlease send new Content");

                        InlineKeyboardButton cancel = new InlineKeyboardButton();
                        cancel.setText("Cancel");
                        cancel.setCallbackData("/todo/cancel");
                        List<InlineKeyboardButton> row = new ArrayList<>();
                        row.add(cancel);
                        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                        rowList.add(row);
                        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
                        markup.setKeyboard(rowList);
                        editMessageText.setReplyMarkup(markup);

                        codeMessage.setEditMessageText(editMessageText);
                        codeMessage.setCodeMessageType(CodeMessageType.EDIT);
                        todoItem.setType(TodoItemType.UPDATE_CONTENT);
                        todoItemStep.put(chatId, todoItem);
                    }
                }
            } else if (command.equalsIgnoreCase("cancel")) {
                this.todoItemStep.remove(chatId);
                sendMessage.setChatId(chatId);
                sendMessage.setText("Update Was Cancelled");

                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText("\uD83D\uDCCB " + "Todo List");
                button1.setCallbackData("/todo/list");
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText("Go to Menu");
                button2.setCallbackData("menu");
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(button1);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(button2);
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(row1);
                rowList.add(row2);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(rowList);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                codeMessage.setSendMessage(sendMessage);
                codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);
            } else if (command.equalsIgnoreCase("delete")) {
                String id = commandList[3];
                boolean delete = this.todoRepository.delete(chatId, id);
                sendMessage.setChatId(chatId);
                if (delete) {
                    sendMessage.setText("Todo was deleted");
                } else {
                    sendMessage.setText("<b>ERROR</b>");
                    sendMessage.setParseMode("HTML");
                }

                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText("\uD83D\uDCCB " + "Todo List");
                button1.setCallbackData("/todo/list");
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText("Go to Menu");
                button2.setCallbackData("menu");
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(button1);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(button2);
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(row1);
                rowList.add(row2);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(rowList);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

                codeMessage.setSendMessage(sendMessage);
                codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);

            }
            return codeMessage;
        }

        if (text.startsWith("/todo_")) {
            String todoId = text.split("/todo_edit_")[1];
            TodoItem item = this.todoRepository.getItem(chatId, todoId);
            if (item == null) {
                sendMessage.setText("Voy jo'ra bu qatdan keldi. No ID found");
            } else {
                sendMessage.setText(item.getTitle() + "\n" + item.getContent() + "\n" +
                        "_" + simpleDateFormat.format(item.getCreatedDate()) + "_");
                sendMessage.setParseMode("Markdown");

                InlineKeyboardButton updateTitle = new InlineKeyboardButton();
                updateTitle.setText("Update Title");
                updateTitle.setCallbackData("/todo/update/title/" + item.getId());
                InlineKeyboardButton updateContent = new InlineKeyboardButton();
                updateContent.setText("Update Content");
                updateContent.setCallbackData("/todo/update/content/" + item.getId());
                InlineKeyboardButton delete = new InlineKeyboardButton();
                delete.setText("❌ " + "Delete");
                delete.setCallbackData("/todo/delete/" + item.getId());
                InlineKeyboardButton todoList = new InlineKeyboardButton();
                todoList.setText("\uD83D\uDCCB " + "Todo List");
                todoList.setCallbackData("/todo/list");
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(updateTitle);
                row1.add(updateContent);
                row1.add(delete);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(todoList);
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(row1);
                rowList.add(row2);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(rowList);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            }
            codeMessage.setSendMessage(sendMessage);
            codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);
            return codeMessage;
        }

        if (this.todoItemStep.containsKey(chatId)) {
            TodoItem todoItem = this.todoItemStep.get(chatId);
            sendMessage.setParseMode("HTML");
            sendMessage.setChatId(chatId);
            codeMessage.setSendMessage(sendMessage);
            codeMessage.setCodeMessageType(CodeMessageType.MESSAGE);
            if (todoItem.getType().equals(TodoItemType.TITLE)) {
                todoItem.setTitle(text);
                sendMessage.setText("<b>Title:</b> " + todoItem.getTitle() + "\n<b>Send content:</b> ");
                todoItem.setType(TodoItemType.CONTENT);
            } else if (todoItem.getType().equals(TodoItemType.CONTENT)) {
                todoItem.setContent(text);
                todoItem.setCreatedDate(new Date());
                todoItem.setType(TodoItemType.FINISHED);
                int add = this.todoRepository.add(chatId, todoItem);
                this.todoItemStep.remove(chatId);
                sendMessage.setText("Item counter: " + add + "\n<b>Title:</b> " + todoItem.getTitle() +
                        "\n<b>Content:</b> " + todoItem.getContent() + "\n<u>Create todo finished</u>");

                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText("\uD83D\uDCCB " + "Todo List");
                button1.setCallbackData("/todo/list");
                InlineKeyboardButton button2 = new InlineKeyboardButton();
                button2.setText("Go to Menu");
                button2.setCallbackData("menu");
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(button1);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(button2);
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(row1);
                rowList.add(row2);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(rowList);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            } else if (todoItem.getType().equals(TodoItemType.UPDATE_TITLE)) {
                todoItem.setTitle(text);
                this.todoItemStep.remove(chatId);
                sendMessage.setText("<b>Title:</b> " + todoItem.getTitle() +
                        "\n<b>Content:</b> " + todoItem.getContent());

                InlineKeyboardButton updateTitle = new InlineKeyboardButton();
                updateTitle.setText("Update Title");
                updateTitle.setCallbackData("/todo/update/title/" + todoItem.getId());
                InlineKeyboardButton updateContent = new InlineKeyboardButton();
                updateContent.setText("Update Content");
                updateContent.setCallbackData("/todo/update/content/" + todoItem.getId());
                InlineKeyboardButton delete = new InlineKeyboardButton();
                delete.setText("❌ " + "Delete");
                delete.setCallbackData("/todo/delete/" + todoItem.getId());
                InlineKeyboardButton todoList = new InlineKeyboardButton();
                todoList.setText("\uD83D\uDCCB " + "Todo List");
                todoList.setCallbackData("/todo/list");
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(updateTitle);
                row1.add(updateContent);
                row1.add(delete);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(todoList);
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(row1);
                rowList.add(row2);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(rowList);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            } else if (todoItem.getType().equals(TodoItemType.UPDATE_CONTENT)) {
                todoItem.setContent(text);
                this.todoItemStep.remove(chatId);
                sendMessage.setText("<b>Title:</b> " + todoItem.getTitle() +
                        "\n<b>Content:</b> " + todoItem.getContent());

                InlineKeyboardButton updateTitle = new InlineKeyboardButton();
                updateTitle.setText("Update Title");
                updateTitle.setCallbackData("/todo/update/title/" + todoItem.getId());
                InlineKeyboardButton updateContent = new InlineKeyboardButton();
                updateContent.setText("Update Content");
                updateContent.setCallbackData("/todo/update/content/" + todoItem.getId());
                InlineKeyboardButton delete = new InlineKeyboardButton();
                delete.setText("❌ " + "Delete");
                delete.setCallbackData("/todo/delete/" + todoItem.getId());
                InlineKeyboardButton todoList = new InlineKeyboardButton();
                todoList.setText("\uD83D\uDCCB " + "Todo List");
                todoList.setCallbackData("/todo/list");
                List<InlineKeyboardButton> row1 = new ArrayList<>();
                row1.add(updateTitle);
                row1.add(updateContent);
                row1.add(delete);
                List<InlineKeyboardButton> row2 = new ArrayList<>();
                row2.add(todoList);
                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(row1);
                rowList.add(row2);
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                inlineKeyboardMarkup.setKeyboard(rowList);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);

            }
        }
        return codeMessage;
    }

    public Map<Long, TodoItem> getTodoItemStep() {
        return todoItemStep;
    }

    public void setTodoItemStep(Map<Long, TodoItem> todoItemStep) {
        this.todoItemStep = todoItemStep;
    }
}
