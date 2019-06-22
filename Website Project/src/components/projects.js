import React, { Component } from "react";
import {
  Tabs,
  Tab,
  Grid,
  Cell,
  Card,
  CardTitle,
  CardActions,
  CardText,
  Button
} from "react-mdl";

class Projects extends Component {
  constructor(props) {
    super(props);
    this.state = { activeTab: 0 };
  }

  toggleCategories() {
    switch (this.state.activeTab) {
      case 0:
        return (
          <div className="projects-grid">
            <Card shadow={5} style={{ minWidth: "450", margin: "auto" }}>
              <CardTitle
                style={{
                  color: "#fff",
                  height: "176px",
                  background:
                    "url(http://globaltoynews.typepad.com/.a/6a0133ec87bd6d970b01b7c8758fa9970b-pi) center / cover"
                }}
              >
                Othello Project
              </CardTitle>
              <CardText>
                Implemented the game logic and a user-friendly GUI for the game
                Othello, which allows for two players
              </CardText>
              <CardActions border>
                <Button colored>
                  <a
                    href="https://github.com/drewcch/Projects/tree/master/Othello%20Project"
                    target="_blank"
                  >
                    GitHub
                  </a>
                </Button>
              </CardActions>
            </Card>

            <Card shadow={5} style={{ minWidth: "450", margin: "auto" }}>
              <CardTitle
                style={{
                  color: "black",
                  height: "176px",
                  background:
                    "url(https://cdn.iconscout.com/icon/premium/png-256-thumb/gps-70521.png) center"
                }}
              >
                GPS Project
              </CardTitle>
              <CardText>
                Implemented a GPS using the API of MapQuest that provides
                directions to a location inputted by the user
              </CardText>
              <CardActions border>
                <Button colored>
                  <a
                    href="https://github.com/drewcch/Projects/tree/master/GPS%20Project"
                    target="_blank"
                  >
                    GitHub
                  </a>
                </Button>
              </CardActions>
            </Card>

            <Card shadow={5} style={{ minWidth: "450", margin: "auto" }}>
              <CardTitle
                style={{
                  color: "black",
                  height: "176px",
                  background:
                    "url(http://www.ultraconnect4.com/gfx/4.png) center / cover"
                }}
              >
                Connect Four Project
              </CardTitle>
              <CardText>
                Implemented the game logic and connected onto a server using a
                protocol to allow the user to play against an AI
              </CardText>
              <CardActions border>
                <Button colored>
                  <a
                    href="https://github.com/drewcch/Projects/tree/master/ConnectFour%20Project"
                    target="_blank"
                  >
                    GitHub
                  </a>
                </Button>
              </CardActions>
            </Card>
          </div>
        );
      case 1:
        return (
          <div className="projects-grid">
            <Card shadow={5} style={{ minWidth: "450", margin: "auto" }}>
              <CardTitle
                style={{
                  color: "red",
                  height: "176px",
                  background:
                    "url(https://csharpcorner-mindcrackerinc.netdna-ssl.com/UploadFile/75a48f/tic-tac-toe-game-in-C-Sharp/Images/TicTacToe_HD_iTunesArtwork.png) center / cover"
                }}
              >
                Tic-Tac-Toe Project
              </CardTitle>
              <CardText>
                Implemented the game logic to allow the user to play against the
                AI, using an algorithm to ensure the AI never loses
              </CardText>
              <CardActions border>
                <Button colored>
                  <a
                    href="https://github.com/drewcch/Projects/tree/master/TicTacToe%20Project"
                    target="_blank"
                  >
                    GitHub
                  </a>
                </Button>
              </CardActions>
            </Card>
          </div>
        );
      case 2:
        return (
          <div className="projects-grid">
            <Card shadow={5} style={{ minWidth: "450", margin: "auto" }}>
              <CardTitle
                style={{
                  color: "black",
                  height: "176px",
                  background:
                    "url(https://www.weather2umbrella.com/wp-content/themes/w2u/image/svg/weather-icons/d06.svg) center / cover"
                }}
              >
                Weather Project
              </CardTitle>
              <CardText>
                Implemented a weather finder application using the API of
                OpenWeatherMap to give the user weather conditions for an
                inputted location
              </CardText>
              <CardActions border>
                <Button colored>
                  <a
                    href="https://github.com/drewcch/Projects/tree/master/Weather%20Project"
                    target="_blank"
                  >
                    GitHub
                  </a>
                </Button>
              </CardActions>
            </Card>

            <Card shadow={5} style={{ minWidth: "450", margin: "auto" }}>
              <CardTitle
                style={{
                  color: "black",
                  height: "176px",
                  background:
                    "url(http://www.thepublishingcontrarian.com/wp-content/uploads/2017/04/art-gallery-feat.jpg) center / cover"
                }}
              >
                Image/Video Finder Project
              </CardTitle>
              <CardText>
                Implemented an application using the API of PixaBay that enables
                the user to find images and videos using certain filters.
              </CardText>
              <CardActions border>
                <Button colored>
                  <a
                    href="https://github.com/drewcch/Projects/tree/master/Image-Video%20Finder%20Project"
                    target="_blank"
                  >
                    GitHub
                  </a>
                </Button>
              </CardActions>
            </Card>
          </div>
        );
    }
  }

  render() {
    return (
      <div className="category-tabs">
        <Tabs
          activeTab={this.state.activeTab}
          onChange={tabId => this.setState({ activeTab: tabId })}
          ripple
        >
          <Tab>Python</Tab>
          <Tab>Java</Tab>
          <Tab>React</Tab>
        </Tabs>

        <Grid>
          <Cell col={12}>
            <div className="content">{this.toggleCategories()}</div>
          </Cell>
        </Grid>
      </div>
    );
  }
}

export default Projects;
