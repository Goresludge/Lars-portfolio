import React from "react";
import {GetData} from "./Utils/GetData";
import Submit from "./Submit"

const outputItems = list => {

  let displayTotal = true;

  if(list === null){
    list = ["","","",""];
    displayTotal = false;
  }

  const items = []
  let i = 0;
  list.forEach(element => {
    element.key = i;
    i++;
    items.push(Object.values(element));
  });

  return(
    <div>{items.map(item => (
      <div key={item} className="outputItems">
        <div className="prevItem">{item[0]}</div>
        <div className="prevQty">{item[1]}</div>
        <div className="prevPrice">{item[2]}</div>
        <div className="prevSum">{item[3]}</div>
      </div>
    ))}
    {displayTotal &&  <div className="totalSum">Total: {sessionStorage.getItem('totalSum')}</div>}
    </div>
  )
}

function DisplayPreview(){
  const list = GetData();
  return(
    <div>
      <div className="contact">
        {list[0] && <div>{list[0]}</div>}
        {list[1] && <div>{list[1]}</div>}
        {list[2] && <div>{list[2]}</div>}
      </div>
      <div className="itemDescriptionArea">
          <div className="itemDesc">Item description</div>
          <div className="qtyDesc">Qty / Hrs</div>
          <div className="priceDesc">Price</div>
          <div className="sumDesc">Sum</div>
        </div>
        <div className="itemArea">
          {outputItems(list[3])}
        </div>
        <div className="footer">
          <textarea value={sessionStorage.getItem('notes') || ''} disabled name="notes"/>
          <div className="dateAndStatus">
            Status: {sessionStorage.getItem('status') ||'Outstanding'}<br/><br/>
            Due date: {sessionStorage.getItem('date') ||'Not selected'}
          </div>
        </div>
        <Submit/>
    </div>
    
  )
  
}

export default DisplayPreview;