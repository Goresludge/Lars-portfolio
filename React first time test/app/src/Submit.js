
import React, { useState } from "react";
import {GetData} from "./Utils/GetData";
import {ValidInputs} from "./Utils/ValidInputs"

function Submit(){

  const [input,setInput] = useState(sessionStorage.getItem('email') ||'');

  const handleInputChange = event => {
    const {name,value} = event.target;
    sessionStorage.setItem(name, value);
    setInput(value)
  }

  function validateEmail(email) {
    const res = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return res.test(String(email).toLowerCase());
  }
  
  const handleSendClick = () => {
    const list = GetData();
    let errorMsg = ValidInputs(list);
    if(!validateEmail(input)){
      errorMsg += "Invalid Email\n";
    }
    if(errorMsg.length === 0){
      let answer = window.confirm("Send invoice to: " + input);
      if(answer){
        alert("Invoice sent to:\n"+ input + "\n(Not implemented)")
      }
      else{
      }
    }
    else{
      alert(errorMsg);
    }
  }

  const handleClearClick = () => {
    let answer = window.confirm("Are you sure you want to clear the invoice?");
    if(answer){
      sessionStorage.clear();
      window.location.reload(false);
    }
    else{

    }
  }

  return(
    <div className="submitArea">
      <input value={input} name="email" maxLength="35" type="email" required placeholder="Email" onChange={handleInputChange}/>
      <button className="sendButton" onClick={handleSendClick}>Send</button>
      <button className="clearButton" onClick={handleClearClick}>Clear invoice</button>
    </div>
  );
}

export default Submit;