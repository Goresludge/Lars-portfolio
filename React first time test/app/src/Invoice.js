import React, { Component } from "react";
import AddItem from "./AddItem"
import ContactInformation from "./ContactInformation"
import AdditionalInformation from "./AdditionalInformation"
import Submit from "./Submit"

class Invoice extends Component{
  
  render() {
    return (
      <div>
        <h2>Faktura / Invoice</h2>
        <ContactInformation/>
        <div className="itemDescriptionArea">
          <div className="itemDesc">Item description</div>
          <div className="qtyDesc">Qty / Hrs</div>
          <div className="priceDesc">Price</div>
          <div className="sumDesc">Sum</div>
        </div>
        <div className="itemArea">
          <AddItem/>
        </div>
        <AdditionalInformation/>
        <Submit/>
      </div>
    );
  }
}

export default Invoice;
