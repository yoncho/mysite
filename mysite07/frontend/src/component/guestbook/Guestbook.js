import React, { useEffect, useState } from 'react';
import {MySiteLayout} from "../../layout";
import styles from '../../assets/scss/component/guestbook/Guestbook.scss';
import WriteForm from './WriteForm';
import MessageList from './MessageList';
function Guestbook(props) {
    const [messageList, setMessageList] = useState([]);

    useEffect(()=>{
        getMessageList();
    }, []);

    const addMessage = async(message)=>{
        try{
            const response = await fetch('/api/guestbook', {
                method: 'post',
                headers:{
                    'Content-Type':'application/json',
                    'Accepts': 'application/json'
                },
                body : JSON.stringify(message)
            });

            if(!response.ok){
                throw new Error(`${reponse.status} ${response.message}`);
            }

            const json = await response.json();
            if (json.result !== 'success') {
                throw json.message;
            }
            return true;
        }catch(err){
            console.error(err);
            return false;
        }
    }
    const getMessageList = async()=>{
        try{
            const response = await fetch('/api/guestbook', {
                method: 'get',
                headers:{
                    'Accepts':'application/json'
                },
                body: null
            });

            if (!response.ok) {
                throw `${response.status} ${response.statusText}`;
            }

            // API success?
            const json = await response.json();
            if (json.result !== 'success') {
                throw json.message;
            }

            // Rendering(Update)
            setMessageList(json.data);

            console.log(json.data);
        }catch(err){
            console.error(err);
        }
    }
    return (
        <MySiteLayout>
            <div className={styles.Guestbook}>
                <h2>방명록</h2>
                <WriteForm addMessage={addMessage}/>
                <MessageList messageList={messageList}/>
            </div>
        </MySiteLayout>
    );
}

export default Guestbook;