"use client";
import CreateAvatar from "../../components/CreateAvatar";
import RecipeOverview from "../../components/RecipeOverview";
import UserInfo from "../../components/UserInfo";
import Image from "next/image";

import React, { useEffect, useState } from "react";
import Link from "next/link";
import { HiStar } from "react-icons/hi2";
import { GiExitDoor } from "react-icons/gi";
import StudentRecipesClient, { StudentRecipesClientError } from "../../utils/StudentRecipesClient";
import { useRouter } from "next/navigation";
import UpdateUserRequestDto from "../../utils/dto/UpdateUserRequestDto";
import { InputField } from "../../components/AddIngredientModal";

export default function Profile() {
  const [file, setFile] = useState<File>();
  const [filebase64, setFileBase64] = useState<string>("");
  const [firstName, setFirstName] = useState<string>();
  const [lastName, setLastName] = useState<string>();
  const [email, setEmail] = useState<string>();
  const [password, setPassword] = useState<string>();

  const recipeClient = new StudentRecipesClient();
  const router = useRouter();

  useEffect(() => {
    fetchInitialProfile();
  }, []);

  async function fetchInitialProfile() {
    try {
      const client = new StudentRecipesClient();
      const user = await client.getUser();
      setFirstName(user.firstName);
      setLastName(user.lastName);
      setEmail(user.email);
      // TODO: image
    } catch (e) {
      alert(e);
    }
  }

  async function logout(): Promise<void> {
    const recipeClient = new StudentRecipesClient();
    await recipeClient.logout();
  }

  const onFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    if (event.target.files) {
      convertFile(event.target.files);
      setFile(event.target.files[0]);
    }
  };

  async function handleSubmit() {
    event?.preventDefault();
    console.log("Submit clicked!");
    const user: UpdateUserRequestDto = {
      firstName: firstName,
      lastName: lastName,
      email: email,
      password: password,
    };

    try {
      if (file === undefined) throw new StudentRecipesClientError("Image missing!");
      const client = new StudentRecipesClient();
      await client.updateUserWithImage(user, file);
    } catch (error: any) {
      if (error instanceof StudentRecipesClientError) {
        const err = error as StudentRecipesClientError;
        alert(`Falsche eingabe: ${err.message}`);
        console.log(err.message);
      } else {
        alert(error);
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
      <form onSubmit={handleSubmit} className="flex flex-row justify-center mt-16">
        <div className="card card-side shadow-xl p-10">
          <figure>
            <div className="avatar">
              <div className="p-4">
                <div className="flex flex-col w-full space-x-4">
                  <div className="flex items-center w-32 aspect-square rounded-lg text-center justify-center bg-base-200">
                    {filebase64 ? (
                      <img className="object-cover rounded-lg" alt="recipe image" src={filebase64} />
                    ) : (
                      "Kein Bild ausgewählt"
                    )}
                  </div>
                  <div className="flex justify-center form-control w-full">
                    <label className="label">
                      <span className="label-text">Bild auswählen</span>
                    </label>
                    <input
                      type="file"
                      onChange={onFileChange}
                      accept="image/png, image/jpeg"
                      className="file-input file-input-bordered w-full"
                      required={true}
                    />
                  </div>
                </div>
              </div>
            </div>
          </figure>
          <div className="card-body">
            <div className="stats stats-vertical shadow">
              <div className="stat">
                <InputField
                  title={"Vorname"}
                  type={"text"}
                  placeholder="Vorname"
                  value={firstName ?? ""}
                  onChange={(value) => {
                    setFirstName(value);
                  }}
                />
                {/*                 <div className="stat-desc">Jan 1st - Feb 1st</div> */}
              </div>
              <div className="stat">
                <InputField
                  title={"Nachname"}
                  type={"text"}
                  placeholder="Nachname"
                  value={lastName ?? ""}
                  onChange={(value) => {
                    setLastName(value);
                  }}
                />
              </div>
              <div className="stat">
                <InputField
                  title={"E-Mail"}
                  type={"email"}
                  value={email ?? ""}
                  placeholder="Email"
                  onChange={(value) => {
                    setEmail(value);
                  }}
                />
              </div>
              <div className="stat">
                <InputField
                  title={"Password"}
                  type={"password"}
                  placeholder="Password"
                  value={password ?? ""}
                  onChange={(value) => {
                    setPassword(value);
                  }}
                />
              </div>
              <button type="submit" className="stat btn mt-2">
                speichern
              </button>
            </div>
          </div>
        </div>
      </form>
      <Link className="w-full flex justify-center" href="/">
        <button onClick={logout} className="btn gap-2 bg-custom-first mt-8 hover:bg-custom-second">
          Ausloggen
          <GiExitDoor size={24}></GiExitDoor>
        </button>
      </Link>
    </div>
  );
}
