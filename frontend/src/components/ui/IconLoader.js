import React, { Component } from 'react';
   
class IconLoader extends Component {

    constructor(props){
        super( props );

        this.state = {
            defaultSrc:'icons/alert-icon-red@3x.png',
            src: this.props.src,
        }
    }

    loadFail = ()=>{
        // 로드 실패하면 기본값 이미지로 교체
        this.setState( { src: this.state.defaultSrc });
    }

    render() {
        return (
            <img src={ this.state.src || this.state.defaultSrc } width={this.props.width} height={this.props.height} alt="아이콘이미지" onError={ this.loadFail } className={ this.props.className }/>
        );
    }
}

    
export default IconLoader;
