import React, { useState } from "react";

function ContactInformation(){

  const [input,setInput] = useState({
    namePerson: sessionStorage.getItem('namePerson') ||'',
    adress: sessionStorage.getItem('adress') ||'',
    city: sessionStorage.getItem('city') ||''
  })
  
  const handleInputChange = event => {
    const {name,value} = event.target;
    sessionStorage.setItem(name, value);
    setInput({
      ...input,
      [name]: value
    });
  }

  return (
    <div className="contact">
      <input value={input.namePerson} name="namePerson" maxLength="35" type="text" placeholder="Full name" onChange={handleInputChange}/><br/>
      <input value={input.adress} name="adress" maxLength="35" type="text" placeholder="Adress" onChange={handleInputChange}/><br/>
      <input value={input.city} name="city" maxLength="35" type="text" placeholder="City" onChange={handleInputChange}/><br/>
    </div>
  )
}

export default ContactInformation;
