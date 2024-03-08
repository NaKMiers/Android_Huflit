package com.anpha.android_huflit.Message;
//Clas đại diện cho tin nhắn bằng chữ
    public class Message {
        //Tin nhắn kiểu chuỗi
        private String text;
        //Biến boolean để xác định tin nhắn do người dùng hay AI gửi
        private boolean sentByUser;


        //Constructor (true thì do người dùng gửi, false thì do AI)
        public Message(String text, boolean sentByUser) {
            super();
            this.text = text;
            this.sentByUser = sentByUser;
        }
        //Các getter và setter
        public String getText() {
            return text;
        }
    
        public void setText(String text) {
            this.text = text;
        }
    
        public boolean isSentByUser() {
            return sentByUser;
        }
    
        public void setSentByUser(boolean sentByUser) {
            this.sentByUser = sentByUser;
        }
    }