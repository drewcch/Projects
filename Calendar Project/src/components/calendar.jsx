import React, { Component } from "react";
import dateFns from "date-fns";

class Calendar extends Component {
  state = {
    currentMonthYear: new Date(),
    selectedDay: new Date()
  };

  renderHeader() {
    const dateFormat = "MMMM YYYY";
    const month = this.renderHeaderImage(
      dateFns.format(this.state.currentMonthYear, "MMMM")
    );

    return (
      <div className={`header row flex-middle ${month}`}>
        <div className="col col-start">
          <div className="icon" onClick={this.prevMonth}>
            chevron_left
          </div>
        </div>
        <div className="col col-center">
          <div className="icon" onClick={this.nextYear}>
            expand_less
          </div>
          <div>{dateFns.format(this.state.currentMonthYear, dateFormat)}</div>
          <div className="icon" onClick={this.prevYear}>
            expand_more
          </div>
        </div>
        <div className="col col-end">
          <div className="icon" onClick={this.nextMonth}>
            chevron_right
          </div>
        </div>
      </div>
    );
  }

  renderDays() {
    const dateFormat = "dddd";
    const days = [];

    let startDate = dateFns.startOfWeek(this.state.currentMonthYear);

    for (let j = 0; j < 7; j++) {
      days.push(
        <div className="col col-center" key={j}>
          {dateFns.format(dateFns.addDays(startDate, j), dateFormat)}
        </div>
      );
    }

    return <div className="days row">{days}</div>;
  }

  renderCells() {
    const { currentMonthYear, selectedDate } = this.state;
    const monthStart = dateFns.startOfMonth(currentMonthYear);
    const monthEnd = dateFns.endOfMonth(currentMonthYear);
    const startDate = dateFns.startOfWeek(monthStart);
    const endDate = dateFns.endOfWeek(monthEnd);

    const dateFormat = "D";

    let weeks = [];
    let days = [];

    let day = startDate;
    let formattedDate = "";

    while (day <= endDate) {
      for (let k = 0; k < 7; k++) {
        formattedDate = dateFns.format(day, dateFormat);
        const newDay = day;

        const isSameMonth = dateFns.isSameMonth(day, monthStart);
        const isSameDay = dateFns.isSameDay(day, selectedDate);

        days.push(
          <div
            className={`col cell ${
              isSameMonth ? (isSameDay ? "selected" : "") : "disabled"
            }`}
            key={day}
            onClick={() =>
              this.onDateClick(dateFns.parse(newDay), isSameMonth, selectedDate)
            }
          >
            <span className="number">{formattedDate}</span>
            <span className="bg">{formattedDate}</span>
          </div>
        );

        day = dateFns.addDays(day, 1);
      }

      weeks.push(
        <div className="row" key={day}>
          {days}
        </div>
      );
      days = [];
    }
    return <div className="body">{weeks}</div>;
  }

  renderHeaderImage(date) {
    switch (date) {
      case "January":
        return "jan";
      case "February":
        return "feb";
      case "March":
        return "mar";
      case "April":
        return "apr";
      case "May":
        return "may";
      case "June":
        return "jun";
      case "July":
        return "jul";
      case "August":
        return "aug";
      case "September":
        return "sep";
      case "October":
        return "oct";
      case "November":
        return "nov";
      case "December":
        return "dec";
    }
  }

  nextMonth = () => {
    this.setState({
      currentMonthYear: dateFns.addMonths(this.state.currentMonthYear, 1)
    });
  };

  prevMonth = () => {
    this.setState({
      currentMonthYear: dateFns.subMonths(this.state.currentMonthYear, 1)
    });
  };

  nextYear = () => {
    this.setState({
      currentMonthYear: dateFns.addYears(this.state.currentMonthYear, 1)
    });
  };

  prevYear = () => {
    this.setState({
      currentMonthYear: dateFns.subYears(this.state.currentMonthYear, 1)
    });
  };

  onDateClick = (day, isSameMonth, selectedDate) => {
    this.setState({
      selectedDate: day
    });

    if (!isSameMonth) {
      dateFns.isBefore(day, selectedDate) ? this.prevMonth() : this.nextMonth();
    }
  };

  render() {
    return (
      <div className="calendar">
        {this.renderHeader()}
        {this.renderDays()}
        {this.renderCells()}
      </div>
    );
  }
}

export default Calendar;
