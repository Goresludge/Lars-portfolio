import React, { useState } from "react";

function AdditionalInformation(){

  const [input,setInput] = useState({
    notes: sessionStorage.getItem('notes') || '',
    date: sessionStorage.getItem('date') ||'',
    status: sessionStorage.getItem('status') ||'Outstanding'
  });
  
  const handleInputChange = event => {
    const {name,value} = event.target;
    sessionStorage.setItem(name, value);
    setInput({
      ...input,
      [name]: value
    });
  }

  return (
    <div className="footer">
      <textarea value={input.notes} name="notes" onChange={handleInputChange} placeholder="Input optional notes"/>
      <div className="dateAndStatus">
        Status:
      <select value={input.status} name="status" onChange={handleInputChange}>
        <option value="Outstanding">Outstanding</option>
        <option value="Paid">Paid</option>
        <option value="Late">Late</option>
      </select><br/><br/>
      Due date:
      <input value={input.date} type="date" name="date" onChange={handleInputChange}/>
      </div>
    </div>
  )
}

export default AdditionalInformation;