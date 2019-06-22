import React, { Component } from "react";
import "./App.css";
import { Layout, Header, Navigation, Drawer, Content } from "react-mdl";
import Main from "./components/main";
import { NavLink } from "react-router-dom";

class App extends Component {
  render() {
    return (
      <div className="demo-big-content">
        <Layout fixedHeader={true}>
          <Header
            className="header-color"
            title={
              <NavLink
                style={{ textDecoration: "none", color: "white" }}
                to="/"
              >
                Andrew Chien
              </NavLink>
            }
            scroll
          >
            <Navigation>
              <NavLink to="/aboutme">About Me</NavLink>
              <NavLink to="/projects">Projects</NavLink>
              <NavLink to="/resume">Resume</NavLink>
              <NavLink to="/contact">Contact</NavLink>
            </Navigation>
          </Header>
          <Drawer
            title={
              <NavLink
                style={{ textDecoration: "none", color: "black" }}
                to="/"
              >
                Home
              </NavLink>
            }
          >
            <Navigation>
              <NavLink to="/aboutme">About Me</NavLink>
              <NavLink to="/projects">Projects</NavLink>
              <NavLink to="/resume">Resume</NavLink>
              <NavLink to="/contact">Contact</NavLink>
            </Navigation>
          </Drawer>
          <Content>
            <div className="page-content" />
            <Main />
          </Content>
        </Layout>
      </div>
    );
  }
}

export default App;
