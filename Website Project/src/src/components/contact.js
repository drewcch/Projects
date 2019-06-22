import React, { Component } from "react";
import { Grid, Cell, List, ListItem, ListItemContent } from "react-mdl";
import avatar from "./img/avatar.png";

class Contact extends Component {
  render() {
    return (
      <div className="contact-body">
        <Grid className="contact-grid">
          <Cell col={6}>
            <h2>Andrew Chien</h2> <br /> <br />
            {/* <img src={img} alt="self-img" className="self-img" /> */}
            <img src={avatar} alt="avatar" style={{ height: "200px" }} />
            <p style={{ width: "75%", margin: "auto", paddingTop: "65px" }}>
              Hello! Here are the various ways you can get in touch with me. I
              can be contacted via phone, email, or Skype using the information
              on the right.
            </p>
          </Cell>
          <Cell col={6}>
            <h2>Contact Me</h2>
            <hr />

            <div className="contact-list">
              <List>
                <ListItem>
                  <ListItemContent
                    style={{
                      fontSize: "25px",
                      fontFamily: "Anton",
                      justifyContent: "center"
                    }}
                  >
                    <i className="fas fa-phone-square" aria-hidden="true" />
                    <br />(781) 956-4850
                  </ListItemContent>
                </ListItem>
                <ListItem>
                  <ListItemContent
                    style={{
                      fontSize: "25px",
                      fontFamily: "Anton",
                      justifyContent: "center"
                    }}
                  >
                    <i className="fas fa-envelope" area-hidden="true" />
                    <br />acchien1@uci.edu
                  </ListItemContent>
                </ListItem>
                <ListItem>
                  <ListItemContent
                    style={{
                      fontSize: "25px",
                      fontFamily: "Anton",
                      justifyContent: "center"
                    }}
                  >
                    <i className="fab fa-skype" aria-hidden="true" />
                    <br /> Andrew Chien
                  </ListItemContent>
                </ListItem>
              </List>
            </div>
          </Cell>
        </Grid>
      </div>
    );
  }
}

export default Contact;
