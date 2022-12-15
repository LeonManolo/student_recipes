"use client";
import CreateAvatar from "../../components/CreateAvatar";
import RecipeOverview from "../../components/RecipeOverview";
import UserInfo from "../../components/UserInfo";
import Image from "next/image";

import React, { useState } from "react";
import Link from "next/link";
import { HiStar } from "react-icons/hi2";
import { GiExitDoor } from "react-icons/gi";
import StudentRecipesClient from "../../utils/StudentRecipesClient";
import { useRouter } from "next/navigation";

export default function Profile() {
  const [file, setFile] = useState<File>();
  const [filebase64, setFileBase64] = useState<string>("");
  const recipeClient = new StudentRecipesClient();
  const router = useRouter();

  async function logout(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    await recipeClient.logout();
  }

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      convertFile(event.target.files);
      setFile(event.target.files[0]);
    }
  };

  async function onSubmit(event: React.FormEvent) {
    event.preventDefault();

    const formData = new FormData();
    if (file != undefined) {
      formData.append("file", file);

      const result = await fetch("https://sea-turtle-app-hqisk.ondigitalocean.app/api/files", {
        method: "POST",
        body: formData,
      });
      if (result.body != null) {
        const json = await result.json();
        console.log(json);
      }
    }
  }

  function convertFile(files: FileList | null) {
    if (files) {
      const fileRef = files[0] || "";
      const fileType: string = fileRef.type || "";
      console.log("This file upload is of type:", fileType);
      const reader = new FileReader();
      reader.readAsBinaryString(fileRef);
      reader.onload = (ev: any) => {
        // convert it to base64
        setFileBase64(`data:${fileType};base64,${btoa(ev.target.result)}`);
      };
    }
  }

  return (
    <div>
      <div>
        <div className="flex flex-row justify-center mt-16">
          <div className="card card-side shadow-xl p-10">
            <figure>
              <div className="avatar">
                <div className="p-4">
                  <div className="w-1/2 rounded">
                    {filebase64 && <Image
                      className="rounded border-double border-8"
                      src={filebase64}
                      alt="Profile Picture"
                      width={300}
                      height={300}
                    />}
                  </div>
                  <form onSubmit={onSubmit} className="flex flex-col">
                    <input type="file" onChange={onChange} className="my-1" />
                    <button className="btn bg-custom-first hover:bg-custom-second">Hochladen</button>
                    {/*  {filebase64 && <img src={filebase64} />} */}
                  </form>
                  <div className="absolute bottom-8 left-1">
                    <Link href="/favorites">
                      <button className="btn gap-2 bg-custom-first hover:bg-custom-second">
                        Favoriten
                        <HiStar size={24} />
                      </button>
                    </Link>
                  </div>
                </div>
              </div>
            </figure>
            <div className="card-body">
              <div className="stats stats-vertical shadow">
                <div className="stat">
                  <div className="stat-title">Vorname: </div>
                  <div className="stat-value">firstName</div>
                  {/*                 <div className="stat-desc">Jan 1st - Feb 1st</div> */}
                </div>
                <div className="stat">
                  <div className="stat-title">Nachname: </div>
                  <div className="stat-value">lastName</div>
                </div>
                <div className="stat">
                  <div className="stat-title">E-Mail: </div>
                  <div className="stat-value">hans@mustermann.de</div>
                </div>
              </div>
              <Link href="/">
                <button onClick={logout} className="btn gap-2 bg-custom-first mt-8 hover:bg-custom-second">
                  Ausloggen
                  <GiExitDoor size={24}></GiExitDoor>
                </button>
              </Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
