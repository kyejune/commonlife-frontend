/**
 * Created by agcdDev on 2016-08-16.
 */
/*******************************************************************************************
 * 파일이름	: common_check.js
 * 설    명	: 유효성체크나 기타 각종 체크처리를 정의한다
 * 추가내용     :
 * 버전관리
 *******************************************************************************************/
var MAX_ATCH_FILE_SIZE = 10000000;
var bTwice = true;
/**
 * IE 버전 체크
 * @return IE 버전
 */
function ieVersionReturn(){
    var bStyle = document.body.style;
    var canvas = document.createElement('canvas');

    //canvas가 없어 - 8
    if( !('getContext' in canvas) ) return 8;

    //transtion이 불가 - 9
    if( !('msTransition' in bStyle) && !('transition' in bStyle) ) return 9;

    //webGL이 없어 - 10
    if( !canvas.getContext('webgl') ) return 10;

    //아니면 11, 나중에 12나오면 여길 분기
    return 11;
}

/**
 * 버튼이중클릭체크
 * @return bTwice (true:정상, false:이미클릭된경우에러)
 */
function checkTwice() {

    if(bTwice) {
        bTwice = false;
        return true;
    }
    alert("중복실행할 수 없습니다.");

    return bTwice;
}

/**
 * 아이디 / 비밀번호 체크
 * @param String 체크할 대상 문자열
 * @param type 체크할 대상(아이디인지 패스워드인지)
 * @return (true:정상, false:체크에러)
 */
function validString(String, type) {

    var RetValue = true;
    var Count;

    var IDPermitChar = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ.';
    var PWPermitChar = '01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()[]?/+-{}.<>';
    var PermitChar;

    if(type == 'ID'){

        PermitChar = IDPermitChar;
    }else{
        PermitChar = PWPermitChar;
    }

    for (var i = 0; i < String.length; i++) {
        Count = 0;
        for (var j = 0; j < PermitChar.length; j++) {
            if(String.charAt(i) == PermitChar.charAt(j)) {
                Count++;
                break;
            }
        }

        if (Count == 0) {
            RetValue = false;
            break;
        }
    }
    return RetValue;
}

/**
 * 숫자 및 영문인지 체크
 * @param fObj
 * @param String
 * @param type
 * @return
 */
function isAlphaNum(fObj) {
    var len1;

    var fname = fObj.getAttribute("fname");

    var str = fObj.value;
    var err = 0;
    for (var i=0; i<str.length; i++)  {
        var chk = str.substring(i,i+1);
        if(!chk.match(/[0-9]|[a-z]|[A-Z]/)) {
            len1 = fObj.value.length-1;
            alert("숫자 및 영문만 입력 가능합니다.");
            fObj.value = fObj.value.substring(0, len1);
            fObj.focus();
            break;
            return false;
        }
    }
}

/**
 * 숫자 및 영문 및 특수문자인지 체크 (ID용)
 * @param fObj
 * @param String
 * @param type
 * @return
 */
function isId(fObj) {
    var len1;

    var chChar = '01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_';
    var fname = fObj.getAttribute("fname");

    var str = fObj.value;
    var err = 0;
    for (var i=0; i<str.length; i++)  {
        var ch=fObj.value.charAt(i);

        if(!chChar.match(ch)) {
            alert("숫자 및 영문, -, _ 만 입력 가능합니다.");
            //fObj.value = "";
            fObj.focus();
            break;
            return false;
        }
    }
}
/**
 * 숫자 및 영문 및 특수문자인지 체크 (경로용)
 * @param fObj
 * @param String
 * @param type
 * @return
 */
function isPath(fObj) {
    var len1;

    var chChar = '01234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ-_/';
    var fname = fObj.getAttribute("fname");

    var str = fObj.value;
    var err = 0;
    for (var i=0; i<str.length; i++)  {
        var ch=str.charAt(i);

        if(!chChar.match(ch)) {
            alert("숫자 및 영문, -, _, / 만 입력 가능합니다.");
            //fObj.value = "";
            fObj.focus();
            break;
            return false;
        }
    }
}


/**
 * 한글입력체크(한글일경우는 아래의 체크, 영문이나 일문의경우는 각각의 js파일에 체크로직을 바꾸어준다)
 * @param str 체크할 문자열
 * @return (true:정상, false:언어체크에러)
 */
function validStrLang(str) {
    for(var j=0; j < str.length; j++) if(str.charCodeAt(j)<44032 || str.charCodeAt(j)>55215) {
        return false;
    }
    return true;
}

/**
 * 주민번호 체크
 * @param jm_bh1 주민번호 앞자리
 * @param jm_bh2 주민번호 뒷자리
 * @return (true:정상, false:주민번호체크에러)
 */
function checkJumin(jm_bh1, jm_bh2)
{
    var tot=0, result=0, re=0, se_arg=0;
    var chk_num="";

    chk_jm_bh = jm_bh1 + jm_bh2;
    if( chk_jm_bh.length != 13 )
    {
        return false;
    }
    else
    {
        for( var i=0; i < 12; i++ )
        {
            if( isNaN(chk_jm_bh.substr(i, 1)) )
            {
                return false;
            }
            se_arg = i;
            if (i >= 8) se_arg = i - 8;
            tot = tot + Number(chk_jm_bh.substr(i, 1)) * (se_arg + 2)
        }
        if( chk_num != "err" )
        {
            re = tot % 11;
            result = 11 - re;
            if( result >= 10 )
            {
                result = result - 10;
            }
            if( result != Number(chk_jm_bh.substr(12, 1)) )
            {
                return false;
            }
            if( (Number(chk_jm_bh.substr(6, 1)) < 1) || (Number(chk_jm_bh.substr(6, 1)) > 2) )
            {
                return false;
            }
        }
    }
    return true;
}

/**
 * 이메일 체크
 * @param iObj 체크할 대상 문자열
 * @return (true:정상, false:이메일체크에러)
 */
function checkEmail (emailStr) {
    var emailPat=/^(.+)@(.+)$/
    var specialChars="\\(\\)<>@,;:\\\\\\\"\\.\\[\\]"
    var validChars="\[^\\s" + specialChars + "\]"
    var quotedUser="(\"[^\"]*\")"
    var ipDomainPat=/^\[(\d{1,3})\.(\d{1,3})\.(\d{1,3})\.(\d{1,3})\]$/
    var atom=validChars + '+'
    var word="(" + atom + "|" + quotedUser + ")"
    var userPat=new RegExp("^" + word + "(\\." + word + ")*$")
    var domainPat=new RegExp("^" + atom + "(\\." + atom +")*$")
    var matchArray=emailStr.match(emailPat)
    //.존재체크
    if (matchArray==null) {
        return false
    }
    var user=matchArray[1]
    var domain=matchArray[2]


    var domainArray=domain.match(domainPat)
    if (domainArray==null) {
        return false
    }

    var atomPat=new RegExp(atom,"g")
    var domArr=domain.match(atomPat)
    var len=domArr.length
    if (domArr[domArr.length-1].length<2 || domArr[domArr.length-1].length>3) {
        return false
    }
    return true;
}
/**
 * 핸드폰번호 체크 정규식
 * @param iObj 체크할 대상 문자열
 * @return (true:정상, false:핸드폰번호체크에러)
 */
function checkMobile(mobileStr){
    var regExp = /^01([0|1|6|7|8|9]?)?([0-9]{3,4})?([0-9]{4})$/;
    if (!regExp.test(mobileStr)) {
        return false
    }
    return true;
}
/**
 * 일반전화번호 체크 정규식
 * @param iObj 체크할 대상 문자열
 * @return (true:정상, false:일반전화번호체크에러)
 */
function checkPhone(phoneStr){
    var regExp = /^\d{2,3}\d{3,4}\d{4}$/;
    if (!regExp.test(phoneStr)) {
        return false
    }
    return true;
}

/**
 * 전화번호 입력값인 숫자 및 -(하이픈)만 입력했는지 체크
 * @param obj
 * @return
 */
function checkPhoneNumber(obj) {
    if (!isPhoneNumber(obj)) {
        alert("숫자 또는 -(하이픈) 만 입력할 수 있습니다.");
        obj.value = "";
        return false;
    }
}


/**
 * 공백제거
 * @param str 체크할 대상 문자열
 * @return tmpStr 공백제거된 물자열
 */
function trim(str)
{
    var tmpStr = '';
    if(str != '' && typeof str != "undefined") {
        // alert(str);
        tmpStr = str.replace(/(^\s*)|(\s*$)/g, "");
    }
    //alert(tmpStr.length);
    return tmpStr;
}

/**
 * 입력체크 (같은 객체명이 복수개 있을때 사용)
 * @param iObj 체크할 대상 폼객체
 * @return (true:정상, false:체크에러)
 */
function checkInput(iObj) {
    //fObj = document.forms[0].elements;
    fObj = iObj.elements;
    for(var i=0; i < fObj.length; i++) {
        if (fObj[i].type == "text" || fObj[i].type == "password" || fObj[i].type == "textarea") {

            // 널체크
            if (fObj[i].getAttribute("required") != null) {
                if (fObj[i].value == "" || trim(fObj[i].value).length == 0) {
                    alert("" + "" + fObj[i].getAttribute("fname") + "(을)를 입력하세요.");
                    fObj[i].focus();
                    return false;
                }
            }
            // 영숫자 체크
            if (fObj[i].getAttribute("numeng") != null && fObj[i].value != "") {
                if (!validString(fObj[i].value, 'ID')) {
                    alert(fObj[i].getAttribute("fname") + "(은)는 영숫자만 입력 가능 합니다.");
                    fObj[i].focus();
                    return false;
                }
            }
            // 한글 체크
            if (fObj[i].getAttribute("han") != null && fObj[i].value != "") {
                if (!validStrKor(fObj[i].value)) {
                    alert(fObj[i].getAttribute("fname") + "(은)는 한글로 입력하세요.");
                    fObj[i].focus();
                    return false;
                }
            }
            // 숫자 체크(실수)
            if (fObj[i].getAttribute("num") != null && fObj[i].value != "") {
                var fname = fObj[i].getAttribute("fname")
                var tempObj = fObj[i];
                if (isNaN(fObj[i].value)) {
                    alert(fname + "(은)는 숫자로 입력해주세요.");
                    tempObj.focus();
                    return false;
                }
            }
            // 숫자 체크(정수)
            if (fObj[i].getAttribute("int") != null && fObj[i].value != "") {
                if (!checkNumber(fObj[i].value)) {
                    alert(fObj[i].getAttribute("fname") + "(은)는 숫자로 입력해주세요.");
                    fObj[i].focus();
                    return false;
                }
            }
            // 최대 입력 BYTE체크
            if (fObj[i].getAttribute("maxsize") != null && fObj[i].value != "") {
                var fname = fObj[i].getAttribute("fname");
                var maxlength = fObj[i].getAttribute("maxsize");
                var tempObj = fObj[i];
                str = fObj[i].value;
                if (getByteLength(str) > maxlength) {
                    //alert(fname + "이(가) 입력가능한 최대 길이를 넘었습니다." + " ["+maxlength "]" + "" + "[" + fname+ "]" + " 값의 글자수 : " + getByteLength(str) + "(한글은 2글자로 취급됩니다.)");
                    alert(fname + "이(가) 입력가능한 최대 길이를 넘었습니다." + " ["+maxlength+" ]" + ""+ " ["+fname+"]" +" 값의 글자수 : " + getByteLength(str) + "(한글은 2글자로 취급됩니다.)");
                    tempObj.focus();
                    return false;
                }
            }
            // 최대 입력 BYTE체크(엔터값포함)
            if (fObj[i].getAttribute("maxsize2") != null && fObj[i].value != "") {
                var fname = fObj[i].getAttribute("fname");
                var maxlength = fObj[i].getAttribute("maxsize2");
                var tempObj = fObj[i];
                str = fObj[i].value;
                if (getByteLength2(str) > maxlength) {
                    //alert(fname + "이(가) 입력가능한 최대 길이를 넘었습니다." + " ["+maxlength "]" + "" + "[" + fname+ "]" + " 값의 글자수 : " + getByteLength2(str) + "(한글은 2글자로 취급됩니다.)");
                    alert(fname + "이(가) 입력가능한 최대 길이를 넘었습니다." + " ["+maxlength+" ]" + ""+ " ["+fname+"]" +" 값의 글자수 : " + getByteLength2(str) + "(한글은 2글자로 취급됩니다.)");
                    tempObj.focus();
                    return false;
                }
            }
            //2010.11.09일 LSH 추가
            // 이메일입력체크
            if (fObj[i].getAttribute("email") != null && fObj[i].value != "") {
                if(!fObj[i].getAttribute("readOnly")){	//readOnly가 아닐 경우만 체크
                    if (!EmailCheck(fObj[i].value)) {
                        alert("유효한 이메일 데이타가 아닙니다");
                        fObj[i].focus();
                        return false;
                    }
                }
            }
            // 핸드폰입력체크
            if (fObj[i].getAttribute("mobile") != null && fObj[i].value != "") {
                if(!fObj[i].getAttribute("readOnly")){	//readOnly가 아닐 경우만 체크
                    if (!checkMobile(fObj[i].value)) {
                        alert("유효한 휴대폰 데이타가 아닙니다");
                        fObj[i].focus();
                        return false;
                    }
                }
            }
            // 일반전화번호입력체크
            if (fObj[i].getAttribute("phone") != null && fObj[i].value != "") {
                if(!fObj[i].getAttribute("readOnly")){	//readOnly가 아닐 경우만 체크
                    if (!checkPhone(fObj[i].value)) {
                        alert("유효한 전화번호 데이타가 아닙니다");
                        fObj[i].focus();
                        return false;
                    }
                }
            }
        } else if (fObj[i].type == "select-one") {
            // 필수선택체크
            if (fObj[i].getAttribute("required") != null) {
                var checkNum = 0;
                if (fObj[i].getAttribute("init") != null && fObj[i].getAttribute("init") == 'YES') {
                    checkNum = 1;
                }
                if (fObj[i].selectedIndex < checkNum) {
                    alert("" + "" + fObj[i].getAttribute("fname") + "(을)를 선택해 주세요.");
                    fObj[i].focus();
                    return false;
                }
            }
        }
    }
    return true;
}

/**
 * 입력체크 (체크할 항목이 단일 항목일 경우에 사용)
 * @param iObj 체크할 대상 폼객체
 * @return (true:정상, false:체크에러)
 */
function checkInputByName(iObj) {
    fObj = iObj;
    if (fObj.type == "text" || fObj.type == "password" || fObj.type == "textarea") {
        // 널체크
        if (fObj.getAttribute("required") != null) {
            if (fObj.value == "" || trim(fObj.value).length == 0) {
                alert("" + "" + fObj.getAttribute("fname") + "(을)를 입력하세요.");
                fObj.focus();
                return false;
            }
        }
        // 영숫자 체크
        if (fObj.getAttribute("numeng") != null && fObj.value != "") {
            if (!validString(fObj.value, 'ID')) {
                alert(fObj.getAttribute("fname") + "(은)는 영숫자만 입력 가능 합니다.");
                fObj.focus();
                return false;
            }
        }
        // 한글 체크
        if (fObj.getAttribute("han") != null && fObj.value != "") {
            if (!validStrKor(fObj.value)) {
                alert(fObj.getAttribute("fname") + "(은)는 한글로 입력하세요.");
                fObj.focus();
                return false;
            }
        }
        // 숫자 체크(실수)
        if (fObj.getAttribute("num") != null && fObj.value != "") {
            var fname = fObj.getAttribute("fname")
            var tempObj = fObj;
            if (isNaN(fObj.value)) {
                alert(fname + "(은)는 숫자로 입력해주세요.");
                tempObj.focus();
                return false;
            }
        }
        // 숫자 체크(정수)
        if (fObj.getAttribute("int") != null && fObj.value != "") {
            if (!checkNumber(fObj.value)) {
                alert(fObj.getAttribute("fname") + "(은)는 숫자로 입력해주세요.");
                fObj.focus();
                return false;
            }
        }
        // 최대 입력 BYTE체크
        if (fObj.getAttribute("maxsize") != null && fObj.value != "") {
            var fname = fObj.getAttribute("fname");
            var maxlength = fObj.getAttribute("maxsize");
            str = fObj.value;
            if (getByteLength(str) > maxlength) {
                alert(fname + "이(가) 입력가능한 최대 길이를 넘었습니다.");
                fObj.focus();
                return false;
            }
        }
        //2010.11.09일 LSH 추가
        // 이메일입력체크
        if (fObj.getAttribute("email") != null && fObj.value != "") {
            if(!fObj[i].getAttribute("readOnly")){	//readOnly가 아닐 경우만 체크
                if (!checkNumber(fObj.value)) {
                    alert("유효한 이메일 데이타가 아닙니다");
                    fObj.focus();
                    return false;
                }
            }
        }
        // 핸드폰입력체크
        if (fObj.getAttribute("mobile") != null && fObj.value != "") {
            if(!fObj[i].getAttribute("readOnly")){	//readOnly가 아닐 경우만 체크
                if (!checkNumber(fObj.value)) {
                    alert("유효한 휴대폰 데이타가 아닙니다");
                    fObj.focus();
                    return false;
                }
            }
        }
        // 일반전화번호입력체크
        if (fObj.getAttribute("phone") != null && fObj.value != "") {
            if(!fObj[i].getAttribute("readOnly")){	//readOnly가 아닐 경우만 체크
                if (!checkNumber(fObj.value)) {
                    alert("유효한 전화번호 데이타가 아닙니다");
                    fObj.focus();
                    return false;
                }
            }
        }
    } else if (fObj.type == "select-one") {
        // 필수선택체크
        if (fObj.getAttribute("required") != null) {
            var checkNum = 0;
            if (fObj.getAttribute("init") != null && fObj.getAttribute("init") == 'YES') {
                checkNum = 1;
            }
            if (fObj.selectedIndex < checkNum) {
                //alert(Language);
                alert("" + "" + fObj.getAttribute("fname") + "(을)를 선택해 주세요.");
                fObj.focus();
                return false;
            }
        }
    }
    return true;
}
/**
 * 문자열의 BYTE체크
 * @param index 바이트체크할 대상문자열
 * @return num 바이트수 리턴
 */
function getByteLength(index) {
    var i,cnt = 0;
    for (i = 0; i < index.length; i++) {
        if (escape(index.charAt(i)).length >= 4){
            cnt+= 2;
        }else {
            cnt++;
        }
    }
    return cnt;
}

/**
 * 문자열의 BYTE체크(엔터포함)
 * @param index 바이트체크할 대상문자열
 * @return num 바이트수 리턴
 */
function getByteLength2(index) {
    var i,cnt = 0;
    for (i = 0; i < index.length; i++) {
        if (escape(index.charAt(i)).length >= 4 || escape(index.charAt(i)) == '%0A'){
            cnt+= 2;
        }else {
            cnt++;
        }
    }
    return cnt;
}

/**
 * 날짜의 유효성을 체크한다.
 */
function validDateCheck(element, format){
    if(format=="yyyymmdd"){
        var iCount =0;
        var st_val = element.value.replace(/-/gi,"");
        if (st_val.length>0){
            if (st_val.length != 8){
                alert(htc_msg_0003);	//날짜형식에 맞지 않습니다.
                element.focus();
                return false;
            }
            st_Year = eval(st_val.substring(0,4));
            st_Month = eval(st_val.substring(4,6));
            st_Day = eval(st_val.substring(6,8));
            if ("01" <= st_Month && st_Month <="12"){
                if (st_Day <= 0 || st_Day > 30 + ((st_Month==4 || st_Month==6 || st_Month==9 || st_Month==11)?0:1) ||
                    (st_Month==2&&st_Day>28+(((st_Year%4==0&&st_Year%100!=0)||st_Year%400==0)?1:0))){
                }else{
                    return true;
                }
            }
            alert(htc_msg_0004);	//유효한 날짜가 아닙니다.
            element.focus();
            return false;
        }else{
            return true;
        }

    }else if(format=="yyyymm"){
        var iCount =0;
        //var st_val = element.value;
        var st_val = element.value.replace(/-/gi,"");
        if (st_val.length>0){
            if (st_val.length < 6){
                alert(htc_msg_0005);	//입력형식에 맞지 않습니다.
                element.focus();
                return false;
            }
            st_Year = eval(st_val.substring(0,4));
            st_Month = eval(st_val.substring(4,6));
            if ("01" <= st_Month && st_Month <="12"){
                return true;
            }
            alert(htc_msg_0006);	//유효한 데이타가 아닙니다.
            element.focus();
            return false;
        }else{
            return true;
        }

    }else{
        return true;
    }
}
/**
 * 날짜의 유효성체크
 * @param fObj 날짜 체크할 폼객체
 * @return (true:정상, false:날짜데이터이외에러)
 */
function checkDate(fObj){
    var iCount =0;
    var fname = fObj.getAttribute("fname");
    var st_val = fObj.value;
    if (st_val.length != 8){
        alert(fname + "은(는) 유효한 날짜  데이터가 아닙니다.");
        fObj.focus();
        return false;
    }
    if (!checkNumber(st_val)) {
        alert(fname + "은(는) 유효한 날짜  데이터가 아닙니다.");
        fObj.focus();
        return false;
    }
    st_Year = eval(st_val.substring(0,4));
    st_Month = eval(st_val.substring(4,6));
    st_Day = eval(st_val.substring(6,8));
    if ("01" <= st_Month && st_Month <="12"){
        if (st_Day <= 0 || st_Day > 30 + ((st_Month==4 || st_Month==6 || st_Month==9 || st_Month==11)?0:1) ||
            (st_Month==2&&st_Day>28+(((st_Year%4==0&&st_Year%100!=0)||st_Year%400==0)?1:0))){
        }else{
            return true;
        }
    }

    alert(fname + "은(는) 유효한 날짜  데이터가 아닙니다.");
    fObj.focus();
    return false;
}

/**
 * 시간의 유효성체크
 * @param fObj 시간 체크할 폼객체
 * @return (true:정상, false:시간데이터이외에러)
 */
function checkTime(fObj){
    var iCount =0;
    var fname = fObj.getAttribute("fname");
    var st_val = fObj.value;
    if (st_val.length != 4){
        alert(fname + "은(는) 유효한 시간 데이터가 아닙니다.");
        fObj.focus();
        return false;
    }
    if (!checkNumber(st_val)) {
        alert(fname + "은(는) 유효한 시간 데이터가 아닙니다.");
        fObj.focus();
        return false;
    }
    st_Hour = eval(st_val.substring(0,2));
    st_Min = eval(st_val.substring(2,4));

    if ("00" <= st_Hour && st_Hour < "24"){
        if ("00" <= st_Min && st_Min < "60"){
            return true;
        }
    }

    alert(fname + "은(는) 유효한 시간 데이터가 아닙니다.");
    fObj.focus();
    return false;
}

/**
 * 숫자형 체크 (순수숫자이외는 모두 에러로 처리)
 * @param valData 체크할 문자열 객체
 * @return (true:정상, false:숫자이외에러)
 */
function checkNumber(valData) {
    if (isNaN(valData)){
        return false;
    } else {
        for (j=0; j<valData.length;j++) {
            var j_char = valData.substring(j, j+1);
            if (j_char == "+" || j_char == "-" || j_char == ".") {
                return false;
            }
        }
        return true;
    }
}

/**
 * 체크박스 중  선택된 것이 있는지 없는지 check
 * @param obj 체크박스 오브젝트
 * @return (true:있음, false:없음)
 */
function hasChecked(obj){
    if(obj!=null){ //obj null check
        if(obj.length){	//length가 존재한다면 obj가 2이상이라는 뜻
            for(var i=0;i<obj.length;i++){	//1개라도 체크된것이 있으면 true return;
                if(obj[i].checked==true){
                    return true;
                }
            }
        }else{	//length가 존재하지 않으면 obj가 1개라는 뜻
            if(obj.checked==true){
                return true;
            }
        }
    }
    return false;
}

/**
 * 체크박스 중  선택된 것의 값을 리턴
 * @param obj 체크박스 오브젝트
 * @return 선택된 값 string
 */
function getCheckedValues(obj){
    var str = "";
    if(obj!=null){ //obj null check
        if(obj.length){	//length가 존재한다면 obj가 2이상이라는 뜻
            for(var i=0;i<obj.length;i++){	//1개라도 체크된것이 있으면 true return;
                if(obj[i].checked==true){
                    str += obj[i].value+";";
                }
            }
        }else{	//length가 존재하지 않으면 obj가 1개라는 뜻
            if(obj.checked==true){
                str += obj.value;
            }
        }
    }
    return str;
}

/**
 * 라디오버튼의 체크된 값을 가져옴.
 * @param obj 라디오버튼 오브젝트
 * @return str
 */
function getRadioValue(obj){
    var objInfo = obj.value;
    var leng = obj.length;

    if(leng == undefined){
        leng = 1;
        return objInfo;
    }else{
        for(var i=0;i<leng;i++){
            if(obj[i].checked == true){
                return obj[i].value;
            }
        }
    }
    return "";
}
/**
 * 라디오버튼의 값을 세팅
 * @param obj 라디오버튼 오브젝트
 * @param checkedVal 세팅값
 * @return str
 */
function setCheckedValue(obj, checkedVal){
    if(obj.length == undefined){
        obj.checked = true;
    }else{
        if(checkedVal!='' && obj.length>0){
            for(var i=0;i<obj.length;i++){
                if(obj[i].value == checkedVal){
                    obj[i].checked = true;
                }
            }
        }
    }
}

/**
 * 전체 체크박스를 체크혹은 체크해제
 * @param obj form객체
 * @param targetName 체크할 객체의 이름
 */
function checkOneMoreCheckbox(obj, targetName) {
    var bCheck = false;
    fObj = obj.elements;
    for (i = 0 ; i < fObj.length; i++) {
        if (fObj[i].type == "checkbox" && fObj[i].name == targetName) {
            if (fObj[i].checked) {
                bCheck = true;
                break;
            }
        }
    }
    if (!bCheck) {
        alert("체크박스를 선택해 주세요!");
    }
    return bCheck
}

/**
 * 전체 체크박스를 체크혹은 체크해제
 * @param obj form객체
 * @param targetName 체크할 객체의 이름
 */
function checkOneMoreCheckboxUser(obj, targetName) {
    var bCheck = false;
    fObj = obj.elements;
    for (i = 0 ; i < fObj.length; i++) {
        if (fObj[i].type == "checkbox" && fObj[i].name == targetName) {
            if (fObj[i].checked) {
                bCheck = true;
                break;
            }
        }
    }
    if (!bCheck) {
        alert("관리자를 선택해 주세요!");
    }
    return bCheck
}

/**
 * 체크박스를 전체체크 혹은 전체해제
 * @param obj 이벤트 객체
 */
function checkAll(obj){
    if(obj.checked){
        allBoxCheck(true);
    }else{
        allBoxCheck(false);
    }
}
/**
 * 체크박스를 전체체크 혹은 전체해제
 * @param val (true, false)
 */
function allBoxCheck(val) {
    var allInput = document.getElementsByTagName("INPUT");
    for( var i=0 ; i<allInput.length ; i++ ) {
        var input = allInput[i];
        //체크박스가 disabled 된 것은 선택하지 않는다.
        if( input.type == "checkbox" && input.disabled == false) {
            input.checked = val;
        }
    }
}

/**
 * 체크박스를 전체체크 혹은 전체해제(해당객체만)
 * @param obj 이벤트 객체
 * @param targetName 체크할 객체의 이름
 */
function checkAllTagerName(obj, targetName){
    if(obj.checked){
        allBoxCheckTagerName(true, targetName);
    }else{
        allBoxCheckTagerName(false, targetName);
    }
}
/**
 * 체크박스를 전체체크 혹은 전체해제
 * @param val (true, false)
 * @param targetName 체크할 객체의 이름
 */
function allBoxCheckTagerName(val, targetName) {
    var allInput = document.getElementsByTagName("INPUT");
    for( var i=0 ; i<allInput.length ; i++ ) {
        var input = allInput[i];
        //체크박스가 disabled 된 것은 선택하지 않는다.
        if( input.type == "checkbox" && input.name == targetName && input.disabled == false) {
            input.checked = val;
        }
    }
}

/**
 * 체크박스를 전체체크 혹은 전체해제(해당객체만, 값에 따라서)
 * @param obj 이벤트 객체
 * @param targetName 체크할 객체의 이름
 * @param code 비교할 코드값
 */
function checkAllTagerValue(obj, targetName, code){
    if(obj.checked){
        allBoxCheckTagerValue(true, targetName, code);
    }else{
        allBoxCheckTagerValue(false, targetName, code);
    }
}
/**
 * 체크박스를 전체체크 혹은 전체해제
 * @param val (true, false)
 * @param targetName 체크할 객체의 이름
 * @param code 비교할 코드값
 */
function allBoxCheckTagerValue(val, targetName, code) {
    var allInput = document.getElementsByTagName("INPUT");
    for( var i=0 ; i<allInput.length ; i++ ) {
        var input = allInput[i];
        if( input.type == "checkbox" && input.name == targetName
            && input.value != code) {
            input.checked = val;
        }
    }
}

/**
 * 넘겨받은 object의 길이를 체크하여 다음(where)으로 focus를 이동
 * @param obj 체크객체
 * @param where 다음객체
 * @param size 체크사이즈
 */
function autoTab(obj, where, size) {
    var len = obj.value.length;
    if(len>=size){
        where.focus();
    }
}

function setTextDisabled(obj){
    obj.value = "";
    obj.readOnly = true;
    obj.style.background = "#EEEEEE";
}
function setTextEnabled(obj){
    obj.readOnly = false;
    obj.style.background = "#FFFFFF";
}
function setSelectDisabled(obj){
    obj.selectedIndex = 0;
    obj.disabled = true;
}
function setSelectEnabled(obj){
    obj.disabled = false;
}

/**
 * 날짜를 특정 개월수 만큼 이동
 * @param Dobj 날짜객체(YYYY-MM)
 * @param cnt 개월수
 */
function calcMonth(Dobj, cnt){
    var pdate = Dobj.value.replace(/-/gi,"");
    var yyyy = pdate.substring(0,4);
    var mm = pdate.substring(4,6) * 1;
    var dd = "01";
    if(cnt==0){
        cnt = 1;
    }
    var newMon = mm + parseInt(cnt-1) - 1;
    var newDate = new Date(yyyy, newMon, dd);
    if((newDate.getMonth()+1)<10){
        newMon = "0"+(newDate.getMonth()+1);
    }else{
        newMon = (newDate.getMonth()+1);
    }
    return newDate.getFullYear()+"-"+newMon;
}

function compareSdateEdate(Eobj, Sobj, msg){
    if(msg==undefined){
        msg = "종료일이 시작일보다 작습니다.";	//종료일이 시작일 보다 작습니다
    }
    var startDate = Sobj.value.replace(/-/gi,"");
    var endDate = Eobj.value.replace(/-/gi,"");
    if(startDate!="" && endDate!=""){
        var start_yyyy = startDate.substring(0,4);
        var start_mm = startDate.substring(4,6);
        var start_dd = startDate.substring(6,startDate.length);
        var sDate = new Date(start_yyyy, start_mm-1, start_dd);

        var end_yyyy = endDate.substring(0,4);
        var end_mm = endDate.substring(4,6);
        var end_dd = endDate.substring(6,endDate.length);
        var eDate = new Date(end_yyyy, end_mm-1, end_dd);

        var diff = Math.ceil((eDate.getTime() - sDate.getTime())/(1000*60*60*24));
        if(diff<0){
            alert(msg);
            Eobj.focus();
            return false;
        }
    }
    return true;
}
function compareSvalueEvalue(Eobj, Sobj){
    compareSvalueEvalue(Eobj, Sobj, "TO의 값이 FROM값보다 작습니다");	//TO의 값이 FROM값보다 작습니다.
}
function compareSvalueEvalue(Eobj, Sobj, msg){
    var startValue = Sobj.value.replace(/,/gi,"");
    var endValue = Eobj.value.replace(/,/gi,"");
    if(startValue!="" && endValue!=""){
        var diff = endValue - startValue;
        if(diff<0){
            alert(msg);
            Eobj.focus();
            return false;
        }
    }
    return true;
}

function setSelectedValue(obj, selectedVal){
    if(selectedVal !="" && obj != null && obj.length>0){
        for(var i=0;i<obj.length;i++){
            if(obj[i].value == selectedVal){
                obj[i].selected = true;
                return;
            }
        }
    }
}

function setSelectedIndex(obj, idx){
    if(obj.length>0){
        for(var i=0;i<obj.length;i++){
            if(i==idx){
                obj[i].selected = true;
                return;
            }
        }
    }
}

function getSelectedIndex(obj, val){
    var idx =0;
    if(obj.length>0){
        for(var i=0;i<obj.length;i++){
            if(obj[i].value == val){
                return i;
            }
        }
        return idx;
    }else{
        return idx;
    }
}

function getSelectedValue(obj, idx){
    var objIdx = getSelectedIndex(obj, idx);
    if(obj.length>0){
        for(var i=0;i<obj.length;i++){
            if(i==idx){
                obj[i].selected = true;
                return obj.options[objIdx].text;
            }
        }
    }
}

/**
 * 동일한 오브젝트가 싱글 혹은 멀티로 사용될 경우 오브젝트의 값을 취득
 * @param obj 객체
 * @param idx 인텍스
 */
/**
 function getValue(obj, idx){
	if(obj != undefined) {
		if(obj.length && obj.length>1){
			if(obj.type == "select-one"){
				return obj.value;
			}else{
				return obj[idx].value;
			}
		}else{
			return obj.value;
		}
	}
}
 */
/**
 * 동일한 오브젝트가 싱글 혹은 멀티로 사용될 경우 오브젝트를 취득
 * @param obj 객체
 * @param idx 인텍스
 */
function getObject(obj, idx){
    if(obj.length && obj.length>1){
        if(obj.type == "select-one"){
            return obj;
        }else{
            return obj[idx];
        }
    }else{
        return obj;
    }
}
/**
 * 동일한 오브젝트가 싱글 혹은 멀티로 사용될 경우 오브젝트의 갯수를 취득
 * @param obj 객체
 */
function getObjectLen(obj){
    var objLen = 0;
    if(obj==null){
        objLen = 0;
    }else if(obj.length){		//length가 존재한다면 obj가 2이상이라는 뜻
        objLen = obj.length;
    }else{
        objLen = 1;
    }
    return objLen;
}
/**
 * 동일한 오브젝트가 싱글 혹은 멀티로 사용될 경우 오브젝트 명칭을 취득
 * @param obj 객체
 * @param idx 인텍스
 */
function getObjectName(obj, idx){
    if(obj.length && obj.length>1){
        if(obj.type == "select-one"){
            return obj.name;
        }else{
            return obj[idx].name+"["+idx+"]";
        }
    }else{
        return obj.name;
    }
}

/**
 * 동일한 오브젝트가 싱글 혹은 멀티로 사용될 경우 오브젝트의 ATTRIBUTE값을 취득
 * @param obj 객체
 * @param idx 인텍스
 * @param attribute 애트리뷰트
 */
function getAttributeValue(obj, idx, attribute){
    if(obj.length && obj.length>1){
        if(obj.type == "select-one"){
            return obj.getAttribute(attribute);
        }else{
            return obj[idx].getAttribute(attribute);
        }
    }else{
        return obj.getAttribute(attribute);
    }
}

function showObjectError(obj){
    obj.style.backgroundColor="#FF8080";
    obj.focus();
}
function showObjectSucc(obj){
    obj.style.backgroundColor="#FFFFFF";
}
function showObjectReadOnly(obj){
    obj.style.backgroundColor="#EEEEEE";
}

/**
 * 지정된 문자(특문)가 들어오면 validation Check
 * @param obj 객체
 */
function validationHost(obj){

    //공백제거
//	obj.value = obj.value.replace(/\s/g,'');

    if(obj.value.search(/[\",\',<,>,(,),_,!,@,#,$,%,^,&,*,-,+,/,?,\\,|,=]/g) >= 0) {
        alert("문자열에 특수문자가 있습니다.특수문자를 제거하여 주십시요!");
        obj.select();
        obj.focus();
        return false;

    }else{
        return true;
    }

}


/**
 * 신용카드 넘버인지 판단
 * @param obj 객체
 */
function isCardNumber(obj) {
    var str = obj.value;
    if(str.length != 16)
        return true;

    for(var i=0; i < 16; i++) {
        if(!('0' <= str.charAt(i) && str.charAt(i) <= '9'))
            return true;
    }
    return false;
}

/**
 * 데이터가 숫자인지 체크한다.
 * @param obj 객체
 */
function isNumber(obj) {
    var str = obj.value;
    if(str.length == 0)
        return false;

    for(var i=0; i < str.length; i++) {
        if(!('0' <= str.charAt(i) && str.charAt(i) <= '9'))
            return false;
    }
    return true;
}

/**
 * 입력값이  전화번호에 해당하는 숫자와 문자 - 만 있는지 체크
 * ex) if (!isNumber(var)) {
 *         alert("숫자값만 입력할 수 있습니다.");
 *     }
 */
function isPhoneNumber(input) {
    var sChars = "1234567890-";
    return isDefindeChars(input, sChars);
}

/**
 * 입력값이 알파벳인지 체크
 * ex) if (!isAlphabet(var)) {
 *         alert("알파벳만 입력할 수 있습니다.");
 *     }
 */
function isAlphabet(input) {
    var sChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    return isDefindeChars(input, sChars);
}

/**
 * 입력값이 특정 문자만으로 되어있는지 체크
 * 특정 문자만 허용하려 할 때 사용
 * ex) if (!isDefindeChars(var, "ABC")) {
 *         alert("A,B,C 문자만 사용할 수 있습니다.");
 *     }
 */
function isDefindeChars(input, chars) {
    for (var i = 0; i < input.value.length; i++) {
        if (chars.indexOf(input.value.charAt(i)) == -1)
            return false;
    }
    return true;
}



/**
 * 컴마찍기
 * @param obj 객체
 */
function toComma(objVal) {
    if (objVal == "") {
        return "";
    }
    var sOrg = objVal;
    var sRetVal     = "";
    var sTmpVal     = "";
    var sFractionVal  = "";
    sOrg = sOrg.toString();
    sOrg = sOrg.replace(/,/g,"")
    var lLengh = sOrg.search(/\./);
    if (lLengh<0) {
        lLengh = sOrg.length;
    } else {
        sFractionVal = sOrg.substr(lLengh);
    }

    lLengh    = lLengh;
    var lRemainder  = lLengh % 3;
    if (lRemainder == 0 && lLengh > 0) {
        lRemainder  = 3;
    }
    sRetVal = sOrg.substr(0,lRemainder);
    while(lRemainder < lLengh) {
        sTmpVal = sTmpVal + "," + sOrg.substr(lRemainder,3);
        lRemainder  += 3;
    }
    sRetVal = sRetVal + sTmpVal + sFractionVal;

    return sRetVal;
}


//---------------------------------------------------------------------
//길이를 업데이트하고 최대길이가 넘으면 입력할 수 없도록 한다.
//(최대길이 만큼으로 텍스트를 줄인다.)
//최대길이-1을 하는 이유는 엔터키가 2글자로 인식되서 중간에 잘리는
//에러를 방지하기 위함.
//---------------------------------------------------------------------
//호출예 : onpropertychange =
//"javascript:updateLength(document.form.txt_len, document.form.msg_cntn, 400);"
//마우스로 붙여넣기 하는 경우 등을 고려해서 onpropertychange를 사용.
//---------------------------------------------------------------------
//counter  : 텍스트의 길이 출력부분 ex) document.form.txt_len
//msgObj : 메시지 입력부분 ex) document.form.msg_cntn
//maxlen  : 최대길이 ex) 400
//---------------------------------------------------------------------
function updateLength(counter, msgObj, maxlen) {
    counter.value = msgObj.value.length;
    if (msgObj.value.length > maxlen) {  // 최대글자수를 초과하면
        alert(""+ maxlen + " 자 이상 입력하셨습니다");

        var tmpstr = msgObj.value;
        tmpstr = tmpstr.substring(0, maxlen) // 최대글자수로 잘라낸다.



        if (tmpstr.substring(maxlen-1, maxlen) == '\r')  // 마지막 문자가 '\r'이면 (엔터키 앞문자이면)
            tmpstr = tmpstr.substr(0, maxlen-1);   // 지운다.

        msgObj.value = tmpstr;
        return;
    }

    counter.value = msgObj.value.length;
}
/**
 * 최대값 maxValue:100 을 넘기면 스크립트 메세지를 부여줌.
 * 호출예 : onkeyup="javascript:updateValue(document.myForm.cpuMinorPer, 100);"
 * @param msgObj
 * @param maxValue
 * @return
 */
function updateValue(msgObj, maxValue) {

    if (msgObj.value > maxValue) {  // 최대값을 초과하면
        alert(""+ maxValue + " 이상 입력하셨습니다");
        msgObj.value = maxValue;
        return;
    }
}

/**
 * input 컴포넌트 입력할 수 있는 자리수 제한
 * @param obj : 폼이름
 * @param length : checkbox 이름
 *
 */
/*
 function checkStrLength(obj, legnth){
 var objLength = obj.value.length;
 if(objLength > legnth){

 alert(""+ legnth + " 자 이상 입력하셨습니다");

 var tmpstr = obj.value;
 tmpstr = tmpstr.substring(0, legnth) // 최대글자수로 잘라낸다.
 obj.value = tmpstr;
 */
function checkStrLength(obj, dbByte){
    var val = obj.value;
    var langByte = LANG_BYTE;
    // 입력받은 문자열을 escape() 를 이용하여 변환한다.
    // 변환한 문자열 중 유니코드(한글 등)는 공통적으로 %uxxxx로 변환된다.
    var temp_estr = escape(val);
    var s_index   = 0;
    var e_index   = 0;
    var temp_str  = "";
    var cnt       = 0;

    // 문자열 중에서 유니코드를 찾아 제거하면서 갯수를 센다.

    while ((e_index = temp_estr.indexOf("%u", s_index)) >= 0)  // 제거할 문자열이 존재한다면
    {
        temp_str += temp_estr.substring(s_index, e_index);
        s_index = e_index + 6;
        cnt ++;
    }

    temp_str += temp_estr.substring(s_index);
    temp_str = unescape(temp_str);  // 원래 문자열로 바꾼다.
    // 유니코드는 2바이트 씩 계산하고 나머지는 1바이트씩 계산한다.

    if(dbByte < ((cnt * langByte) + temp_str.length)){
        alert("입력할 수 있는 글자 수를 초과했습니다.");
        //alert("byte : " + ((cnt * langByte) + temp_str.length) + "");
        //alert("length : " + obj.value.length);

        obj.value = val.substring(0, (obj.value.length-1)) // 최대글자수로 잘라낸다.
    }
}


/**
 * 전화번호 포멧 변환
 * @param num 객체
 */
function phone_format(num){
    return num.replace(/(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})/,"$1-$2-$3");
}

/**
 * 들어온 숫자를 금액 타입으로 변경시켜준다.
 * ex) 121212121 -> 121,212,121
 * @param {Object} obj 사용자가 입력한 숫자 타입의 값
 * @author Once
 */
function formattedMoney(obj) {
    var format = "";
    var err =1;
    var a=removeFormat(document.all[obj].value.toString(),',');
    if(a == "_&^&_")
        err = 1;
    else
        err = 0;
    a = parseInt(a);
    var money =a.toString();
    money = reverse(money);

    for(var i = money.length-1; i > -1; i--) {
        if((i+1)%3 == 0 && money.length-1 != i) format += ",";
        format += money.charAt(i);
    }

    if(err == 1){
        alert("입력값이 숫자 가 아닙니다.");
        return "";
    }else{
        return format;
    }
}
/**
 *강제 지연
 * @param mills 초
 */
function pausecomp(mills){
    var date = new Date();
    var curDate = null;
    do{curDate = new Date();}
    while(curDate-date < mills);
}




/**
 * CheckBox를 Hidden으로 바꿔주는 함수
 * 선택 안된 체크박스 값 넘기기
 * @param f : 폼이름
 * @param ele : checkbox 이름
 *
 */
function CheckboxToHidden(f,ele) {
    var ele_h;
    var val;

    if (typeof(ele.length) != "undefined") {// checkbox가 배열일경우
        for (var i = 0; i < ele.length; i++) {
            // hidden객체생성, 이름은 checkbox와 같게한다.
            ele_h = document.createElement("input");
            ele_h.setAttribute("type","hidden");
            ele_h.setAttribute("name",ele[i].name);
            ele[i].checked ? val = ele[i].value : val = "";
            ele_h.setAttribute("value",val);
            f.appendChild(ele_h);

            // 기존 checkbox의 이름을 이름_dummy로 변경한후 checked = false해준다.
            ele[i].checked = false;
            ele[i].setAttribute("name",ele[i].name + "_dummy");
        }
    } else {// checkbox가 한개
        ele_h = document.createElement("input");
        ele_h.setAttribute("type","hidden");
        ele_h.setAttribute("name",ele.name);
        ele.checked ? val = ele.value : val = "";
        ele_h.setAttribute("value",val);
        f.appendChild(ele_h);

        ele.checked = false;
        ele.setAttribute("name",ele.name + "_dummy");
    }
}

/**
 * input 이메일 체크 로직
 * @param Email : 폼이름
 * @param Result :  이메일 체크  Y/N
 *
 */
function EmailCheck(Email){
    var filter = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i
    if (filter.test(Email)){
        Result = true;
    }else{
        Result = false;
    }
    return Result;
}

function checkScript(obj) {
    var val = $(obj).val().toLowerCase();

    if (val.indexOf("script") != -1) {
        alert("스크립트는 사용할수 없습니다.")
        return false;
    }

    if (val.indexOf("iframe") != -1) {
        alert("iframe은 사용할수 없습니다.")
        return false;
    }

    if (val.indexOf("<") != -1) {
        alert("특수문자는 입력할수 없습니다.")
        return false;
    }

    if (val.indexOf(">") != -1) {
        alert("특수문자는 입력할수 없습니다.")
        return false;
    }

    return true;
}

function checkConfirm(){
    return confirm(""+"정말 진행하시겠습니까?"+"");
}


function checkUploadFile(fileName){
    var special_pattern = /[`~!@+#$%^&*|\\\'\";:\/=?]/gi;

    var fname=fileName;
    fname = fname.split("\\");
    f_name = fname[fname.length-1];
    if( special_pattern.test(f_name) == true ){
        return false;
    }
    return true;
}


//*********************************************************
//** 행병합
//*********************************************************
function mergeRows(a, b) {
    var argu  = mergeRows.arguments;
    var cnt  = 1;                                        // rowspan 값
    var oTbl  = argu[0];                                  // 비교할 Table Object, default=첫번째 테이블
    var oRow;                                            // 현재 Row Object
    var oCell;                                            // 현재 Cell Object
    var iRow;                                            // 이전에 일치했던 Row Index
    var iCell = argu[1] == null ? 0 : argu[1];            // 비교할 Cell Index, default=0
    var vPre;                                            // 이전에 일치했던 값
    var vCur;                                            // 현재 값
    var bChk  = false;                                    // 처음 일치인지 여부

    try {
        for (var i=0; i<oTbl.rows.length; i++) {            // Row Index만큼 Loop
            for (var j=0; j<oTbl.rows[i].cells.length; j++) { // Cell Index 만큼 Loop
                if (iCell == -1 || iCell == j) {                              // 비교할 Cell Index와 현재 Cell Index가 동일하면,,
                    vCur = oTbl.rows[i].cells[j].innerHTML;
                    //	alert(vCur);
                    if (vPre == vCur) {                          // 이전값과 현재값이 동일하면,,
                        if (bChk == false) {                        // 처음 일치시에만 적용
                            iRow = i-1;
                            bChk = true;
                        } // end of if
                        cnt++;

                        oTbl.rows[iRow].cells[j].rowSpan = cnt;
                        oTbl.rows[i].deleteCell(j);
                    } else {                                      // 이전값과 현재값이 다르면,,
                        cnt = 1;
                        vPre = vCur;
                        bChk = false;
                    } // end of if

                    break;
                } // end of if
            } // end of for
        } // end of for
    } catch (e) {
        alert(e.description);
    } // end of try{} catch{}
} // end of function
