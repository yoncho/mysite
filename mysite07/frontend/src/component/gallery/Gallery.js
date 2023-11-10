import React, {useState, useEffect} from 'react';
import {MySiteLayout} from "../../layout";
import Header from "./Header";
import ImageList from "./ImageList";
import styles from '../../assets/scss/component/gallery/Gallery.scss';
import data from '../../assets/json/data';

export default function Index() {
    const [imageList, setImageList] = useState([]);
    
    useEffect(()=>{
        getImageList();
    }, []);
    const removeImage = async(no) =>{
        try{
            const response = await fetch(`/api/gallery/${no}`, {
                method: 'delete',
                headers: {
                    'Content-Type': 'application/json', 
                    'Accept': 'application/json'
                },
                body:null
            });

            if(!response.ok){
                throw `${response.status} ${response.statusText}`;
            }

            const updateImageList = imageList.filter(x=> {
                if(x.no !== no) return x;
            })
            setImageList(updateImageList);
        }catch(err){
            console.error(err);
        }
    }
    const addImage = async (comment, file) => {
        try {
            // Create FormData
            const formData = new FormData();
            formData.append('comments', comment);
            formData.append('file', file);

            // Post
            const response = await fetch(`/api/gallery`, {
                method: 'post',
                body: formData
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
            setImageList([json.data, ...imageList]);
            
            return true;

        } catch (err) {
            console.error(err);
            return false;
        }
    };

    const getImageList = async () => {

        try {
            // Get
            const response = await fetch(`/api/gallery`, {
                method: 'get',
                headers: {
                    'Content-Type': 'application/json', 
                    'Accept': 'application/json'
                },
                body: null
            });

            if (!response.ok) {
                throw `${response.status} ${response.statusText}`;
            }
            console.log(response);
            // API success?
            const json = await response.json();
            if (json.result !== 'success') {
                throw json.message;
            }
            console.log(json.data);
            setImageList(json.data);
        } catch (err) {
            console.error(err);
        }
    };


    return (
        <MySiteLayout>
            <div className={styles.Gallery}>
                <Header addImage={addImage}/>
                <ImageList imageList={imageList} removeImage={removeImage}/>
            </div>
        </MySiteLayout>
    )
}