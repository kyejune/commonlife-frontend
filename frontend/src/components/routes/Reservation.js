import React, {Component} from 'react';
import HeaderOfReservation from 'components/ui/HeaderOfReservation';
import SelectWithTitle from 'components/ui/SelectWithTitle';

class Reservation extends Component {

    render() {




        return <div>

            <HeaderOfReservation/>

            <div className="cl-fitted-box">

                <SelectWithTitle/>

                <ul>
                    <li style={{height: 100, backgroundColor: 'orange'}}/>
                    <li style={{height: 100, backgroundColor: 'purple'}}/>
                    <li style={{height: 100, backgroundColor: 'red'}}/>
                    <li style={{height: 100, backgroundColor: 'green'}}/>
                    <li style={{height: 100, backgroundColor: 'orange'}}/>
                    <li style={{height: 100, backgroundColor: 'purple'}}/>
                    <li style={{height: 100, backgroundColor: 'red'}}/>
                    <li style={{height: 100, backgroundColor: 'green'}}/>
                    <li style={{height: 100, backgroundColor: 'orange'}}/>
                    <li style={{height: 100, backgroundColor: 'purple'}}/>
                    <li style={{height: 100, backgroundColor: 'red'}}/>
                    <li style={{height: 100, backgroundColor: 'green'}}/>
                </ul>
            </div>
        </div>
    }

}

export default Reservation;