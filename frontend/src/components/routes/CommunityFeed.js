import React, {Component} from 'react';
import {Button} from 'react-md';

const CommunityFeed = () => {

    const style = {
        height: 3000,
        width: '100%',
        backgroundColor: 'orange',
    }

    return (
        <div className="cl-fitted-box">

            <div style={style}>
                <h2>
                    커뮤니티 피드
                </h2>
            </div>


            <Button floating primary iconClassName="fa fa-pencil fa-2x" className="cl-write__button--fixed"/>
        </div>
    );
};

export default CommunityFeed;