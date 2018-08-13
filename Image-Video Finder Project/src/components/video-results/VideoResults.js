import React, { Component } from "react";
import PropTypes from "prop-types";
import { GridList, GridTile } from "material-ui/GridList";
import IconButton from "material-ui/IconButton";
import PlayCircleOutline from "material-ui/svg-icons/av/play-circle-outline";
import Dialog from "material-ui/Dialog";
import FlatButton from "material-ui/FlatButton";

class VideoResults extends Component {
  state = {
    open: false,
    currentVid: ""
  };

  capitalizeFirstLetter = string =>
    string.replace(/\b\w/g, l => l.toUpperCase());

  handleOpen = vid => {
    this.setState({ open: true, currentVid: vid });
  };

  handleClose = () => {
    this.setState({ open: false });
  };

  render() {
    let videoListContent;
    const { videos } = this.props;
    if (videos) {
      videoListContent = (
        <GridList cols={4}>
          {videos.map(vid => (
            <GridTile
              title={this.capitalizeFirstLetter(vid.tags)}
              key={vid.id}
              subtitle={
                <span>
                  by <strong>{vid.user}</strong>
                </span>
              }
              actionIcon={
                <IconButton
                  onClick={() => this.handleOpen(vid.videos.medium.url)}
                >
                  <PlayCircleOutline color="white" />
                </IconButton>
              }
            >
              <video src={vid.videos.medium.url} type="video/mp4" />
            </GridTile>
          ))}
        </GridList>
      );
    } else {
      videoListContent = null;
    }

    const actions = [
      <FlatButton label="Close" primary={true} onClick={this.handleClose} />
    ];

    return (
      <div>
        {videoListContent}
        <Dialog
          actions={actions}
          modal={false}
          open={this.state.open}
          onRequestClose={this.handleClose}
        >
          <video
            src={this.state.currentVid}
            type="video/mp4"
            style={{ width: "100%" }}
            controls="control"
            autoplay={true}
          />
        </Dialog>
      </div>
    );
  }
}

VideoResults.propTypes = {
  videos: PropTypes.array.isRequired
};

export default VideoResults;
