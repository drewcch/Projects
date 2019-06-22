import React, { Component } from "react";
import { Grid, Cell } from "react-mdl";
import avatar from "./img/avatar.png";

class Landing extends Component {
  state = {};
  render() {
    return (
      <div style={{ width: "100%", margin: "auto" }}>
        <Grid className="landing-grid">
          <Cell col={12}>
            <img src={avatar} alt="avatar" className="avatar-img" />
            <div className="banner-text">
              <h1>Software Developer</h1>
              <hr />
              <p>Python | Java | C++ | C# | HTML/CSS | JavaScript | React</p>
              <div className="social-links">
                {/*Github*/}
                <a href="https://github.com/drewcch/Projects" target="_blank">
                  <i className="fab fa-github-square" aria-hidden="true" />
                </a>

                {/*LinkedIn*/}
                <a
                  href="https://www.linkedin.com/in/andrew-chien-26943a156/"
                  target="_blank"
                >
                  <i className="fab fa-linkedin" aria-hidden="true" />
                </a>

                {/*Handshake*/}
                <a
                  href="https://uci.joinhandshake.com/users/3948022"
                  target="_blank"
                >
                  <i className="far fa-handshake" aria-hidden="true" />
                </a>
              </div>
            </div>
          </Cell>
        </Grid>
      </div>
    );
  }
}

export default Landing;
