import {Link} from 'react-router-dom';

export default function Login(){
    return (
        <div class="loginPage">
            <div class="title">
                <h1>로그인 페이지</h1>        
            </div>
            <div class="loginForm">
                <div class="inputBox">
                    <input type='text' placeholder='아이디를 입력하세요.' />
                </div>
                <div class="inputBox">
                    <input type='password' placeholder='비밀번호를 입력하세요.' />
                </div>
                <div class="inputBox">
                    <button class="loginBtn">로그인</button>
                </div>
                <div class="inputBox">
                    <Link to="/map"><button class="registBtn">회원가입</button></Link>
                </div>
            </div>
        
        </div>
    )
}