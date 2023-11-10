import React from 'react';
import styles from '../../assets/scss/component/guestbook/MessageList.scss';
import Message from './Message';

function MessageList({messageList}) {
    return (
        <div className={styles.MessageList}>
            {
                messageList.map(msg=>{<Message message={msg}/>})
            }
        </div>
    );
}

export default MessageList;