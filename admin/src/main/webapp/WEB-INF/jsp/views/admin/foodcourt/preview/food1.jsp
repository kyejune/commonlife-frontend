<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/views/common/commonHead.jsp" %>
<!doctype html>
<html lang="ko">
<head>
	<meta charset="UTF-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<title>덕평자연휴게소</title>
	<link rel="stylesheet" href="/css/front/fonts.css" />
	<link rel="stylesheet" href="/css/front/style.css" />

	<script src="/js/jquery.js"></script>
	<script src="/js/front/style.js"></script>

</head>
<body>
	<!-- Wrapper -->
	<div id="wrapper">

		<!-- Header -->
		<div id="header">
			<h1><a href="#"><img src="/images/logo.png" alt="덕평자연휴게소" /></a></h1>
			<ul id="navi">
				<li class="item_01">
					<a href="javascript:">시설안내</a>
					<ul>
						<li><a href="#">휴식공간</a></li>
						<li><a href="#">콘텐츠시설</a></li>
						<li><a href="#">편의시설</a></li>
						<li><a href="#">달려라KoKo</a></li>
						<li><a href="#">OOOZOOO</a></li>
					</ul>
				</li>
				<li class="item_02">
					<a href="javascript:">식사/간식</a>
					<ul>
						<li><a href="#">푸드코트</a></li>
						<li><a href="#">전문식당</a></li>
						<li><a href="#">디저트/카페</a></li>
					</ul>
				</li>
				<li class="item_03">
					<a href="javascript:">쇼핑정보</a>
					<ul>
						<li><a href="#">쇼핑몰</a></li>
						<li><a href="#">특별행사</a></li>
					</ul>
				</li>
				<li class="item_04">
					<a href="javascript:">덕평소식</a>
					<ul>
						<li><a href="#">공지사항</a></li>
						<li><a href="#">이벤트</a></li>
					</ul>
				</li>
				<li class="item_05">
					<a href="javascript:">고객센터</a>
					<ul>
						<li><a href="#">자주 묻는 질문</a></li>
						<li><a href="#">고객의 소리</a></li>
						<li><a href="#">단체주문</a></li>
						<li><a href="#">입점/프로모션 문의</a></li>
						<li><a href="#">오시는 길</a></li>
					</ul>
				</li>
			</ul>
			<ul id="link">
				<li><a href="http://www.ooozooo.co.kr" target="_blank"><img src="/images/link_01.png" alt="OOOZOOO" /></a></li>
				<li><a href="https://www.runkoko.com" target="_blank"><img src="/images/link_02.png" alt="달려라 KoKo" /></a></li>
			</ul>
			<div id="subnavi"></div>
		</div>
		<!-- // Header -->

		<!-- Container -->
		<div id="container">
			<div id="visual_sub" class="visual_02">
				<div class="title">
					<h2 class="nsq">푸드코트</h2>
					<p>휴게소음식 그 이상의 맛</p>
				</div>
			</div>

			<div id="contents" class="wide">
				<div id="main_menu">
					<div class="txt">
						<p class="label">대표메뉴</p>
						<ul>
							<li>
								<dl>
									<dt>덕평 소고기국밥</dt>
									<dd>전국 고속도로 휴게소 메뉴 판매 1위!<br />이제 휴게소 음식을 넘어 국민메뉴로.</dd>
									<dd class="price nsq">₩ 6,900</dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>수제 돈가스</dt>
									<dd>덕평자연휴게소 2등 메뉴. <br />특유의 바삭한 튀김옷과 담백한 고기가 일품.</dd>
									<dd class="price nsq">₩ 8,000</dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>육개장</dt>
									<dd>얼큰하고 진한 맛과 건강을 위한 완성형 메뉴</dd>
									<dd class="price nsq">₩ 8,000</dd>
								</dl>
							</li>
							<li>
								<dl>
									<dt>냄비라면</dt>
									<dd>끓이는 방식부터 다르다! <br />덕평만의 화끈한 조리법을 맛보세요.</dd>
									<dd class="price nsq">₩ 4,000</dd>
								</dl>
							</li>
						</ul>
						<p class="page"><span class="current">1</span> / <span class="total">4</span></p>
						<p class="btn"><a class="btn_prev" href="javascript:"></a><a class="btn_next" href="javascript:"></a></p>
					</div>
					<div class="img">
						<ul>
							<li class="item_01"></li>
							<li class="item_02"></li>
							<li class="item_03"></li>
							<li class="item_04"></li>
						</ul>
					</div>
				</div>

				<!-- =======================   -->
				<!-- == DATA START   -->
				<!-- =======================   -->
				<c:if test="${contentHtml != null }">
					<c:out value="${contentHtml}" escapeXml="false"/>
				</c:if>


				<!-- =======================   -->
				<!-- == DATA END   -->
				<!-- =======================   -->

			</div>
		</div>
		<!-- // Container -->

		<!-- Footer -->
		<div id="footer">
			<div class="top">
				<div class="inner">
					<ul>
						<li><a href="#">회사소개</a></li>
						<li><a href="#">채용정보</a></li>
						<li><a href="#">윤리경영</a></li>
						<li class="point"><a href="#">오시는 길</a></li>
						<li class="point"><a href="#">단체주문</a></li>
					</ul>
					<div>
						<span>공지</span >
						<p><a href="#">덕평자연휴게소 홈페이지가 리뉴얼 되었습니다.  앞으로 많은 관심과 사랑 부탁드립니다.</a></p>
						<a class= "btn_more" href="#"><img src="/images/btn_more.png" alt="더보기" /></a>
					</div>
				</div>
			</div>

			<div class="bottom">
				<div class="inner">
					<div class="left">
						<h1><a href="#"><img src="/images/logo_footer.png" alt="덕평자연휴게소" /></a></h1>
						<p class="item_01">경기도 이천시 마장면 각평리 319<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>TEL 031.645.0001<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>FAX 031.634.9026</p>
						<p class="item_02">COPYRIGHT(C) 2017 BY NATUREBRIDGE. ALL RIGHTS RESERVED</p>
					</div>
					<div class="right">
						<div id="family">
							<p>FAMILY SITE</p>

						</div>
						<ul>
							<li><a href="#"><img src="/images/m/sns_01.png" alt="페이스북" /></a></li>
							<li><a href="#"><img src="/images/m/sns_02.png" alt="인스타그램" /></a></li>
						</ul>
					</div>
				</div>
			</div>

			<a id="btn_top" href="javascript:"></a>
		</div>
		<!-- // Footer -->

	</div>
	<!-- // Wrapper -->
</body>
</html>