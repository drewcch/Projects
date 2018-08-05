import React, { Component } from "react";

const Weather = props => (
  <div className="weather_info">
    {props.city &&
      props.country && (
        <p className="weather__key">
          Location:{" "}
          <span className="weather__value">
            {props.city}, {props.country}
          </span>
        </p>
      )}

    {props.temperature && (
      <p className="weather__key">
        Temperature:{" "}
        <span className="weather__value"> {props.temperature}°C</span>
        <span className="weather__subkey">
          <br />Expected High:{" "}
          <span className="weather__subvalue"> {props.max_temp}°C</span>
        </span>
        <span className="weather__subkey">
          <br />Expected Low:{" "}
          <span className="weather__subvalue"> {props.min_temp}°C</span>
        </span>
      </p>
    )}

    {props.humidity && (
      <p className="weather__key">
        Humidity: <span className="weather__value"> {props.humidity}%</span>
      </p>
    )}

    {props.wind_speed && (
      <p className="weather__key">
        Wind Speed:{" "}
        <span className="weather__value"> {props.wind_speed} m/s</span>
      </p>
    )}

    {props.pressure && (
      <p className="weather__key">
        Pressure: <span className="weather__value"> {props.pressure} hpa</span>
      </p>
    )}

    {props.visibility && (
      <p className="weather__key">
        Visibility:{" "}
        <span className="weather__value"> {props.visibility} m</span>
      </p>
    )}

    {props.description && (
      <p className="weather__key">
        Conditions: <span className="weather__value"> {props.description}</span>
      </p>
    )}

    {props.error && (
      <p className="weather__error">
        <br />
        {props.error}
      </p>
    )}
  </div>
);

export default Weather;
