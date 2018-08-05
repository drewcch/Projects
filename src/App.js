import React, { Component } from "react";
import Titles from "./components/Titles";
import Form from "./components/Form";
import Weather from "./components/Weather";

const API_KEY = "fef83c2181952f9b5a3b22523235fb3d";

class App extends Component {
  state = {
    temperature: undefined,
    max_temp: undefined,
    min_temp: undefined,
    city: undefined,
    country: undefined,
    humidity: undefined,
    wind_speed: undefined,
    pressure: undefined,
    visibility: undefined,
    description: undefined,
    error: undefined
  };

  capitalizeFirstLetter = string =>
    string.replace(/\b\w/g, l => l.toUpperCase());

  getWeather = async e => {
    e.preventDefault();
    const city = e.target.elements.city.value;
    const country = e.target.elements.country.value;
    const api_call = await fetch(
      `http://api.openweathermap.org/data/2.5/weather?q=${city},${country}&APPID=${API_KEY}&units=metric`
    );
    const data = await api_call.json();

    if (city && country) {
      this.setState({
        temperature: data.main.temp,
        max_temp: data.main.temp_max,
        min_temp: data.main.temp_min,
        city: data.name,
        country: data.sys.country,
        humidity: data.main.humidity,
        wind_speed: data.wind.speed,
        pressure: data.main.pressure,
        visibility: data.visibility,
        description: this.capitalizeFirstLetter(data.weather[0].description),
        error: ""
      });
    } else {
      this.setState({
        temperature: undefined,
        max_temp: undefined,
        min_temp: undefined,
        city: undefined,
        country: undefined,
        humidity: undefined,
        wind_speed: undefined,
        pressure: undefined,
        visibility: undefined,
        description: undefined,
        error: "Please enter a city and country."
      });
    }
  };

  render() {
    return (
      <div>
        <div className="wrapper">
          <div className="main">
            <div className="container">
              <div className="row">
                <div className="col-xs-5 title-container">
                  <Titles />
                </div>
                <div className="col-xs-7 form-container">
                  <Form getWeather={this.getWeather} />
                  <Weather
                    temperature={this.state.temperature}
                    max_temp={this.state.max_temp}
                    min_temp={this.state.min_temp}
                    city={this.state.city}
                    country={this.state.country}
                    humidity={this.state.humidity}
                    wind_speed={this.state.wind_speed}
                    pressure={this.state.pressure}
                    visibility={this.state.visibility}
                    description={this.state.description}
                    error={this.state.error}
                  />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default App;
