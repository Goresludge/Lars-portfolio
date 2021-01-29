import React, { Component } from "react";
import DisplayPreview from "./DisplayPreview"

class Preview extends Component{

  render() {
    return (
      <div>
        <h2>Faktura / Invoice</h2>
        <DisplayPreview/>
    </div>
    );
  }
}

export default Preview;
