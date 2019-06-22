import React, { Component } from "react";
import { Grid, Cell } from "react-mdl";
import img from "./img/self-image.jpg";

class About extends Component {
  state = {};
  render() {
    return (
      <div className="contact-body">
        <Grid className="about-grid">
          <Cell col={6}>
            <img src={img} alt="self-img" className="self-img" />
            <p style={{ width: "75%", margin: "auto", paddingTop: "65px" }}>
              Hello! My name is Andrew Chien, and I am an up-and-coming software
              developer who is thrilled to be a part of a rapidly growing
              industry. Software is everywhere in our world, and it is a
              personal goal of mine to provide users with the most intuitive and
              enjoyable experience possible when interacting with the software
              they use. I am a fast learner and can easily adapt to working in any group environment in order to ensure the success of of the projects at hand.
            </p>
          </Cell>
          <Cell col={6}>
            <h4>School</h4>
            <p>
              I am currently attending University of California, Irvine, as a
              Software Engineer major. Before college, I attended Wellesley High
              School in Massachusetts, which is where I grew up. That's right, I'm both a Californian and a Bostonian at heart!
            </p>
            <hr
              style={{
                borderTop: "3px solid #2a2abb",
                width: "100%"
              }}
            />
            <h4>Work</h4>
            <p>
              In the past, I have worked as software intern at Nova Biomedical
              and the Massachusetts State House, which involved me identifying
              errors within the usability of software as it pertains to its
              requirements. I also engaged with socket programming at Nova in order to create demo software of a critical care analyzer. In addition, I was also an undergraduate grader in
              linear algebra at UCI.
            </p>
            <hr
              style={{
                borderTop: "3px solid #2a2abb",
                width: "100%"
              }}
            />
            <h4>Hobbies</h4>
            <p>
              I am a fervent follower of technology, especially for PC and
              mobile platforms, and sports (huge Boston fan over here!)
            </p>
          </Cell>
        </Grid>
      </div>
    );
  }
}

export default About;
