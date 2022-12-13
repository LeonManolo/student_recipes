"use client"
import CreateAvatar from '../../components/CreateAvatar';
import RecipeOverview from '../../components/RecipeOverview';
import UserInfo from '../../components/UserInfo';
/* export default async function Profile() {
    return (
        <div>
            <div className='flex flex-row justify-center'>
                <CreateAvatar />
            </div>
        </div>
    );
} */

import React, { useState } from 'react';

export default function Profile() {
    const [file, setFile] = useState<File>();
    const [filebase64, setFileBase64] = useState<string>("")

    const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        if (event.target.files) {
            convertFile(event.target.files)
            setFile(event.target.files[0]);
        }
    };

    async function onSubmit(event: React.FormEvent) {
        event.preventDefault();

        const formData = new FormData();
        if (file != undefined) {
            formData.append('file', file);

            const result = await fetch("https://sea-turtle-app-hqisk.ondigitalocean.app/api/files", { method: "POST", body: formData })
            if (result.body != null) {
                const json = await result.json()
                console.log(json)
            }
        }
    };
    

    function convertFile(files: FileList | null) {
        if (files) {
            const fileRef = files[0] || ""
            const fileType: string = fileRef.type || ""
            console.log("This file upload is of type:", fileType)
            const reader = new FileReader()
            reader.readAsBinaryString(fileRef)
            reader.onload = (ev: any) => {
                // convert it to base64
                setFileBase64(`data:${fileType};base64,${btoa(ev.target.result)}`)
            }
        }
    }

    return (
        <div>
            <form onSubmit={onSubmit}>
                <input type="file" onChange={onChange} />
                <button>
                    Upload
                </button>
                {filebase64 && <img src={filebase64} />}
            </form>
        </div>
    );
};

