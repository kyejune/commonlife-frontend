import React, { Component } from 'react';
   
class CheckBoxes extends Component {
    render() {


        let { max, items } = this.props;
        max = max || items.length;

        items = items || [];

        const n = 'name-' + Math.random()*100000;

        return (

            <div className="cl-checkboxes">
                {
                    items.map((item, index) => {
                        return <label key={index}> <input type={this.props.radio?'radio':'checkbox'} name={n} /> { item.label } </label>
                    })
                }
            </div>
        );
    }
}

    
export default CheckBoxes;