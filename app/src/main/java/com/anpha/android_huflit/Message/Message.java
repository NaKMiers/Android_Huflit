package com.anpha.android_huflit.Message;

    public class Message {
        private String text;
        private boolean sentByUser;


    
        public Message(String text, boolean sentByUser) {
            super();
            this.text = text;
            this.sentByUser = sentByUser;
        }
    
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