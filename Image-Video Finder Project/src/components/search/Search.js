import React, { Component } from "react";
import TextField from "material-ui/TextField";
import SelectField from "material-ui/SelectField";
import MenuItem from "material-ui/MenuItem";
import axios from "axios";
import ImageResults from "../image-results/ImageResults";
import VideoResults from "../video-results/VideoResults";

const apiUrl = "https://pixabay.com/api";
const apiKey = "9753747-18b2a3ee256a2894ce8aa5926";

class Search extends Component {
  state = {
    searchText: "",
    amount: 10,
    media_type: "Images",
    images: [],
    videos: [],
    order: "popular"
  };

  onTextChange = e => {
    const val = e.target.value;
    this.setState(
      { searchText: val },
      val === ""
        ? this.setState({ images: [], videos: [] })
        : this.state.media_type === "Images"
          ? this.getImageURL()
          : this.getVideoURL()
    );
  };

  getVideoURL = () => {
    axios
      .get(
        `${apiUrl}/videos/?key=${apiKey}&q=${this.state.searchText}&per_page=${
          this.state.amount
        }&safesearch=true&order=${this.state.order}`
      )
      .then(res => this.setState({ videos: res.data.hits }))
      .catch(err => console.log(err));
  };

  getImageURL = () => {
    axios
      .get(
        `${apiUrl}/?key=${apiKey}&q=${
          this.state.searchText
        }&image_type=photo&per_page=${
          this.state.amount
        }&safesearch=true&order=${this.state.order}`
      )
      .then(res => this.setState({ images: res.data.hits }))
      .catch(err => console.log(err));
  };

  fetchURL = () => {
    this.state.media_type === "Images"
      ? this.getImageURL()
      : this.getVideoURL();
  };

  onAmountChange = (e, index, value) => {
    this.setState({ amount: value });
    this.fetchURL();
  };

  onMediaChange = (e, index, value) => {
    this.setState({ media_type: value });
    this.fetchURL();
  };

  onOrderChange = (e, index, value) => {
    this.setState({ order: value });
    this.fetchURL();
  };

  render() {
    return (
      <div>
        <TextField
          name="searchText"
          value={this.state.searchText}
          onChange={this.onTextChange}
          floatingLabelText="Search for Images or Videos"
          fullWidth={true}
        />
        <br />

        <SelectField
          name="amount"
          floatingLabelText="Amount"
          value={this.state.amount}
          onChange={this.onAmountChange}
        >
          <MenuItem value={10} primaryText="10" />
          <MenuItem value={20} primaryText="20" />
          <MenuItem value={30} primaryText="30" />
          <MenuItem value={40} primaryText="40" />
          <MenuItem value={50} primaryText="50" />
        </SelectField>

        <SelectField
          name="order"
          floatingLabelText="Order"
          value={this.state.order}
          onChange={this.onOrderChange}
        >
          <MenuItem value={"popular"} primaryText="Popular" />
          <MenuItem value={"latest"} primaryText="Latest" />
        </SelectField>

        <SelectField
          name="media_type"
          floatingLabelText="Media Type"
          value={this.state.media_type}
          onChange={this.onMediaChange}
        >
          <MenuItem value={"Images"} primaryText="Images" />
          <MenuItem value={"Videos"} primaryText="Videos" />
        </SelectField>

        <br />
        {this.state.media_type === "Images" ? (
          this.state.images.length > 0 ? (
            <ImageResults images={this.state.images} />
          ) : null
        ) : this.state.videos.length > 0 ? (
          <VideoResults videos={this.state.videos} />
        ) : null}
      </div>
    );
  }
}

export default Search;
