import React, { Component } from 'react';
import reactStringReplace from "react-string-replace";
   
class TagComponent extends Component {
    render() {

        let content = this.props.content;

        // 이미지 복원
        let imgs = this.props.content.match(/<img[^>]+>/g);
        if( imgs ) {
            imgs.forEach((img, index) => {
                content = reactStringReplace(content, img, (match, index, offset) => {
                    let src = match.match(/src=\S+/)[0].replace(/(src=)|(")|(')|(<img)|(\/>)|\s/g, '');
                    return <img key={'img' + index} src={src} width="100%" alt="첨부 이미지"/>;
                });
            });
        }

        // 줄넘김 복원
        content = reactStringReplace(content, /(\n)/g, (match, index) => {
            return <br key={'br-' + index}/>;
        });


        if( !this.props.nolink ) {
            // 각종 링크들 복원
            content = reactStringReplace(content, /(https:\/\/\S+)/g, (match, index, offset) => {
                return <a key={match + offset} href={match} target="_blank">{match}</a>;
            });

            content = reactStringReplace(content, /(http:\/\/\S+)/g, (match, index, offset) => {
                return <a key={match + offset} href={match} target="_blank">{match}</a>;
            });

            content = reactStringReplace(content, /(www\.\S+)/g, (match, index, offset) => {
                return <a key={match + offset} href={match} target="_blank">{match}</a>;
            });
        }

        return content;
    }
}

    
export default TagComponent;
