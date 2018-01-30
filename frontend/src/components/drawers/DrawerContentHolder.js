/* DrawerContentHolder.jsx */
import React, {Component} from 'react';
import {Button, Toolbar} from 'react-md';
import {withRouter} from 'react-router';
import {observer} from 'mobx-react';
import Store from "scripts/store";
import closeSrc from 'images/back@3x.jpg';

class DrawerContentHolder extends Component {

    constructor( props ){
        super( props );

        this.state = {
            title: props.title,
        }
    }


    onClose(){

        let paths = this.props.location.pathname.split('/');
            paths.length -= 2;
        this.props.history.replace(paths.join('/'));

        Store.drawer = '';
    }

    updateTitle( title ){
        this.setState({
            title: title,
        })
    }


    render() {

        let CustomHeader, CloseButton;
        if( this.props.match.params.drawer === 'like' ){
            /* LIKE용 헤더 */
            CustomHeader = <header className="md-paper md-paper--2 md-toolbar md-background--primary md-toolbar--text-white md-toolbar--fixed">
                <button type="button"
                        onClick={ ()=> this.onClose() }
                        className="md-btn md-btn--flat md-btn--text md-pointer--hover md-text md-inline-block md-btn--toolbar md-toolbar--action-left cl-back__button">
                    <img src="data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAASABIAAD/4QBMRXhpZgAATU0AKgAAAAgAAYdpAAQAAAABAAAAGgAAAAAAA6ABAAMAAAABAAEAAKACAAQAAAABAAAAPKADAAQAAAABAAAAPAAAAAD/7QA4UGhvdG9zaG9wIDMuMAA4QklNBAQAAAAAAAA4QklNBCUAAAAAABDUHYzZjwCyBOmACZjs+EJ+/8AAEQgAPAA8AwERAAIRAQMRAf/EAB8AAAEFAQEBAQEBAAAAAAAAAAABAgMEBQYHCAkKC//EALUQAAIBAwMCBAMFBQQEAAABfQECAwAEEQUSITFBBhNRYQcicRQygZGhCCNCscEVUtHwJDNicoIJChYXGBkaJSYnKCkqNDU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6g4SFhoeIiYqSk5SVlpeYmZqio6Slpqeoqaqys7S1tre4ubrCw8TFxsfIycrS09TV1tfY2drh4uPk5ebn6Onq8fLz9PX29/j5+v/EAB8BAAMBAQEBAQEBAQEAAAAAAAABAgMEBQYHCAkKC//EALURAAIBAgQEAwQHBQQEAAECdwABAgMRBAUhMQYSQVEHYXETIjKBCBRCkaGxwQkjM1LwFWJy0QoWJDThJfEXGBkaJicoKSo1Njc4OTpDREVGR0hJSlNUVVZXWFlaY2RlZmdoaWpzdHV2d3h5eoKDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uLj5OXm5+jp6vLz9PX29/j5+v/bAEMAAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAf/bAEMBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAf/dAAQACP/aAAwDAQACEQMRAD8A/D+v9qD/AC/CgAoAKACgAoAKACgD/9D8P6/2oP8AL8KACgAoAKACgAoAKAP/0fw/r/ag/wAvwoAciNIyois7uwREQFmdmOFVVGSzMSAABkk4GeKTaSbbSSV23oklu2+iSGk20km23ZJbt9lvq/T7z9mbP/gnn8JP2dP2M/GH7QP7dfiLxN4I+KXxO8Ozaf8As2/Bvw3NBb+N7DxCUt9R0zW/E2jXhQXNxcAWo8SaJqH2ew8HeEb+6/ta4Tx5q+h6boP4RPxOzvinjvA8NeHeFwmPybKMVGrxVnuLjKWX1cNeVKtQwleF3CEff+qYinzVcfjacPYweXUMRVxX61HgTK8g4SxWecaYjEYPMsxoOHD+VYeUVjKde0alOtiKMrc8pe79YozcIYTCzl7SSxtWjSofjJX7wfkgUAFABQB//9L8P6/2oP8AL8KAP6af+CKP7A/wn8R+E4v20PiHrvg34heIvD+ratbfDzwA2owz6L8NNe8Ou7HxL8Rw8E/2Txau2DVPDdjcWlxZ+HdFubDxgi6jq17pH9gfyX4+eJGdYbGy4CyzD47K8LiKNCWZ5j7KUcRm+HxS0wmVtSjz4F3lRxVSM4TxeIhVwLdKhSrrF/0T4QcEZZXwq4ux1bCY/EUatWOAwXtFKjl9ag/94x94y5cVtUoQlCUcPRlDEr2tWdOWHT9qT/gnP8WP2u/ixrPxa+LX/BQL9mC81K8LWXh/w/ZaxNH4c8FeHI5pJLDwz4ZsJdel+yadaeY0k88jPfarfS3OqapPc6hdXE7vg/xRybgnJqGS5L4a8XU6UP3mJxNShF4rH4pxSqYvF1Fh1z1Z2SjFJU6NNRo0YQpQjGJxJwDmfFOZ1c0zTjnh2dSfuUKEJtYfB4dNuGHw8HiXy043u5P36s3KpUcpylKXzn/w5D/6vq/Zf/8ABt/9+a+p/wCJgP8Aq3nF3/gn/wC4Hgf8Qd/6rPh3/wAD/wDu5+P/AMefhT/wo74v+PfhN/wlugeO/wDhBtbbRv8AhL/C0nneH9exa21z9t0qTzZ99sftHlZ81/njcZ4xX7fw5nP+sOR5bnX1LE5d/aGH9v8AUsWrYnDe/OHs6y5Y2n7l7cq0fXc/Ks7yz+xs1x2V/WqON+pVvZfWsNrQr+7GXPT1l7vvW3eqPI69s8oKAP/T/D+v9qD/AC/CgD7E/Yr/AG1Pit+xL8Vrbx/4AuTq3hrVTaWHxF+HV/dyw+HvHnh6GVmNrdbVmXTtd05Zrifw54jht5rvR7uWWOSG+0i+1fSNS+G4+4Bybj/JpZbmUfYYyhz1MrzSnBSxOXYmSXvR29rhqrjGOKwspRhXhGLUqdenRr0PrOEOL8z4PzOONwUva4arywx+AnJqhjaCfwytf2denzN4fEKLlSk2mqlKdWjP9FP21P2LPhT+0T8Kbn/goF/wT+t11X4eaqt3qnxw+B+mWsUPiH4W+IIYkvfEmp6b4csmmbTrfTjN9s8U+FrTzbTTbSWPxb4SlvfBV8G0j8v4B4+znhjOY+GniXJ0M0oOFHh/iCtNyw2b4aTdPC0quLqJKtKqo8mDxs+WdacXgsaqWYUv3/3nF/CGWZ9lkuOeBo+1wFXmqZxk9OKjXy2ulz4ipTw8Ob2cabfNicNC8acZLFYWU8HP91+E9f0Sfi4UAFABQB//1Pw/r/ag/wAvwoAKAPT/AAF8aviz8LtB+IHhf4efEHxR4O8PfFTw+nhb4haRoWqTWNl4q0FLgXC2GpRR/wDbe0a5tzBePpWoavozXJ0nWdXsrvyMyyDJc4xOWYzNMsweOxWTYl4zLK+JpRqVMHiHHl9pSbv/AHZqElKCrUqFdRVahQqQ9LBZxmmW0MdhsBjsThMPmVBYbHUqNRwhiaKlzclRL/t6HNHlk6U6tJylSq1Kc/MK9c80KACgAoA//9X8P6/2oP8AL8KACgAoAKACgAoAKAP/1vw/r/ag/wAvwoAKACgAoAKACgAoA//Z" alt="이전" width="40" height="40"/>
                </button>
                { this.state.title }
            </header>
        }

        if( this.props.back ){

            CloseButton = <Button onClick={ ()=> this.onClose() } flat className="cl-back__button">
                <img src={ closeSrc } alt="이전" width="40" height="40"/>
            </Button>

        }else{

            CloseButton = <Button icon onClick={ ()=> this.onClose() }>close</Button>;
        }


        const { children } = this.props;


        // 자식 객체로 들어오는 children에게 updateTitle 메서드를 제공해서, 변경가능하게끔
        let childrenWithProps = React.Children.map( children, child =>
            React.cloneElement( child, { updateTitle: title=> this.updateTitle(title) })
        );

        return <div className="cl-drawer-content-holder">

                <Toolbar
                    colored
                    fixed
                    nav={CloseButton}
                    title={ this.state.title }
                    titleClassName="cl-ellipsis"
                    children={ CustomHeader }
                />

                <section className="dialogs__content">

                    {childrenWithProps}

                </section>

            </div>
    }
}

export default observer( withRouter(DrawerContentHolder) );