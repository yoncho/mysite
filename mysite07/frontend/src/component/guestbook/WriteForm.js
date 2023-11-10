import React from 'react';
import styles from '../../assets/scss/component/guestbook/WriteForm.scss';
function WriteForm({addMessage}) {
    const submitEvent = (e)=>{
        e.preventDefault();
        const message = {
            name: (e.target.author.value),
            password: (e.target.password.value),
            contents: (e.target.contents.value)
        }
        if(addMessage(message)){
            e.target.author.value = "";
            e.target.password.value = "";
            e.target.contents.value = "";
        }
    }
    return (
        <form className={styles.WriteForm} onSubmit={(e)=>submitEvent(e)}>
            <input name='author' type="text" placeholder='작성자'></input>
            <textarea name='contents' placeholder='내용 입력'></textarea>
            <input name='password' type="password" placeholder='비밀번호'></input>
            <input type="submit"></input>
        </form>
    );
}

export default WriteForm;