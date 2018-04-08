import React, {Component} from 'react';
import Net from "../../scripts/net";

class HouseHolderInputs extends Component {


    constructor(props) {
        super(props);

        this.state = {
            dongs: [],
            hos: [],
            name: undefined,
            phone: undefined,
            certId: undefined,

            selectedDong: "undefined",
            selectedHo: "undefined",
        }
    }

    componentDidMount() {
        Net.getDongsInBranch(this.props.branch.cmplxId, data => {
            this.setState({dongs: data});

            if (data.length === 1) {
                this.setState({selectedDong: data[0]});
                this.loadHos(data[0]);
            }

        });
    }

    loadHos(dongId) {
        Net.getNumbersInBranch(this.props.branch.cmplxId, dongId, data => {
            this.setState({hos: data});
        });
    }

    // 동 선택되면,
    onSelectedDong = evt => {
        this.setState({selectedDong: evt.target.value, selectedHo: "undefined"});
        this.loadHos(evt.target.value);
    }

    // 호 선택되면,
    onSelectedHo = evt => {
        this.setState({selectedHo: evt.target.value});
        console.log(evt.target.value);
    }

    // 인증 번호 요청
    onRequestCertNo=()=>{
        let { branch } = this.props;
        let { selectedDong, selectedHo, name, phone } = this.state;
        Net.requestHouseHolderPhoneAuthNumber( branch.cmplxId, selectedDong, selectedHo, name, phone, res=>{
           console.log( '인증 번호 요청:', res );
        });
    }

    render() {
        return <div className="cl-join-householder">

            <div className="cl-bg--black30">
                <div className="color-white">
                    <span>{this.props.branch.cmplxNm}</span>
                    <span className="color-white50">({this.props.branch.addr})</span>
                </div>

                <select value={this.state.selectedDong} onChange={this.onSelectedDong}>
                    <option value="undefined" disabled>동</option>
                    {this.state.dongs.map(item => {
                        return <option key={item} value={item}>{item}</option>
                    })}
                </select>

                <select value={this.state.selectedHo} onChange={this.onSelectedHo}>
                    <option value="undefined" disabled>호수</option>
                    {this.state.hos.map(item => {
                        return <option key={item} value={item}>{item}</option>
                    })}
                </select>
            </div>

            <div>
                <input type="text" value={this.state.name} placeholder="세대주 이름"/>
                <input type="number" value={this.state.phone} placeholder="세대주 휴대폰 번호('-'제외 숫자만 입력)"/>
                <button onClick={ this.onRequestCertNo }>인증번호 요청</button>
                <input type="number" placeholder="인증번호 입력"/>
            </div>

        </div>
    }
}


export default HouseHolderInputs;
