import React, { Component } from "react";
import Calendar from "./components/calendar";
import "./App.css";

class App extends Component {
  render() {
    return (
      <div className="App">
        <header>
          <div id="logo">
            <span className="icon">calendar_today</span>
            <span>
              React <b>Calendar</b>
            </span>
          </div>
        </header>
        <main>
          <Calendar />
        </main>
      </div>
    );
  }
}

export default App;
