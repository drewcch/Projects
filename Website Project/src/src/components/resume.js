import React, { Component } from "react";
import { Grid, Cell } from "react-mdl";
import avatar from "./img/avatar.png";
import Education from "./education";
import Experience from "./experience";
import Skills from "./skills";

class Resume extends Component {
  render() {
    return (
      <div style={{ fontFamily: "Oxygen" }}>
        <Grid>
          <Cell col={4}>
            <div style={{ textAlign: "center" }}>
              <img src={avatar} alt="avatar" style={{ height: "200px" }} />
            </div>
            <h2 style={{ paddingTop: "30px" }}>Andrew Chien</h2>
            <h4 style={{ color: "grey" }}>Software Developer</h4>
            <hr
              style={{
                borderTop: "3px solid rgb(118, 209, 252)",
                width: "50%"
              }}
            />
            <p>
              {" "}
              Hello, my name is Andrew Chien and I am currently a third-year
              Software Engineer major at the University of California, Irvine.
              Through my past project and work experiences, I will be able to
              contribute immediately as a member of any software development
              group.
            </p>
            <hr
              style={{
                borderTop: "3px solid rgb(118, 209, 252)",
                width: "50%"
              }}
            />
            <h5>Address</h5>
            <p>510 Scented Violet, Irvine CA, 92620</p>
            <h5>Phone</h5>
            <p>(781) 956-4850</p>
            <h5>Email</h5>
            <p>acchien1@uci.edu</p>
            <hr
              style={{
                borderTop: "3px solid rgb(118, 209, 252)",
                width: "50%"
              }}
            />
          </Cell>
          <Cell className="resume-right-col" col={8}>
            <h2>Education</h2>
            <Education
              startDate="September 2016"
              endDate="June 2020"
              schoolName="University of California, Irvine"
              schoolDescription="Studied within the Donald Bren School of Information and Computer Sciences, taking classes in programming, user experience, networking, and more."
            />
            <hr style={{ borderTop: "3px solid #e22947" }} />
            <h2>Experience</h2>
            <Experience
              startDate="September 2017"
              endDate="December 2017"
              jobName="Undergraduate Grader in Linear Algebra"
              jobLocation="University of California, Irvine — Irvine, CA"
              jobDescription="Graded labs, exams, and homework for a class of 200 students. Also developed solutions for all classwork and answered questions during discussion hours on a coordinated schedule with three other graders and two teaching assistants."
            />

            <Experience
              startDate="June 2015"
              endDate="August 2015"
              jobName="Software Intern"
              jobLocation="Nova Biomedical — Waltham, MA"
              jobDescription="Identified errors within the software of a critical care analyzer. Proceeded to fix errors within the requirements document for the analyzer that consisted of incorrect or mismatched information between the requirements document and analyzer screen."
            />

            <Experience
              startDate="June 2014"
              endDate="August 2014"
              jobName="IT Intern"
              jobLocation="Massachusetts State House — Boston, MA"
              jobDescription="Inspected for and fixed errors on the Massachusetts Legislative website, using JAWS (audio recitation program for visually impaired) for error identification. Errors consisted of repetitions and missing information when reciting from website’s code."
            />
            <hr style={{ borderTop: "3px solid #e22947" }} />
            <h2>Skills</h2>
            <Skills skill="Python" progress={90} />
            <Skills skill="Java" progress={80} />
            <Skills skill="C++" progress={70} />
            <Skills skill="HTML/CSS" progress={60} />
            <Skills skill="JavaScript" progress={60} />
            <Skills skill="React" progress={40} />
          </Cell>
        </Grid>
      </div>
    );
  }
}

export default Resume;
