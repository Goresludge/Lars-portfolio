import React, { Component } from "react";
import {
  Route,
  NavLink,
  BrowserRouter
} from "react-router-dom";
import Invoice from "./Invoice";
import Preview from "./Preview";
 
class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div>
          <ul className="header">
            <li><NavLink exact to="/">Edit <br/> Invoice</NavLink></li>
            <li><NavLink to="/preview">Preview <br/> Invoice</NavLink></li>
          </ul>
          <div className="content">
            <Route exact path="/" component={Invoice}/>
            <Route path="/preview" component={Preview}/>
          </div>
        </div>
      </BrowserRouter>
    );
  }
}
 
export default App;